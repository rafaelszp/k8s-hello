# clock-service

##  Dev JAVA_OPTS 

```
export JAVA_OPTS="-Dswarm.port.offset=1010 \
-Djava.awt.headless=true \
-Xms40m -Xmx120m \
-XX:MaxMetaspaceSize=120m"
```

## Startup

### IDE

Just run Class: `org.wildfly.swarm.Swarm`

### Uber jar

```
java -jar $JAVA_OPTS $target/clock-service-swarm.jar 
```