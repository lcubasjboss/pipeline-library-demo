def ejecutar() {                
    println "Ejecutando JMeter.. - groovy file"
    //env.EMAIL_BUILD_STATUS = "FAILED"
    script {
                fecha = new Date()
                timestamp = fecha.format("yyMMdd.HHmm", TimeZone.getTimeZone('UTC'))
                //Descargar archivo jmx y csv desde repositorio de Bitbucket de Performance
                //Ejecutando script de JMeter por linea de comandos
                echo "Llegamos"
                echo "${BUILD_NUMBER}"
                echo "${WORKSPACE}"
                sh "ls -ltr"
            echo "${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl"
        sh "ls -ltr ${WORKSPACE}/01_Escenarios/"
        sh("mkdir ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
        sh("touch ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html/test")
    }
     //sh "jmeter.sh -n -t  ${WORKSPACE}/01_Escenarios/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${threadscount} -Jlg.${threadsByStep} -Jlg.${timeSecByStep} -Jlg.${rampUpSec} -Jlg.${holdDuration} -Jlg.${threadsToStop} -Jlg.${shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
     //sh "cp -rf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html output"
//Setting Git
script {
    echo "Setting Git configs"
    sh("git --version")
sh ("git config -l")    
sh("git config --global user.name jenkins-bitbucket-integration")
sh("git config --global user.email jenkins-bitbucket-integration@gentera.com.mx")
sh ("git config -l")
    //withCredentials([usernamePassword(credentialsId: 'git-pass-credentials-ID', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
    withCredentials([usernamePassword(credentialsId: '425befb0-743f-4979-ba65-88d35fd69480', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
    echo "Ejecutando comandos git"
    sh("git init .")
    //sh("git remote add performance-test-results git@github.com:lcubasjboss/performance-test-results.git")
    sh("tar -zcvf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html.tar.gz ./${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
    sh("git checkout -b main")
    sh("git add ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html.tar.gz")
    sh("git commit -m Subiendo reporte ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
    sh("git push performance-test-results main")
    //sh("git tag -a some_tag -m 'Jenkins'")
    //sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@<REPO> --tags')
}
    
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
