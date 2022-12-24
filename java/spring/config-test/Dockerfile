FROM amazoncorretto:17-alpine

WORKDIR /

ADD build/libs/*SNAPSHOT.jar /config-test.jar

ENTRYPOINT ["java", "-jar", "/config-test.jar"]