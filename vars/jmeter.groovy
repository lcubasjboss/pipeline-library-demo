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
    }
     //sh "jmeter.sh -n -t  ${WORKSPACE}/01_Escenarios/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${threadscount} -Jlg.${threadsByStep} -Jlg.${timeSecByStep} -Jlg.${rampUpSec} -Jlg.${holdDuration} -Jlg.${threadsToStop} -Jlg.${shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
     //sh "cp -rf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html output"
//Setting Git
script {
    echo "Setting Git configs"
    sh("git --version")
sh ("git config -l")    
sh("git config --global user.name Jenkins-Bitbucket Integration")
sh("git config --global user.email jenkins-bitbucket-integration@gentera.com.mx")
sh ("git config -l")
    withCredentials([usernamePassword(credentialsId: 'git-pass-credentials-ID', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
    echo "Ejecutando comandos git"
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
