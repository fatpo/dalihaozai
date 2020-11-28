#!/bin/bash
docker build -t ouyangbro/dalihaozaiweb:latest .
docker stop  dalihaozaiweb
docker rm dalihaozaiweb

docker run --name dalihaozaiweb -p 80:80 -d ouyangbro/dalihaozaiweb:latest


docker images|grep none|awk '{print $3 }'|xargs docker rmi
