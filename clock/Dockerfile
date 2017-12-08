FROM openjdk:8-alpine
ADD target/clock-service-swarm.jar /app/clock-service-swarm.jar
#ADD target/clock-service-swarm.jar /deployments/clock-service-swarm.jar
EXPOSE 8080
CMD java -jar $(printenv JAVA_OPTS) /app/clock-service-swarm.jar