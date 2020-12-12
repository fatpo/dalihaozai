#!/bin/bash
docker pull docker.io/ouyangbro/dalihaozai:latest

docker-compose down

# 删除无用镜像
docker images|grep none|awk '{print $3 }'|xargs docker rmi

docker-compose up -d
