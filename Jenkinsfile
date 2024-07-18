pipeline {
     agent any
     stages {
         stage('Powershell version')
         {
            steps{
                    sh 'pwsh --version'
            }
            
         }

         stage('Execute SQL Migartion')
         {
            steps{
                sh 'pwsh SQLMigrate.ps1'
            }
        }
     }
}