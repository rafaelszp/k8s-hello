package szp.rafael.k8s.status;

import javax.json.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Status {

  public static final Pattern uptimePattern = Pattern
//		  .compile("^.*\\s+load\\s+average:\\s+([\\d\\.]+),\\s+([\\d\\.]+),\\s+([\\d\\.]+)$");
		  .compile("(.*?)(load\\saverage\\:\\s)(\\d+\\.\\d+)(\\,\\s)(\\d+\\.\\d+)(\\,\\s)(\\d+\\.\\d+)");

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
//	SystemInfo systemInfo = new SystemInfo();
//	Properties props = PropertiesUtil.loadProperties("oshi.json.properties");
	JsonObjectBuilder status = Json.createObjectBuilder()
			.add("operatingSystem", Json.createObjectBuilder()
					.add("",sys.getName())
					.add("arch",sys.getArch())
					.add("version",sys.getVersion())
					.add("lastLoadAverage",sys.getSystemLoadAverage())
					.add("loadAverage",getLoad(uptime()))
					.add("availableProcessors",sys.getAvailableProcessors())
			)
			.add("memoryPool", memoryPoolArray)
			.add("garbageCollector",gcArray);

	return status.build();

  }


  public static JsonArray getLoad(String uptimeCmdResult) {

	JsonArrayBuilder load = Json.createArrayBuilder();

	String[] arr = uptimeCmdResult.replaceAll("^.*?load\\s+average\\:\\s+", "").replaceAll("\n", "").split(",");

	if (arr.length>0) {
	  double oneMinuteLoadAvg = Double.parseDouble(arr[0]);
	  double fiveMinuteloadAvg = Double.parseDouble(arr[1]);
	  double fifteenMinuteLoadAvg = Double.parseDouble(arr[2]);
	  load.add(oneMinuteLoadAvg).add(fiveMinuteloadAvg).add(fifteenMinuteLoadAvg);
	}
	return load.build();
  }

  public static String uptime() {
	ProcessBuilder pb = null;
	String cmd="/usr/bin/uptime";
	boolean waitForResult=true;

	pb = new ProcessBuilder("/bin/sh", "-c", cmd);
	pb.redirectErrorStream(true);
	Writer wtr = null;
	try {
	  Process process = pb.start();
	  if (waitForResult) {
		InputStream stream = process.getInputStream();

		if (stream != null) {
		  wtr = new StringWriter();

		  char[] crunchifyBuffer = new char[2048];
		  try {
			Reader crunchifyReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			int count;
			while ((count = crunchifyReader.read(crunchifyBuffer)) != -1) {
			  wtr.write(crunchifyBuffer, 0, count);
			}
		  } finally {
			stream.close();
		  }
		  wtr.toString();
		  stream.close();
		}
	  }
	} catch (Exception e) {
	  System.err.println("Error Executing tcpdump command" + e);
	}
	return wtr.toString();
  }

}
