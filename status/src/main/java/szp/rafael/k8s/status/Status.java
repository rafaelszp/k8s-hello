package szp.rafael.k8s.status;

import oshi.json.SystemInfo;
import oshi.json.util.PropertiesUtil;

import javax.json.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

public class Status {

  public static JsonObject get(){

	OperatingSystemMXBean sys = ManagementFactory.getOperatingSystemMXBean();

	JsonArrayBuilder statusBuilder = Json.createArrayBuilder();
	ManagementFactory.getMemoryPoolMXBeans().stream().forEach(m -> {

	  JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
			  .add("name",m.getName())
			  .add("type",m.getType().toString())
			  .add("usage", Json.createObjectBuilder()
					  .add("init",m.getUsage().getInit())
					  .add("used",m.getUsage().getUsed())
					  .add("max",m.getUsage().getMax()))
			  .add("peak", Json.createObjectBuilder()
					  .add("init",m.getPeakUsage().getInit())
					  .add("used",m.getPeakUsage().getUsed())
					  .add("max",m.getPeakUsage().getMax()));
	  Optional.ofNullable(m.getCollectionUsage()).ifPresent(memoryUsage -> {
		objectBuilder.add("collections",Json.createObjectBuilder()
				.add("init",m.getCollectionUsage().getInit())
				.add("used",m.getCollectionUsage().getUsed())
				.add("max",m.getCollectionUsage().getMax()));
	  });

	  JsonObject metric = objectBuilder.build();
	  statusBuilder.add(metric);
	});

	JsonArrayBuilder gcArray = Json.createArrayBuilder();
	ManagementFactory.getGarbageCollectorMXBeans().stream().forEach(gc -> {
	  JsonObjectBuilder mpool = Json.createObjectBuilder()
			  .add("collectionCount",gc.getCollectionCount())
			  .add("collectionTime",gc.getCollectionTime())
			  .add("name",gc.getName())
			  .add("canonicalName",gc.getName());
	  JsonArrayBuilder gcPoolNames = Json.createArrayBuilder();
	  Arrays.asList(gc.getMemoryPoolNames()).stream().forEach(s -> gcPoolNames.add(s));
	  mpool.add("memoryPoolNames",gcPoolNames);
	  gcArray.add(mpool);
	});


	JsonArray memoryPoolArray = statusBuilder.build();
	SystemInfo systemInfo = new SystemInfo();
	Properties props = PropertiesUtil.loadProperties("oshi.json.properties");
	JsonObjectBuilder status = Json.createObjectBuilder()
			.add("operatingSystem",systemInfo.toJSON(props))
			.add("memoryPool", memoryPoolArray)
			.add("garbageCollector",gcArray);

	return status.build();

  }

}
