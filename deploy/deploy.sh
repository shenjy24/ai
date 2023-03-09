#!/usr/bin/bash

# shellcheck disable=SC2164
cd /root/workspace/ai/ai

git pull
mvn clean package -Dmaven.test.skip=true

docker build -f Dockerfile -t ai .

docker run -d -p 18080:18080 -e TZ=Asia/Shanghai --name ai -v /root/workspace/ai/logs:/ai/logs -v /root/workspace/ai/conf/application.yml:/ai/conf/application.yml -v /root/workspace/ai/ai/target/ai.jar:/ai/jar/app.jar ai

docker logs ai