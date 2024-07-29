// Define a method to set environment variables
def terraformDeploy() {

     dir('scripts/Terraform'){
         sh 'pwd'
      }
      dir('scripts/Terraform') {
                    // Change to the specified directory and run Terraform commands
                    sh 'terraform init'
                    sh 'terraform plan'
                    sh 'terraform apply -auto-approve'
                }
    }

terraformDeploy()
