FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/tostrip-1.0.jar /app/tostrip-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tostrip-1.0.jar"]