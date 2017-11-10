#FROM openjdk:8u131-jdk-alpine problems with networking ;(
# FROM fabric8/java-centos-openjdk8-jdk ##Problems with logmanager ;(
FROM openjdk:8u151-jdk
ADD target/k8s-hello-swarm.jar /app/k8s-hello-swarm.jar
EXPOSE 8080
CMD java -jar /app/k8s-hello-swarm.jar