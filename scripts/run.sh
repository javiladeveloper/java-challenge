#!/bin/bash

# Navegar al directorio del proyecto
cd "$(dirname "$0")/.."

# Ejecutar la aplicación
mvn spring-boot:run

# Verificar si la aplicación se ejecutó correctamente
if [ $? -eq 0 ]; then
  echo "Aplicación ejecutándose!"
else
  echo "Error al ejecutar la aplicación!"
  exit 1
fi
