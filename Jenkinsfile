pipeline {
  agent any

  environment {
    JAVA_HOME = "C:\\Program Files\\Android\\Android Studio\\jbr"
    ANDROID_SDK_ROOT = "C:\\Users\\hujun\\AppData\\Local\\Android\\Sdk"
    PATH = "${JAVA_HOME}\\bin;${ANDROID_SDK_ROOT}\\platform-tools;${ANDROID_SDK_ROOT}\\emulator;${env.PATH}"
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Check Java') {
      steps {
        bat 'echo JAVA_HOME=%JAVA_HOME%'
        bat 'java -version'
      }
    }

    stage('Run Gradle') {
      steps {
        bat 'gradlew.bat tasks'
      }
    }
  }
}
