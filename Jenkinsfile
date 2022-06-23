pipeline {
    agent any
    triggers {
        pollSCM 'H/15 * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh '''
                    cd /var/lib/jenkins/workspace/spring-boot
                    mvnw spring-boot:run
                '''
            }
        }
    }
}