// vars/pipelineJmeter.groovy
def call(Map pipelineParams) {
//def call() {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                  echo "Hola desde stage Build"
                }
            }
            stage ('Test') {
                steps {
                  echo "Hola desde stage Test"
                }
            }
        }
    }
}
