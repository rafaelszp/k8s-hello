FROM openjdk:8-alpine
ADD target/k8s-hello-swarm.jar /app/k8s-hello-swarm.jar
EXPOSE 8080
CMD java -jar $(printenv JAVA_OPTS) /app/k8s-hello-swarm.jar