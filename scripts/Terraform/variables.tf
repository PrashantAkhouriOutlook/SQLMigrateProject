variable "azure_spn_client_id" {
  description = "The Azure SPN clientID "
  type        = string
}
variable "azure_spn_client_secret" {
  description = "The Azure SPN client secret "
  type        = string
}

variable "azure_spn_tenant_id" {
  description = "The Azure SPN tenant id "
  type        = string
}

variable "azure_subscription_id" {
  description = "The Azure Susbcripition id "
  type        = string
}

variable "resource_group_name" {
  description = "The name of the Azure resource group."
  type        = string
}

variable "tags" {
  description = "Tags to apply to resources."
  type        = map(string)
  default     = {
    environment = "development"
    team        = "devops"
  }
}
