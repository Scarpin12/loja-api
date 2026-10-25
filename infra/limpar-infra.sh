#!/bin/bash
# Remove TUDO que foi criado (so execute depois que o professor corrigir a entrega).
RM="rm00000"                       # <-- mesmo RM do criar-infra.sh
az group delete --name "rg-loja-$RM" --yes --no-wait
