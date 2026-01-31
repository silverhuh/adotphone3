pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Gradle Test') {
            steps {
                bat 'gradlew.bat tasks'
            }
        }
    }
}
