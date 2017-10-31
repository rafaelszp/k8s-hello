#!/bin/bash

cd hello; mvn clean package -DskipTests
cd ..
docker build -t rafaelszp/k8s-hello .