#!/bin/sh
mvn clean package && docker build -t kwetter/Kwetter .
docker rm -f Kwetter || true && docker run -d -p 8080:8080 -p 4848:4848 --name Kwetter kwetter/Kwetter 
