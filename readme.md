
#### 运行指令
```
docker run -d -p 18080:18080 -e TZ=Asia/Shanghai --name ai -v /root/workspace/logs:/logs -v /root/workspace/application.yml:/application.yml ai
```