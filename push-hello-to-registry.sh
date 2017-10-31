#!/usr/bin/env bash

REGISTRY=$1

if [ -z $REGISTRY ]; then
    echo "syntax: sh push-hello-to-registry.sh REGISTRY:PORT"
    exit 1
fi

docker tag rafaelszp/k8s-hello $1/k8s-hello
docker push $1/k8s-hello