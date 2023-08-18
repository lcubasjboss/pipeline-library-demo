// vars/pipelineJmeterTestParams.groovy
//def call(Map pipelineParams) {
//def call()
def call(body) {
    //def pipelineParams= [:]
    //body.resolveStrategy = Closure.DELEGATE_FIRST
    //body.delegate = pipelineParams
    //body()
   echo "variables"
   // pipelineParams.each { println(it) }
     params.each {
    println it.key + " = " + it.value
  }
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
                    println "DEBUG: parameter ENVIRONMENT = ${params.ENVIRONMENT}"
                    script {
                      sayHello("FUNCIONANDO SAY HELLO")
                      sayHello.saludo("FUNCIONANDO SAY HELLO - saludo")
                    }
                }
            }
        }
    }
}
