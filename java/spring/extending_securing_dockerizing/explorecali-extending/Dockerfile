FROM amazoncorretto:17-alpine

WORKDIR /

ADD build/libs/explorecali*SNAPSHOT.jar /explorecali.jar

ENTRYPOINT ["java", "-jar", "/explorecali.jar"]