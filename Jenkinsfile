pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
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

        stage('Clean Project') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Execute Automation') {
            steps {
                bat 'mvn test'
            }
        }
    }

    post {

        always {
            echo 'Pipeline Finished'
        }

        success {
            echo 'Automation Executed Successfully'
        }

        failure {
            echo 'Automation Execution Failed'
        }
    }
}