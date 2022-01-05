FROM gradle:7.3.1-jdk-alpine as BUILDER
# Not necessary
ARG VERSION=0.0.1-SNAPSHOT
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# COPY build.gradle /home/gradle/src
# COPY src /home/gradle/src

# May need ./gradlew
RUN gradle clean build
# COPY /home/gradle/src/build/libs/*.jar /home/gradle/src/build/libs/application.jar

FROM amazoncorretto:11-alpine
WORKDIR /app/

COPY --from=BUILDER /home/gradle/src/build/libs/booting-web-0.0.1-SNAPSHOT.jar /app/application.jar
CMD java -jar /app/application.jar