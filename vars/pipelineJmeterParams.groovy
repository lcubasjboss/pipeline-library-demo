// vars/pipelineJmeterTestParams.groovy
//def call(Map pipelineParams) {
//def call()
def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
    pipelineParams.each { println(it) }
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                  echo "Hola desde stage Build"
                  //echo pipelineParams.name
                }
            }
            stage ('Test') {
                steps {
                  echo "Hola desde stage Test"
                  //echo pipelineParams.surname
                  //echo "Environment: ${pipelineParams.ENVIRONMENT}"
                 // echo "${pipelineParams.ENVIRONMENT}"
                    echo pipelineParams.ENVIRONMENT
                    script {
                      sayHello("FUNCIONANDO SAY HELLO")
                      sayHello.saludo("FUNCIONANDO SAY HELLO - saludo")
                    }
                }
            }
        }
    }
}
