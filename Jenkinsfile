pipeline {
    agent any

    environment {
        // Azure File Share details
        AZURE_STORAGE_ACCOUNT_NAME = 'sqldatastorage'            // Replace with your Azure Storage account name
        AZURE_FILE_SHARE_NAME = 'sqldatabackups'                      // Replace with your Azure File Share name
        BLOB_NAME = 'EmployeeDB.bacpac'                            // Replace with the name of your BACPAC file in Azure File Share
        LOCAL_BACPAC_PATH = '/temp/EmployeeDB.bacpac'               // Local path to download BACPAC file

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
                    withCredentials([
                        string(credentialsId: 'azure-client-id', variable: 'AZURE_CLIENT_ID'),
                        string(credentialsId: 'azure-client-secret', variable: 'AZURE_CLIENT_SECRET'),
                        string(credentialsId: 'azure-tenant-id', variable: 'AZURE_TENANT_ID'),
                        string(credentialsId: 'azure-storage-key', variable: 'AZURE_STORAGE_KEY')
                    ]) {
                        sh """
                            # Log in to Azure
                            az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET --tenant $AZURE_TENANT_ID

                            # Install Azure CLI extension for File Share if necessary
                            az extension add --name storage-preview || true

                            # Create a local directory to mount Azure File Share
                            mkdir -p /mnt/azurefile

                            # Mount Azure File Share
                            sudo mount -t cifs //$AZURE_STORAGE_ACCOUNT_NAME.file.core.windows.net/$AZURE_FILE_SHARE_NAME /mnt/azurefile -o vers=3.0,username=$AZURE_STORAGE_ACCOUNT_NAME,password=$AZURE_STORAGE_KEY,dir_mode=0777,file_mode=0777

                            # Download the BACPAC file
                            cp /mnt/azurefile/$BLOB_NAME $LOCAL_BACPAC_PATH
                        """

                        // Check if the download was successful
                        if (sh(script: "test -f $LOCAL_BACPAC_PATH", returnStatus: true) != 0) {
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
                        error "SqlPackage.exe not found at $sqlPackagePath"
                    }

                    // Restore the database from the BACPAC file
                    sh """
                        $sqlPackagePath /Action:Import /SourceFile:$LOCAL_BACPAC_PATH /TargetServerName:$AZURE_SQL_SERVER_NAME /TargetDatabaseName:$AZURE_SQL_DATABASE_NAME /TargetUser:$AZURE_SQL_ADMIN_USER /TargetPassword:$AZURE_SQL_ADMIN_PASSWORD
                    """

                    // Check if the restore was successful
                    if (currentBuild.result == 'FAILURE') {
                        error "Failed to restore BACPAC file to Azure SQL Database"
                    }
                }
            }
        }
    }

    // post {
    //     always {
    //         // Ensure we have a node context for cleanup
    //         node {
    //             // Clean up
    //             echo "Cleaning up..."
    //             sh "rm -f ${LOCAL_BACPAC_PATH}"
    //             sh "sudo umount /mnt/azurefile || true"
    //         }
    //     }
    // }
}
