//def ejecutar() {                
def ejecutar(params) {                
  println "Checando variables que vienen de Jenkins Job"
  params.each {
    println it.key + " = " + it.value
  }
  params.each {param ->
    println " '${param.key.trim()}' -> '${param.value.trim()}' "
  }
  println "BRANCH"
//  println scm.branches[0].name
  def gitBranch = scm.branches[0].name
  print gitBranch
  println "GIT REPO"
  def gitRepo = scm.getUserRemoteConfigs()[0].getUrl()
  println gitRepo
  //println scm.getUserRemoteConfigs()[0].getUrl().tokenize('/').last().split("\\.")[0]
  //println scm.getUserRemoteConfigs()[0].getUrl()
  imprimir(gitBranch,gitRepo)
  println "IMprimiendo GIT_BRANCH / GIT_URL"
  println GIT_BRANCH
  println GIT_URL
  def credentialID = scm.userRemoteConfigs[0].credentialsId
  println credentialID
  println "Ejecutando JMeter.."
  script {
       fecha = new Date()
       timestamp = fecha.format("yyMMdd.HHmm", TimeZone.getTimeZone('UTC'))
       sh("mkdir ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html")
        //sh("touch ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html/test")
  }
    //sh "jmeter.sh -n -t  ${WORKSPACE}/01_Escenarios/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${threadscount} -Jlg.${threadsByStep} -Jlg.${timeSecByStep} -Jlg.${rampUpSec} -Jlg.${holdDuration} -Jlg.${threadsToStop} -Jlg.${shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
    //Descomentar
    sh "jmeter.sh -n -t  ${WORKSPACE}/01_Escenarios/Pruebatecnica.jmx -l  ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}.jtl -JSC00.${params.threadscount} -Jlg.${params.threadsByStep} -Jlg.${params.timeSecByStep} -Jlg.${params.rampUpSec} -Jlg.${params.holdDuration} -Jlg.${params.threadsToStop} -Jlg.${params.shutdownTime} -e -o ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html"                 
    sh "cp -rf ${WORKSPACE}/SC00_CrearReceta_UAT_5_VU_${timestamp}_html output"
    sh "ls -ltr output"
    stash includes: 'output/**', name: 'output'
}

def publicarResultados() {                
    println "Publicando Resultados.."
    unstash 'output'
    sh("ls -ltr output")
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
  /*
  stash 'output'
  echo "output files"
  sh("ls -ltr output")
  */
  println $BUILD_TAG
  println BUILD_TAG
}
def imprimir(branch,repo) {
  println "IMprimineod desde imprimir method"
  println repo
  println branch
}
