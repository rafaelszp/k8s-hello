package szp.rafael.k8s.kubeclient;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class RegistryTest {

  //https://jsosic.wordpress.com/2017/01/23/deleting-images-from-docker-registry/ TO DELETE

  public static final String REGISTRY_URL = "http://10.21.154.64:30400/v2";

  public static void main(String... args) throws UnirestException {
	listRepositories();

  }

  private static void listRepositories() throws UnirestException {
	HttpResponse<JsonNode> json = Unirest.get(REGISTRY_URL+"/_catalog")
			.header("accept", "application/json")
			.asJson();
	int len = json.getBody().getObject().getJSONArray("repositories").length();

	List<String> repos = new ArrayList<>();
	for(int i=0;i<len;i++){
	  repos.add(json.getBody().getObject().getJSONArray("repositories").get(i).toString());
	}
	out.println("REPOSITORIES");
	out.println(json.getBody().toString());
	System.out.println("=================");
	repos.forEach(s -> {
	  try {
		System.out.printf("Listing tags for %s\n",s);
		listTags(s);
	  } catch (UnirestException e) {
		e.printStackTrace();
	  }
	});
  }

  private static void listTags(String tagName) throws UnirestException {
	HttpResponse<JsonNode> json = Unirest.get(REGISTRY_URL+"/"+tagName+"/tags/list")
			.header("accept", "application/json")
			.asJson();

	out.println(json.getBody().toString());

  }
}
