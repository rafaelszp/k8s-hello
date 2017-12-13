package szp.rafael.k8s.hz;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class HazelcastConfigurator {



  Logger logger = Logger.getLogger(this.getClass().getName());
  @Asynchronous
  public void configure() {
	try {
	  logger.log(Level.INFO,"Loading hazelcast"+Thread.currentThread().getName());
	  Thread.sleep(3000);
	  Properties properties = new Properties();

	  XmlConfigBuilder builder = new XmlConfigBuilder();
	  builder.setProperties(System.getProperties());
	  Config config = builder.build();
	  HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
	} catch (InterruptedException e) {  e.printStackTrace();  }

  }
}
