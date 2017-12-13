package szp.rafael.k8s.hz;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class HazelcastInitializer {

  Logger logger = Logger.getLogger(this.getClass().getName());

  @EJB
  HazelcastConfigurator configurator;

  @PostConstruct
  public void init(){

	String environment = System.getProperty("environment");
	if(environment==null){
	  System.setProperty("environment","local");
	}
	configurator.configure();

	logger.log(Level.INFO,"Hazelcast configurator called "+Thread.currentThread().getName());
  }


}
