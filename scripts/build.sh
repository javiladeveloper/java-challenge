#!/bin/bash

# Navegar al directorio del proyecto
cd "$(dirname "$0")/.."

# Compilar y empaquetar la aplicación
./mvnw clean install

# Verificar si el build fue exitoso
if [ $? -eq 0 ]; then
  echo "Build exitoso!"
else
  echo "Error en el build!"
  exit 1
fi
