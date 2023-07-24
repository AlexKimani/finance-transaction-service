# Add maven as part of the docker image to build project
FROM maven:3.8.7-openjdk-18-slim AS build
RUN mkdir -p /var/log/
RUN chmod a+rwx -R /var/log/
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN --mount=type=cache,target=/root/.m2,rw mvn dependency:go-offline -f /usr/src/app/pom.xml clean package

# Create the run target service
FROM openjdk:17.0.1-jdk-slim
MAINTAINER joealexkimani
COPY --from=build /usr/src/app/target/finance-transaction-service-1.0.0.jar /usr/app/finance-transaction-service-1.0.0.jar
ENTRYPOINT ["java","-jar","/usr/app/finance-transaction-service-1.0.0.jar"]