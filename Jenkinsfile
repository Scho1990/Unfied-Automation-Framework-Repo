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

        stage('Compile') {
            steps {
                bat 'mvn compile'
            }
        }

        stage('Execute Smoke Suite') {
            steps {
                bat 'mvn test -DsuiteXmlFile=testng/testng.xml'
            }
        }
    }

    post {

        always {
            echo 'Pipeline Finished'
            archiveArtifacts artifacts: 'test-output/**', allowEmptyArchive: true
            archiveArtifacts artifacts: 'reports/**', allowEmptyArchive: true
        }

        success {
            echo 'Smoke Suite Passed'
        }

        failure {
            echo 'Smoke Suite Failed'
        }
    }
}