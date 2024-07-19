pipeline {
    agent any

    environment {
        // Azure File Share details
        AZURE_STORAGE_ACCOUNT_NAME = 'sqldatastorage'            // Replace with your Azure Storage account name
        AZURE_FILE_SHARE_NAME = 'sqldatabackups'                      // Replace with your Azure File Share name
        BLOB_NAME = 'EmployeeDB.bacpac'                            // Replace with the name of your BACPAC file in Azure File Share
        LOCAL_BACAPC_PATH = '/tmp/EmployeeDB.bacpac'               // Local path to download BACPAC file

        // Azure SQL Database details
        AZURE_SQL_SERVER_NAME = 'targetsqlserver.database.windows.net' // Replace with your Azure SQL Server name
        AZURE_SQL_DATABASE_NAME = 'EmployeeDB'                 // Replace with the name of your target Azure SQL database
        AZURE_SQL_ADMIN_USER = credentials('azure-sql-admin-user')   // Jenkins credentials ID for Azure SQL admin user
        AZURE_SQL_ADMIN_PASSWORD = credentials('azure-sql-admin-password') // Jenkins credentials ID for Azure SQL admin password

        // Azure credentials
        AZURE_CLIENT_ID = credentials('azure-client-id')             // Jenkins credentials ID for Azure Client ID
        AZURE_CLIENT_SECRET = credentials('azure-client-secret')     // Jenkins credentials ID for Azure Client Secret
        AZURE_TENANT_ID = credentials('azure-tenant-id')             // Jenkins credentials ID for Azure Tenant ID
        AZURE_STORAGE_KEY = credentials('azure-storage-key')         // Jenkins credentials ID for Azure Storage Key
    }

    stages {
        stage('Download BACPAC from Azure File Share') {
            steps {
                script {
                    // Use Azure CLI to mount Azure File Share
                    withCredentials([
                        string(credentialsId: env.AZURE_CLIENT_ID, variable: 'AZURE_CLIENT_ID'),
                        string(credentialsId: env.AZURE_CLIENT_SECRET, variable: 'AZURE_CLIENT_SECRET'),
                        string(credentialsId: env.AZURE_TENANT_ID, variable: 'AZURE_TENANT_ID'),
                        string(credentialsId: env.AZURE_STORAGE_KEY, variable: 'AZURE_STORAGE_KEY')
                    ]) {
                        sh """
                            # Log in to Azure
                            az login --service-principal -u ${env.AZURE_CLIENT_ID} -p ${env.AZURE_CLIENT_SECRET} --tenant ${env.AZURE_TENANT_ID}

                            # Install Azure CLI extension for File Share if necessary
                            az extension add --name storage-preview

                            # Create a local directory to mount Azure File Share
                            mkdir -p /mnt/azurefile

                            # Mount Azure File Share
                            sudo mount -t cifs //${env.AZURE_STORAGE_ACCOUNT_NAME}.file.core.windows.net/${env.AZURE_FILE_SHARE_NAME} /mnt/azurefile -o vers=3.0,username=${env.AZURE_STORAGE_ACCOUNT_NAME},password=${env.AZURE_STORAGE_KEY},dir_mode=0777,file_mode=0777

                            # Download the BACPAC file
                            cp /mnt/azurefile/${env.BLOB_NAME} ${env.LOCAL_BACAPC_PATH}
                        """

                        // Check if the download was successful
                        if (currentBuild.result == 'FAILURE') {
                            error "Failed to download BACPAC file from Azure File Share"
                        }
                    }
                }
            }
        }

        stage('Restore BACPAC to Azure SQL Database') {
            steps {
                script {
                    // Ensure SqlPackage.exe path is correct
                    def sqlPackagePath = "/opt/sqlpackage/sqlpackage"

                    // Check if SqlPackage.exe exists
                    if (!fileExists(sqlPackagePath)) {
                        error "SqlPackage.exe not found at ${sqlPackagePath}"
                    }

                    // Restore the database from the BACPAC file
                    sh """
                        ${sqlPackagePath} /Action:Import /SourceFile:${env.LOCAL_BACAPC_PATH} /TargetServerName:${env.AZURE_SQL_SERVER_NAME} /TargetDatabaseName:${env.AZURE_SQL_DATABASE_NAME} /TargetUser:${env.AZURE_SQL_ADMIN_USER} /TargetPassword:${env.AZURE_SQL_ADMIN_PASSWORD}
                    """

                    // Check if the restore was successful
                    if (currentBuild.result == 'FAILURE') {
                        error "Failed to restore BACPAC file to Azure SQL Database"
                    }
                }
            }
        }
    }

    post {
        always {
            // Clean up
            echo "Cleaning up..."
            sh "rm -f ${env.LOCAL_BACAPC_PATH}"
            sh "sudo umount /mnt/azurefile"
        }
    }
}