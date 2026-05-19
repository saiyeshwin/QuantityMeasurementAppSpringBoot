pipeline {
    agent any

    environment {
        ECR_URI = '671662643053.dkr.ecr.ap-south-1.amazonaws.com/quantitymeasurement-backend'
        ECR_REGISTRY = '671662643053.dkr.ecr.ap-south-1.amazonaws.com'
        DOCKER_SERVER = '172.31.41.106'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'docker-cicd',
                    url: 'https://github.com/saiyeshwin/QuantityMeasurementAppSpringBoot.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('quantitymeasurement') {
                    sh '''
                    docker build -t quantitymeasurement-backend:latest .
                    '''
                }
            }
        }

        stage('Login To ECR') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-ecr-creds'
                ]]) {
                    sh '''
                    aws ecr get-login-password --region ap-south-1 | \
                    docker login --username AWS --password-stdin ${ECR_REGISTRY}
                    '''
                }
            }
        }

        stage('Tag Docker Image') {
            steps {
                sh '''
                docker tag quantitymeasurement-backend:latest ${ECR_URI}:latest
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                sh '''
                docker push ${ECR_URI}:latest
                '''
            }
        }

        stage('Deploy To Docker EC2') {
            steps {
                sh '''
                ssh -o StrictHostKeyChecking=no \
                -i /var/lib/jenkins/.ssh/quantity-key.pem.pem \
                ubuntu@${DOCKER_SERVER} << EOF
                aws ecr get-login-password --region ap-south-1 | \
                docker login --username AWS --password-stdin ${ECR_REGISTRY}
                cd ~/QuantityMeasurementAppSpringBoot/quantitymeasurement
                docker compose pull
                docker compose up -d

EOF
                '''
            }
        }
    }
}
