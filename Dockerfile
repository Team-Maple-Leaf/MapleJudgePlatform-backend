FROM openjdk:11

WORKDIR /app

COPY . .

WORKDIR /app/backend

RUN chmod +x ./gradlew
RUN ./gradlew build

WORKDIR /app/backend/build/libs

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./backend-0.0.1-SNAPSHOT.jar"]
