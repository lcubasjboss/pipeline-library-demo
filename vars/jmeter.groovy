def ejecutar() {                
    println "Ejecutando JMeter.."
    //env.EMAIL_BUILD_STATUS = "FAILED"
    script {
                fecha = new Date()
                timestamp = fecha.format("yyMMdd.HHmm", TimeZone.getTimeZone('UTC'))
                //Descargar archivo jmx y csv desde repositorio de Bitbucket de Performance
                //Ejecutando script de JMeter por linea de comandos
                echo "${WORKSPACE}"
                // sh "jmeter.sh -n -t  ${WORKSPACE}/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${threadscount} -Jlg.${threadsByStep} -Jlg.${timeSecByStep} -Jlg.${rampUpSec} -Jlg.${holdDuration} -Jlg.${threadsToStop} -Jlg.${shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
                // sh "cp -rf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html output"

                env.EMAIL_BUILD_STATUS = "SUCCESS"
    }     
}
