// vars/pipeline-jmeter.groovy
//def call(Map pipelineParams) {
def call() {
    pipeline {
        agent any
        stages {
            stage('Build') {
               echo "Hola desde stage Build"
            }

            stage ('Test') {
                echo "Hola desde stage Test"
            }
        }
    }
}
