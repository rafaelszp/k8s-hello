#FROM openjdk:8u131-jdk-alpine
#FROM fabric8/java-centos-openjdk8-jdk
FROM openjdk:8u151-jdk
ADD target/clock-service-swarm.jar /app/clock-service-swarm.jar
#ADD target/clock-service-swarm.jar /deployments/clock-service-swarm.jar
EXPOSE 8080
CMD java -jar /app/clock-service-swarm.jar