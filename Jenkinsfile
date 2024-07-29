pipeline {
    agent any
    
    stages {
        // stage('Initialize Environment') {
        //     steps {
        //         script {
        //             sh "pwd"
        //             echo 'Preparing environment...'
        //             // You can call a Groovy script here if needed
        //             // For example, you might include a Groovy script from a file
        //             load 'scripts/setvariables.groovy'

        //                  echo "AZURE_SQL_SERVER_NAME is set to ${env.AZURE_SQL_SERVER_NAME}"
        //                  echo "AZURE_CLIENT_ID is set to  ${AZURE_CLIENT_ID}"
        //         }
        //     }
        // }

         stage('TerraformDeployment') {
            steps {
                script {
                        
                      sh '''
                     
                        echo 'Deploying through Terraform ...'
                        load 'scripts/terraformdeploy.groovy'
                    
                    '''
                }
            }
        }

        // stage('Build') {
        //     steps {
        //         script {
        //             echo 'Building the project...'
        //             // Call the Groovy script for build
        //             // For example, you might execute a Groovy script that builds your project
        //             load 'scripts/build.groovy'
        //         }
        //     }
        // }

        // stage('Test') {
        //     steps {
        //         script {
        //             echo 'Running tests...'
        //             // Call the Groovy script for testing
        //             // For example, you might execute a Groovy script that runs tests
        //             // load 'scripts/test.groovy'
        //         }
        //     }
        // }

        stage('Deploy') {
            steps {
                script {
                      sh '''
                        echo 'Deploying the project...'
                        echo " AZURE_SQL_SERVER_NAME is set to ${env.AZURE_SQL_SERVER_NAME}"
                        echo ' AZURE_CLIENT_ID is set to $AZURE_CLIENT_ID'
                    // Call the Groovy script for deployment
                    // For example, you might execute a Groovy script that deploys your application
                    load 'scripts/deploy.groovy'
                    
                    '''
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
