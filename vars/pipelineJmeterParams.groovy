// vars/pipelineJmeterParams.groovy
//def call(Map pipelineParams) {
def call(body) {
    //def pipelineParams= [:]
    //body.resolveStrategy = Closure.DELEGATE_FIRST
    //body.delegate = pipelineParams
    //body()
   echo "variables"
     //pipelineParams.each { println(it) }
     params.each {
    println it.key + " = " + it.value
  }
    MY_VAR = "hello world"
  pipeline {
    agent any
    stages {
     stage('Ejecución de JMeter') {
       steps {
            script{
              echo "Iniciando Ejecución desde pipelineJmeter.groovy.."
              //echo "Iniciando Ejecución.."
              //jmeter.ejecutar(params)
            }
       }
     }
     stage('Publicación de Resultados') {
       steps {
            script{
              echo "Publicando Resultados desde pipelineJmeter.groovy.."
              //echo "Publicando Resultados.."
              //jmeter.publicarResultados()
            }
       }
     }
    stage('Subir resultados a Repositorio de Bitbucket') {
       steps {
           script{
            echo "Subiendo resultados desde pipelineJmeter.groovy.."
              echo "Subiendo resultados.."
            jmeter.subirResultados()
    }
  }
  }
     stage('Remover carpetas') {
       steps {
           script{
             echo "Removiendo carpetas desde pipelineJmeter.groovy.."
             echo "Removiendo carpetas.."
             jmeter.removerCarpetas()
               println MY_VAR
           }
       }
    }
  }
  post {
       always {
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true,
                    patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                               [pattern: '.propsfile', type: 'EXCLUDE']])
        }
  }
 }
}
