#!/usr/bin/bash

# shellcheck disable=SC2164
cd /root/workspace/ai/ai

git pull
mvn clean package -Dmaven.test.skip=true

docker restart ai