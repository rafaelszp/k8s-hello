# k8s-hello

## Dev JAVA_OPTS

```
export JAVA_OPTS="-Dswarm.port.offset=0 \
-Djava.awt.headless=true \
-Xms40m -Xmx150m \
-XX:MaxMetaspaceSize=120m \
-Denvironment=<kubernetes|local>"
```

## Startup

### IDE

Just run Class: `org.wildfly.swarm.Swarm`

### Uber jar

```
java -jar $JAVA_OPTS $target/hello-swarm.jar 
```