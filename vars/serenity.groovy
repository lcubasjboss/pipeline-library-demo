def ejecutarTestUnitario() {
    println "Ejecutando Test Unitario
    //env.EMAIL_BUILD_STATUS = "FAILED"
    script {
             sh("mvn verify -Dit.test=TestRunners -Denvironment=${ENVIRONMENT} -Dtags=${params.TAGS} -Dmaven.test.failure.ignore=true")
    }
}

def calcularRatioExitoso() {
    def fileContent = readFile('./target/site/serenity/summary.txt').trim()
    def testCasesLine = fileContent =~ /Test Cases:\s+(\d+)/
    def testCases = testCasesLine ? testCasesLine[0][1] : null
    def passedLine = fileContent =~ /Passed:\s+(\d+)/
    def passed = passedLine ? passedLine[0][1] : null
    def failedLine = fileContent =~ /Failed:\s+(\d+)/
    def failed = failedLine ? failedLine[0][1] : null
    def successRate =(passed.toInteger() * 100) / testCases.toInteger()
    SUCCESS_RATE = successRate
    PASSED = passed
    FAILED = failed
    env.FLAG_SUCCESS_RATE = successRate >= 80 ? 'SUCCESS' : 'FAILURE'
    env.FLAG_COLOR = env.FLAG_SUCCESS_RATE == 'SUCCESS' ? '00ad00' : 'ff0000'
}

def importarResultadosTest() {
    echo 'Import result tests is '+IMPORT
    NOTIFICATION_MESSAGE = "[Build Jenkins](<http://localhost/job/${JOB_NAME}/${BUILD_NUMBER}>)"
    if(IMPORT == 'true'){
           env.TOKEN = sh(script: 'curl --request POST --url ${URL_TOKEN_XRAY} --header \'Content-Type: application/json\' --data \'{ "client_id": "\'${CLIENT_ID}\'", "client_secret": "\'${CLIENT_SECRET}\'" }\' | sed -e \'s/"//g\'', returnStdout: true).trim()
           env.RESPONSE = sh(script: 'curl --request POST --url ${URL_RESULT_XRAY} --header "Authorization: Bearer ${TOKEN}" --header "Content-Type: application/json" --data @"./target/tests-results.json"', returnStdout: true).trim()
           env.KEY_EXECUTION = sh(script: 'echo "$RESPONSE" | jq -r \'.key\' | sed -e \'s/"//g\'', returnStdout: true).trim()
           if(env.USER_STORY){
                  sh(script:'curl --request POST --url ${URL_JIRA_ISSUELINK} --user ${JIRA_USER}:${JIRA_TOKEN} --header \'Content-Type: application/json\' --header \'Accept: application/json\' --data \'{"inwardIssue": {"key": "\'${KEY_EXECUTION}\'"}, "outwardIssue": {"key": "\'${USER_STORY}\'"}, "type": {"name": "Test", "inward": "is tested by"}}\'', returnStdout: false)
            }
            NOTIFICATION_MESSAGE = "[Build Jenkins](<http://localhost/job/${JOB_NAME}/${BUILD_NUMBER}>) - [Test results in Jira](<${URL_TEST_EXECUTION}${KEY_EXECUTION}>)"
    }
}

def accionesPost() {
    dir("${workspace}") {
        archiveArtifacts 'target/site/serenity/*.*';
        publishHTML(target: [reportDir:'target/site/serenity/',
              reportName :'Reporte Automatizaci\u00f3n Yastas',
              reportFiles:'index.html',
              alwaysLinkToLastBuild:true,
              keepAll:true,
              allowMissing:false])
        office365ConnectorSend webhookUrl: 'https://compartamos.webhook.office.com/webhookb2/405c39e4-d748-4bdf-ab6f-36b9db3d18c4@b2496988-78ea-4b1d-b0f8-19b548a6902b/JenkinsCI/04ad8acb0f944f04a9bdd065bcbfcd46/fad1db5f-6c84-4e86-9f27-fa1ae5424e5f',
              status: "Success Rate: ${SUCCESS_RATE}% - Test Passed: ${PASSED} - Test Failed: ${FAILED}",
              message:"${NOTIFICATION_MESSAGE}",
              color: "${env.FLAG_COLOR}"
    }
}
