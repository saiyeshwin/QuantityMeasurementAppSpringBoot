pipeline {
    agent any

    stages {
        stage('Git Checkout') {
            steps {
                git branch:'dev',
                url:'https://github.com/saiyeshwin/QuantityMeasurementAppSpringBoot.git'
            }
        }
         stage('Build Artifact') {
            steps {
                sh '''
                cd quantitymeasurement
                mvn clean package -Dmaven.test.skip=true
                '''
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                scp quantitymeasurement/target/quantitymeasurement-0.0.1-SNAPSHOT.jar ubuntu@172.31.41.125:/app/quantitymeasurement/target/
                ssh ubuntu@172.31.41.125 'sudo systemctl restart quantityapp.service'
                ssh ubuntu@172.31.41.125 'sudo systemctl status quantityapp.service'
                '''
                
            }
        }
    }
}
