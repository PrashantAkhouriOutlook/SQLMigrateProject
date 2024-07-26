// Define a method to set environment variables
def setEnvVars() {
    // Set environment variables
    env.MY_SHARED_VAR = 'shared_value'
    echo "MY_SHARED_VAR is set to ${env.MY_SHARED_VAR}"
     
        //Azure File Share details
        env.AZURE_STORAGE_ACCOUNT_NAME = 'sqldatastorage'            // Replace with your Azure Storage account name
        env.AZURE_STORAGE_ACCOUNT_NAME = 'sqldatastorage'            // Replace with your Azure Storage account name
        env.AZURE_FILE_SHARE_NAME = 'sqldatabackups'                      // Replace with your Azure File Share name
        env.BLOB_NAME = 'EmployeeDB.bacpac'                            // Replace with the name of your BACPAC file in Azure File Share
        env.LOCAL_BACPAC_PATH = '/temp/EmployeeDB.bacpac'               // Local path to download BACPAC file

        // Azure SQL Database details
        env.AZURE_SQL_SERVER_NAME = 'targetsqlserver.database.windows.net' // Replace with your Azure SQL Server name
        env.AZURE_SQL_DATABASE_NAME = 'EmployeeDB'                 // Replace with the name of your target Azure SQL database
        env.AZURE_SQL_ADMIN_USER = credentials('azure-sql-admin-user')   // Jenkins credentials ID for Azure SQL admin user
        env.AZURE_SQL_ADMIN_PASSWORD = credentials('azure-sql-admin-password') // Jenkins credentials ID for Azure SQL admin password

        // Azure credentials ( dont use useCredentials for type secrettext , secrekey,)
        env.AZURE_CLIENT_ID = credentials('azure-client-id')             // Jenkins credentials ID for Azure Client ID
        env.AZURE_CLIENT_SECRET = credentials('azure-client-secret')     // Jenkins credentials ID for Azure Client Secret
        env.AZURE_TENANT_ID = credentials('azure-tenant-id')             // Jenkins credentials ID for Azure Tenant ID
        env.AZURE_STORAGE_KEY = credentials('azure-storage-key')         // Jenkins credentials ID for Azure Storage Key

        echo "AZURE_SQL_SERVER_NAME is set to ${env.AZURE_SQL_SERVER_NAME}"
        echo "AZURE_CLIENT_ID is set to ${env.AZURE_CLIENT_ID}"
}

// Call the method to set environment variables
setEnvVars()
