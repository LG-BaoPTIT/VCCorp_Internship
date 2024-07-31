pipeline {
    agent any

    environment {
        IMAGE_TAG = "v1"
    }

    tools {
        maven 'maven_3_5_0'
    }

    stages {
        stage('Build maven') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/LG-BaoPTIT/auth-service']])
                sh 'mvn clean install'
            }
        }

        stage('Build docker image') {
            steps {
                script {
                    sh "docker build -t lgbptit/auth-service:${IMAGE_TAG} ."
                }
            }
        }

        stage('Push image to Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhup-pwd', variable: 'dockerhuppwd')]) {
                        sh "docker login -u lgbptit -p ${dockerhuppwd}"
                        sh "docker push lgbptit/auth-service:${IMAGE_TAG}"
                    }
                }
            }
        }

         stage('Stop and remove old container') {
            steps {
                script {
                    sh 'docker stop auth-service-container || true'
                    sh 'docker rm auth-service-container || true'
                }
            }
         }

        stage('Run docker container') {
            steps {
                script {
                    sh "docker run -d --name  auth-service-container -p 9009:9009 --network myNetwork --ip 172.19.0.5 lgbptit/auth-service:${IMAGE_TAG}"
                }
            }
        }

//         stage('Clean old docker image') {
//             steps {
//                 script {
//                     sh 'docker rmi -f lgbptit/authService-container:v3 || true'
//                 }
//             }
//         }

    }
}

