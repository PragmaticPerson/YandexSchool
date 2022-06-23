pipeline {
    agent any
    triggers {
        pollSCM 'H/15 * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh './mvnw spring-boot:run'
            }
        }
    }
}