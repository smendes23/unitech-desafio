FROM openjdk:17-oracle

WORKDIR /app

COPY target/unitech-0.0.1-SNAPSHOT.jar /app/unitech.jar

ENTRYPOINT ["java", "-jar", "teste-fourd.jar"]