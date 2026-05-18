pipeline {
agent any

environment {

    AWS_ACCOUNT_ID = '671662643053'
    AWS_REGION = 'ap-south-1'
    IMAGE_NAME = 'quantitymeasurement-backend'

    ECR_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}"
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
                docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
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

    stage('Deploy To Application EC2') {

        steps {

            sh '''
            ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/id_rsa ubuntu@13.206.120.157 << EOF

            aws ecr get-login-password --region ap-south-1 | \
            docker login --username AWS --password-stdin ${ECR_URI}

            cd ~/QuantityMeasurementAppSpringBoot/quantitymeasurement

            docker compose pull

            docker compose up -d

            EOF
            '''
        }
    }
}
}
