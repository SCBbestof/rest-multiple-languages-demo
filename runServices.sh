#!/bin/sh


function ctrl_c() {
    echo "Shutting down services..."

    cd java-gateway
        ./mvnw spring-boot:stop
    :

    cd jprocessor
        ./mvnw spring-boot:stop
    :

    curl -XPOST localhost:8083/shutdown
}

trap ctrl_c INT

cd java-gateway
    ./mvnw clean install
    ./mvnw spring-boot:start
:
cd jprocessor
    ./mvnw clean install
    ./mvnw spring-boot:start
:
cd pyesser
    nohup flask run --host=0.0.0.0 --port=8083 &
:
tail -f /dev/null
~
~
~
~
