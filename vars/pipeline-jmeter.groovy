@Library(['pipeline-yastas']) _
pipeline {
  agent any
  stages {
    stage("Ejecución de JMeter") {
      steps {
        script{
          echo "Iniciando Ejecución.."
          jmeter.ejecutar()
        }
      }
    }
}
}
