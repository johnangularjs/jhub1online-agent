#!/bin/bash
JAVA_PATH="java"
DEBUG="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4000,suspend=n"
(
until $JAVA_PATH -jar JHUB1OnlineAgent.jar; do
echo "$(date +%Y-%m-%d_%H:%M) JHUB1OnlineAgent exit with code $?. Respawning..." >> ./log/AgentCrash.log
sleep 5
done
) &