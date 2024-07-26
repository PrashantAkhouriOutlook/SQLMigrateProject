// Define a method to set environment variables
def setEnvironmentVars() {
    // Set environment variables
    env.MY_SHARED_VAR = 'shared_value'
    echo "MY_SHARED_VAR is set to ${env.MY_SHARED_VAR}"
    
    // Azure File Share details
    env.AZURE_STORAGE_ACCOUNT_NAME = 'sqldatastorage'            // Replace with your Azure Storage account name
    env.AZURE_FILE_SHARE_NAME = 'sqldatabackups'                 // Replace with your Azure File Share name
    env.BLOB_NAME = 'EmployeeDB.bacpac'                            // Replace with the name of your BACPAC file in Azure File Share
    env.LOCAL_BACPAC_PATH = '/temp/EmployeeDB.bacpac'               // Local path to download BACPAC file

    // Azure SQL Database details
    env.AZURE_SQL_SERVER_NAME = 'targetsqlserver.database.windows.net' // Replace with your Azure SQL Server name
    env.AZURE_SQL_DATABASE_NAME = 'EmployeeDB'                 // Replace with the name of your target Azure SQL database

    // Using withCredentials for sensitive data
    withCredentials([usernamePassword(credentialsId: 'azure-sql-admin-user', usernameVariable: 'AZURE_SQL_ADMIN_USER', passwordVariable: 'AZURE_SQL_ADMIN_PASSWORD'),
                     string(credentialsId: 'azure-client-id', variable: 'AZURE_CLIENT_ID'),
                     string(credentialsId: 'azure-client-secret', variable: 'AZURE_CLIENT_SECRET'),
                     string(credentialsId: 'azure-tenant-id', variable: 'AZURE_TENANT_ID'),
                     string(credentialsId: 'azure-storage-key', variable: 'AZURE_STORAGE_KEY')]) {
        // Echo the credentials (for demonstration purposes, avoid doing this in real-world scenarios)
        echo "AZURE_SQL_SERVER_NAME is set to ${env.AZURE_SQL_SERVER_NAME}"
        echo "AZURE_CLIENT_ID is set to ${env.AZURE_CLIENT_ID}"
    }
}

// Call the method to set environment variables
setEnvironmentVars()
