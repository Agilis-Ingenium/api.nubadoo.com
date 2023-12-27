# Build the app using Maven
FROM maven:3.6.0-jdk-8-alpine

# Download dependencies
ADD pom.xml /
RUN mvn verify clean

# Build after dependencies are down so it wont redownload unless the POM changes
ADD . /
RUN mvn -B clean package -DskipTests

# Use the JAR file used in the first part and copy it across ready to RUN
FROM openjdk:8-jdk-alpine
WORKDIR /root/

## COPY packaged JAR file and rename as app.jar 
COPY --from=0 /target/*-jar-with-dependencies.jar app.jar 

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]
