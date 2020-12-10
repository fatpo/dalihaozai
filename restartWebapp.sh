#!/bin/bash
docker build -t ouyangbro/dalihaozaiweb:latest .
docker stop  dalihaozaiweb
docker rm dalihaozaiweb

docker run --name dalihaozaiweb -p 80:80 -d  --net root_default   --link root_dalihaozai_1:myapp ouyangbro/dalihaozaiweb:latest

# 删除无用镜像
docker images|grep none|awk '{print $3 }'|xargs docker rmi
