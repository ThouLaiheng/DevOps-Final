#!/bin/bash
service nginx start
service ssh start
java -jar /app/app.jar
