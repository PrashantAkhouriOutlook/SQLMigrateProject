pipeline {
    agent any
    
    stages {
        stage('Preparation') {
            steps {
                script {
                    echo 'Preparing environment...'
                    // You can call a Groovy script here if needed
                    // For example, you might include a Groovy script from a file
                    load 'scripts/preparation.groovy'
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
                    echo 'Deploying the project...'
                    // Call the Groovy script for deployment
                    // For example, you might execute a Groovy script that deploys your application
                    load 'scripts/deploy.groovy'
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
