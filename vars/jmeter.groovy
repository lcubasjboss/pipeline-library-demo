def ejecutar() {                
    println "Ejecutando JMeter.. - groovy file"
    //env.EMAIL_BUILD_STATUS = "FAILED"
    script {
                fecha = new Date()
                timestamp = fecha.format("yyMMdd.HHmm", TimeZone.getTimeZone('UTC'))
                //Descargar archivo jmx y csv desde repositorio de Bitbucket de Performance
                //Ejecutando script de JMeter por linea de comandos
                //echo "Llegamos"
                //echo "${BUILD_NUMBER}"
                //echo "${WORKSPACE}"
                //sh "ls -ltr"
            //echo "${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl"
        //sh "ls -ltr ${WORKSPACE}/01_Escenarios/"
        sh("mkdir ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
        sh("touch ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html/test")
    }
     //sh "jmeter.sh -n -t  ${WORKSPACE}/01_Escenarios/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${threadscount} -Jlg.${threadsByStep} -Jlg.${timeSecByStep} -Jlg.${rampUpSec} -Jlg.${holdDuration} -Jlg.${threadsToStop} -Jlg.${shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
     //sh "cp -rf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html output"
//Setting Git
script {
    echo "Setting Git configs"
    //sh("git --version")
//sh ("git config -l")    
sh("git config --global user.name jenkins-bitbucket-integration")
sh("git config --global user.email jenkins-bitbucket-integration@gentera.com.mx")
//sh ("git config -l")
    echo "Ejecutando comandos git"
    sh("git init .")
    //sh("git remote add performance-test-results10 git@github.com:lcubasjboss/performance-test-results.git")
    sh("git remote add performance-test-results10 https://github.com/lcubasjboss/performance-test-results.git")
    sh("tar -zcvf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html.tar.gz ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
    sh("git checkout -b main10")
    sh("git add ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html.tar.gz")
    sh("git commit -m 'Subiendo reporte ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html'")
    //withCredentials([usernamePassword(credentialsId: 'git-pass-credentials-ID', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
    withCredentials([usernamePassword(credentialsId: '393376bc-8463-4ab3-b2fb-3029d48396dc', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
     //  sh("git push performance-test-results3 main3")
          //sh("git push https://${USERNAME}:${PASSWORD}@github.com/${USERNAME}/performance-test-results.git main8")
        sh("git push https://${USERNAME}@github.com/lcubasjboss/performance-test-results.git main10")
        //https://github.com/lcubasjboss/performance-test-results.git
        //git push https://<User Name>:<Token>@github.com/<User Name>/<Your Repository>.git
        //git push https://<GITHUB_ACCESS_TOKEN>@github.com/<GITHUB_USERNAME>/<REPOSITORY_NAME>.git
    //sh("git tag -a some_tag -m 'Jenkins'")
    //sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@<REPO> --tags')
}
      sh("git remote -v")
    sh("rm -rf .git")
    //            env.EMAIL_BUILD_STATUS = "SUCCESS"
}
}

def publicarResultados() {                
    println "Publicando Resultados .."
    //env.EMAIL_BUILD_STATUS = "FAILED"
    script {
      echo "Publicando Resultados - groovy file"
        publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'output',
                    reportFiles: 'index.html',
                    reportName: 'JMeter Report'
                ])
    }              
}
