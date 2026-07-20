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
                echo "Browser     : ${params.BROWSER}"
                echo "Execution   : ${params.EXECUTION}"
                echo "Environment : ${params.ENV}"
                echo "Suite       : ${params.SUITE}"
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

        stage('Execute Test Suite') {
            steps {

                bat """
                mvn test ^
                -Dbrowser=${params.BROWSER} ^
                -Dexecution=${params.EXECUTION} ^
                -Denvironment=${params.ENV} ^
                -DsuiteXmlFile=testng/${params.SUITE}.xml
                """

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