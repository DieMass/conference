FROM gradle:jdk11 AS build
COPY . /build
WORKDIR /build
RUN gradle build -x test --no-daemon

FROM openjdk:11
COPY --from=build /build/build/libs/*.jar /app/app.jar
WORKDIR /app
CMD ["java",  "-jar", "app.jar"]