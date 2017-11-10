FROM openjdk:8u151-jdk
ADD target/clock-service-swarm.jar /app/clock-service-swarm.jar
#ADD target/clock-service-swarm.jar /deployments/clock-service-swarm.jar
EXPOSE 8080
CMD java -jar /app/clock-service-swarm.jar