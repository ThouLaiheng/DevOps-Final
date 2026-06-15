pipeline {
    agent any

    triggers {
        pollSCM('*/5 * * * *')
    }

    environment {
        // Email recipients
        SRE_EMAIL = 'srengty@gmail.com'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dspring.profiles.active=test'
            }
            post {
                failure {
                    script {
                        // Get the committer email of the latest commit
                        def developerEmail = sh(
                            script: 'git --no-pager log -1 --pretty=format:"%ae"',
                            returnStdout: true
                        ).trim()

                        mail(
                            to: "${SRE_EMAIL}, ${developerEmail}",
                            subject: "BUILD/TEST FAILED: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                            body: """
                                Project: ${env.JOB_NAME}
                                Build #: ${env.BUILD_NUMBER}
                                Status: FAILED during Test stage
                                URL: ${env.BUILD_URL}
                                Committer: ${developerEmail}
                                Branch: ${env.BRANCH_NAME}
                            """
                        )
                    }
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy via Ansible') {
            steps {
                sh 'ansible-playbook -i inventory.ini deploy.yml'
            }
        }
    }

    post {
        failure {
            script {
                def developerEmail = sh(
                    script: 'git --no-pager log -1 --pretty=format:"%ae"',
                    returnStdout: true
                ).trim()

                mail(
                    to: "${SRE_EMAIL}, ${developerEmail}",
                    subject: "FAILED: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                    body: """
                        Project: ${env.JOB_NAME}
                        Build #: ${env.BUILD_NUMBER}
                        Status: FAILED at stage ${env.STAGE_NAME}
                        URL: ${env.BUILD_URL}
                        Committer: ${developerEmail}
                        Branch: ${env.BRANCH_NAME}
                    """
                )
            }
        }

        success {
            echo "Pipeline completed successfully. Deployed via Ansible."
        }
    }
}