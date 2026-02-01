pipeline {
  agent any

  environment {
    JAVA_HOME = "C:\\Program Files\\Android\\Android Studio\\jbr"
    ANDROID_SDK_ROOT = "C:\\Users\\hujun\\AppData\\Local\\Android\\Sdk"
    PATH = "${JAVA_HOME}\\bin;${ANDROID_SDK_ROOT}\\platform-tools;${ANDROID_SDK_ROOT}\\emulator;${env.PATH}"
    AVD_NAME = "Medium_Phone"
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Start Emulator') {
      steps {
        bat 'adb kill-server'
        bat 'adb start-server'
        bat 'start "" /B emulator -avd %AVD_NAME% -no-window -no-audio -gpu swiftshader_indirect -no-snapshot-save'
        bat 'adb wait-for-device'
        bat 'adb shell getprop sys.boot_completed'
      }
    }

    stage('Run Instrumentation Tests') {
      steps {
        bat 'gradlew.bat connectedAndroidTest'
      }
    }
  }

  post {
    always {
      bat 'adb emu kill || exit /b 0'
    }
  }
}
