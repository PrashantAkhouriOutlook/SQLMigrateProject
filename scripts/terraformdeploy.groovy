// Define a method to set environment variables
def terraformDeploy() {

     sh "pwd"
     dir("${env.WORKSPACE}/aQA"){
    sh "pwd"
      }
      dir("${env.WORKSPACE}/aQA") {
                    // Change to the specified directory and run Terraform commands
                    sh 'terraform init'
                    sh 'terraform plan'
                    sh 'terraform apply -auto-approve'
                }
    }

terraformDeploy()
