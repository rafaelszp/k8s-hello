FROM openjdk:8u131-jdk-alpine
ADD target/k8s-hello-swarm.jar /app/k8s-hello-swarm.jar
EXPOSE 8080
CMD java -jar /app/k8s-hello-swarm.jar