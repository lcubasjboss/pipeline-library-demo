def ejecutar() {                
    println "Ejecutando JMeter.. - groovy file"
    def podDeployYml = readFileProps.podDeployYmlByServiceAccount(confProjectYml['serviceAccountDeploy'])

    podTemplate(yaml: podDeployYml) {
        node(POD_LABEL) {
             container("jmeter-container") {

    script {
              fecha = new Date()
              timestamp = fecha.format("yyMMdd.HHmm", TimeZone.getTimeZone('UTC'))
              sh("mkdir ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
              sh("touch ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html/test")
    }
                
                sh "jmeter.sh -n -t  ${WORKSPACE}/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${threadscount} -Jlg.${threadsByStep} -Jlg.${timeSecByStep} -Jlg.${rampUpSec} -Jlg.${holdDuration} -Jlg.${threadsToStop} -Jlg.${shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
                sh "cp -rf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html output"

                env.EMAIL_BUILD_STATUS = "SUCCESS"
        }     
            }
        }
    }
}

jmeter
/*
def publicarReportes() {
          publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'output',
                    reportFiles: 'index.html',
                    reportName: 'JMeter Report'
                ])
}
*/
return this
