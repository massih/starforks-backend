FROM adoptopenjdk/openjdk16:jre-16.0.1_9-alpine

WORKDIR /app

COPY target/starforks-0.1.jar .

CMD java -jar starforks-0.1.jar

#ENTRYPOINT[]