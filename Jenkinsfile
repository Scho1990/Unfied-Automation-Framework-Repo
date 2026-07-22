pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome','firefox','edge'],
            description: 'Select Browser')

        choice(
           name: 'ENV',
            choices: ['qa','uat','prod'],
            description: 'Select Environment')

        choice(
            name: 'EXECUTION',
            choices: ['LOCAL','GRID'],
            description: 'Execution Mode')

        choice(
            name: 'SUITE',
            choices: ['smoke','regression','sanity'],
            description: 'Select Test Suite')

        choice(
            name: 'PARALLEL_MODE',
             choices: ['methods', 'classes', 'tests'],
             description: 'TestNG parallel mode'
        )

        string(
             name: 'THREAD_COUNT',
             defaultValue: '3',
             description: 'Number of parallel threads'
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
                echo "Browser       : ${params.BROWSER}"
                echo "Execution     : ${params.EXECUTION}"
                echo "Environment   : ${params.ENV}"
                echo "Suite         : ${params.SUITE}"
                echo "Parallel Mode : ${params.PARALLEL_MODE}"
                echo "Thread Count  : ${params.THREAD_COUNT}"
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

        stage('Execute Test Suite') {
            steps {

                bat """
                mvn test ^
                -Dbrowser=${params.BROWSER} ^
                -Dexecution=${params.EXECUTION} ^
                -Denvironment=${params.ENV} ^
                -DparallelMode=${params.PARALLEL_MODE} ^
                -DthreadCount=${params.THREAD_COUNT} ^
                -DsuiteXmlFile=testng/${params.SUITE}.xml
                """

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
            echo 'Smoke Suite Passed'
        }

        failure {
            echo 'Smoke Suite Failed'
        }
        cleanup {
                cleanWs()
            }
    }
}