# Specify the Terraform provider for Azure
provider "azurerm" {
  features {}
  client_id       = var.azure_spn_client_id
  client_secret   = var.azure_spn_client_secret
  tenant_id       = var.azure_spn_tenant_id
  subscription_id = var.azure_subscription_id

}

# Define the resource group
resource "azurerm_resource_group" "rg-tf-demo" {
  name     = "rg-tf-demo"
  location = "West US"
}
