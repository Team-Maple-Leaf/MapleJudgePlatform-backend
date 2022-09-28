FROM openjdk:11-jdk AS builder
COPY ./backend/gradlew .
COPY ./backend/gradle gradle
COPY ./backend/build.gradle .
COPY ./backend/settings.gradle .
COPY ./backend/src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:11-jdk
COPY --from=builder ./build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
