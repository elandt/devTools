FROM gradle:9-jdk25 AS build

RUN mkdir /workdir
WORKDIR /workdir
COPY build.gradle /workdir
COPY src /workdir/src

RUN gradle clean bootJar

FROM eclipse-temurin:25-jre-noble
EXPOSE 8080

RUN mkdir /deploy
COPY --from=build /workdir/build/libs/*.jar /deploy/app.jar

CMD ["java", "-jar", "/deploy/app.jar"]