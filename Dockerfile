FROM openjdk:17-oracle

WORKDIR /app

COPY target/unitech-desafio-0.0.1-SNAPSHOT.jar /app/unitech-desafio.jar

ENTRYPOINT ["java", "-jar", "teste-fourd.jar"]