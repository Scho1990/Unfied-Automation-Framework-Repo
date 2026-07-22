//=============================================================
// Unified Automation Framework
// Enterprise Jenkins Pipeline
//=============================================================

//-------------------------------
// Helper Method
//-------------------------------
def runTests(String browser) {

    echo "=============================================="
    echo "Browser        : ${browser}"
    echo "Execution      : ${params.EXECUTION}"
    echo "Environment    : ${params.ENV}"
    echo "Suite          : ${params.SUITE}"
    echo "Parallel Mode  : ${params.PARALLEL_MODE}"
    echo "Thread Count   : ${params.THREAD_COUNT}"
    echo "=============================================="

    bat """
        mvn clean test ^
        -Dbrowser=${browser} ^
        -Dexecution=${params.EXECUTION} ^
        -Denvironment=${params.ENV} ^
        -DparallelMode=${params.PARALLEL_MODE} ^
        -DthreadCount=${params.THREAD_COUNT} ^
        -DsuiteXmlFile=testng/${params.SUITE}.xml
    """
}

pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    parameters {
        choice(
            name: 'EXECUTION_TYPE',
            choices: ['SINGLE', 'CROSS_BROWSER'],
            description: 'Execution Type')

        choice(
            name: 'BROWSER',
            choices: ['CHROME','FIREFOX','EDGE'],
            description: 'Browser (Used only for SINGLE execution)')

        choice(
           name: 'ENV',
            choices: ['QA','UAT','PROD'],
            description: 'Environment')

        choice(
            name: 'EXECUTION',
            choices: ['LOCAL','GRID'],
            description: 'Execution Mode')

        choice(
            name: 'SUITE',
            choices: ['smoke','regression'],
            description: 'Test Suite')

        choice(
            name: 'PARALLEL_MODE',
             choices: ['methods', 'classes', 'tests'],
             description: 'TestNG Parallel Mode'
        )

        string(
             name: 'THREAD_COUNT',
             defaultValue: '3',
             description: 'Parallel Thread Count'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                echo '========== CHECKOUT =========='
                checkout scm
            }
        }

        stage('Verify Environment') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
            }
        }

        stage('Print Environment') {
            steps {
                echo "==================================="
                echo "Execution Type : ${params.EXECUTION_TYPE}"
                echo "Browser        : ${params.BROWSER}"
                echo "Execution      : ${params.EXECUTION}"
                echo "Environment    : ${params.ENV}"
                echo "Suite          : ${params.SUITE}"
                echo "Parallel Mode  : ${params.PARALLEL_MODE}"
                echo "Thread Count   : ${params.THREAD_COUNT}"
                echo "==================================="
            }

        }

        stage('Clean Project') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                bat 'mvn compile'
            }
        }

        stage('Start Selenium Grid') {
            when {
                expression { params.EXECUTION == 'GRID' }
            }
            steps {
                echo '========== Starting Selenium Grid =========='
                bat 'docker compose -f docker/docker-compose.yml down'
                bat 'docker compose -f docker/docker-compose.yml up -d'
            }
        }

        stage('Wait for Grid') {
            when {
                expression { params.EXECUTION == 'GRID' }
            }
            steps {
                echo '========== Waiting for the grid to up =========='
                sleep(time: 20, unit: 'SECONDS')
            }
        }

        stage('Execute Tests') {
            steps {
               script {
                    if (params.EXECUTION_TYPE == 'SINGLE') {
                        runTests(params.BROWSER.toLowerCase())
                    }
                    else {
                       parallel(
                             Chrome: {
                                  runTests("chrome")
                             },
                             Firefox: {
                                  runTests("firefox")
                             },
                             Edge: {
                                  runTests("edge")
                             }
                       )
                         }
                    }
               }
            }
        }

    post {

        always {

          script {
                    if (params.EXECUTION == 'GRID') {
                        bat 'docker compose -f docker/docker-compose.yml down'
                    }
                }
            echo 'Pipeline Finished'
            archiveArtifacts (artifacts: 'logs/**', allowEmptyArchive: true,fingerprint: true)
            archiveArtifacts (artifacts: 'reports/**', allowEmptyArchive: true,fingerprint: true)

        }

        success {
            echo "===================================="

            echo "Build Successful"

            echo "===================================="
        }

        failure {
             echo "===================================="

             echo "Build Failed"

             echo "===================================="
        }
        cleanup {
                cleanWs()
            }
    }
}