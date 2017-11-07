FROM openjdk:8u131-jdk-alpine
ADD hello/target/k8s-hello-swarm.jar /app/k8s-hello-swarm.jar
EXPOSE 8080
CMD java -jar -Xms384m -Xmx500m /app/k8s-hello-swarm.jar