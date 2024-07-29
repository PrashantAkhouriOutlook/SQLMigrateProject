# Specify the Terraform provider for Azure
provider "azurerm" {
  features {}
}

# Define the resource group
resource "azurerm_resource_group" "rg-tf-demo" {
  name     = "rg-tf-demo"
  location = "West US"
}
