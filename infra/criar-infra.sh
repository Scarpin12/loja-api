#!/bin/bash
# Cria a infraestrutura no Azure: Resource Group, Azure SQL (servidor + banco),
# Plano de Servico e Web App Java. Execute no Cloud Shell (Bash).
#
# Uso:
#   export DB_PASSWORD='SenhaForte#2026'   # nunca escreva a senha neste arquivo
#   bash criar-infra.sh
#
# Antes de rodar, ALTERE o RM abaixo (letras minusculas, sem espacos).

set -e

if [ -z "$DB_PASSWORD" ]; then
  echo "Defina a senha do banco: export DB_PASSWORD='...'"
  exit 1
fi

RM="rm00000"                       # <-- ALTERE para o seu RM
LOCATION="brazilsouth"             # se o SQL nao aceitar a regiao, tente eastus2
RESOURCE_GROUP="rg-loja-$RM"
SQL_SERVER="sql-loja-$RM"
SQL_DB="lojadb"
SQL_USER="sqladmin"
APP_PLAN="plan-loja-$RM"
WEBAPP="app-loja-$RM"
RUNTIME="JAVA:17-java17"

# Registrar os servicos utilizados
az provider register --namespace Microsoft.Web
az provider register --namespace Microsoft.Sql

# Grupo de recursos
az group create --name "$RESOURCE_GROUP" --location "$LOCATION"

# Banco de dados em nuvem (PaaS): Azure SQL
az sql server create \
  --name "$SQL_SERVER" \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --admin-user "$SQL_USER" \
  --admin-password "$DB_PASSWORD"

az sql db create \
  --resource-group "$RESOURCE_GROUP" \
  --server "$SQL_SERVER" \
  --name "$SQL_DB" \
  --service-objective Basic

# Permite que servicos do Azure (Web App, Cloud Shell) acessem o banco
az sql server firewall-rule create \
  --resource-group "$RESOURCE_GROUP" \
  --server "$SQL_SERVER" \
  --name AllowAzureServices \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 0.0.0.0

# Plano de servico e Web App
az appservice plan create \
  --name "$APP_PLAN" \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --sku B1 \
  --is-linux

az webapp create \
  --name "$WEBAPP" \
  --resource-group "$RESOURCE_GROUP" \
  --plan "$APP_PLAN" \
  --runtime "$RUNTIME"

# Variaveis de ambiente do Web App (dados sensiveis nao ficam no codigo)
JDBC_URL="jdbc:sqlserver://$SQL_SERVER.database.windows.net:1433;database=$SQL_DB;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"

az webapp config appsettings set \
  --name "$WEBAPP" \
  --resource-group "$RESOURCE_GROUP" \
  --settings \
    SPRING_DATASOURCE_URL="$JDBC_URL" \
    SPRING_DATASOURCE_USERNAME="$SQL_USER" \
    SPRING_DATASOURCE_PASSWORD="$DB_PASSWORD"

echo ""
echo "Infra criada."
echo "  Web App : https://$WEBAPP.azurewebsites.net"
echo "  Servidor: $SQL_SERVER.database.windows.net  (banco: $SQL_DB, usuario: $SQL_USER)"
echo ""
echo "Proximo passo: executar o script.sql no banco (Portal > SQL database > Query editor)."
