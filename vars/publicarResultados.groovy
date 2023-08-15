def publicarResultados() {                
    println "Publicando Resultados .."
    //env.EMAIL_BUILD_STATUS = "FAILED"
    script {
      echo "Publicando Resultados - publicarResultados.groovy"
      //            publishHTML(target: [
      //              allowMissing: false,
      //              alwaysLinkToLastBuild: true,
      //              keepAll: true,
      //              reportDir: 'output',
      //              reportFiles: 'index.html',
      //              reportName: 'JMeter Report'
      //          ])
    }              
}
