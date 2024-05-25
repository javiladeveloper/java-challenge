#!/bin/bash

# Navegar al directorio del proyecto
cd "$(dirname "$0")/.."

# Verificar si el puerto 8080 está en uso
if lsof -i:8080 -t >/dev/null; then
  echo "El puerto 8080 ya está en uso. Por favor, cierre la aplicación que está utilizando este puerto antes de continuar."
  exit 1
fi

# Ejecutar la aplicación
./mvnw spring-boot:run

# Verificar si la aplicación se ejecutó correctamente
if [ $? -eq 0 ]; then
  echo "Aplicación ejecutándose!"
else
  echo "Error al ejecutar la aplicación!"
  exit 1
fi