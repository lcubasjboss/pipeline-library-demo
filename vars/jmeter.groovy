//def ejecutar() {                
def ejecutar(params) {                
  println "Checando variables que vienen de Jenkins Job"
  params.each {
    println it.key + " = " + it.value
  }
  params.each {param ->
    println " '${param.key.trim()}' -> '${param.value.trim()}' "
  }
  print 'DEBUG: parameter TC = ' + params.threadscount
  print "DEBUG: parameter TC = ${params.threadscount}"    
  println env.branch
  def myvariables = getBinding().getVariables()
for (v in myvariables) {
   echo "${v} " + myvariables.get(v)
}
  println "Ejecutando JMeter.."
  script {
       fecha = new Date()
       timestamp = fecha.format("yyMMdd.HHmm", TimeZone.getTimeZone('UTC'))
       sh("mkdir ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
        //sh("touch ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html/test")
  }
    //sh "jmeter.sh -n -t  ${WORKSPACE}/01_Escenarios/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${threadscount} -Jlg.${threadsByStep} -Jlg.${timeSecByStep} -Jlg.${rampUpSec} -Jlg.${holdDuration} -Jlg.${threadsToStop} -Jlg.${shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
    //Descomentar
    //sh "jmeter.sh -n -t  ${WORKSPACE}/01_Escenarios/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${params.threadscount} -Jlg.${params.threadsByStep} -Jlg.${params.timeSecByStep} -Jlg.${params.rampUpSec} -Jlg.${params.holdDuration} -Jlg.${params.threadsToStop} -Jlg.${params.shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
    //sh "cp -rf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html output"
    //sh "ls -ltr output"
}

def publicarResultados() {                
    println "Publicando Resultados.."
    script {
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

def removerCarpetas() {                
    println "Publicando Resultados .."
    script {
        sh("rm -rf output ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
    }
}

def subirResultados() {
    println "Subiendo Resultados .."
     script {
     }
}
