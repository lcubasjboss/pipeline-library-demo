// vars/pipelineJmeter.groovy
def call(Map pipelineParams) {
//def call() {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                  echo "Hola desde stage Build"
                  echo pipelineParams.name
                  //echo "${pipelineParams.name}"
                }
            }
            stage ('Test') {
                steps {
                  echo "Hola desde stage Test"
                  echo pipelineParams.surname
                  //echo "${pipelineParams.surname}"
                  echo "Environment:" pipelineParams.ENVIRONMENT
                }
            }
        }
    }
}
