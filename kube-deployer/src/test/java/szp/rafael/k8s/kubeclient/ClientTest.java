package szp.rafael.k8s.kubeclient;


import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class ClientTest {

  private static final String KUBERNTES_NODE_ADDRESS = "https://10.21.154.64:6443";
  private static KubernetesClient client;

  public static void main(String... args){
    config();
    listPods();

//    client.extensions().deployments().

  }

  private static void listPods() {
    PodList pods = client.pods().list();
    System.out.println("PODS LIST");
    pods.getItems().forEach(pod -> {
      System.out.println(pod.getMetadata().getName());
    });
  }

  private static void config() {
    Config config = new ConfigBuilder().withMasterUrl(KUBERNTES_NODE_ADDRESS).build();
    client = new DefaultKubernetesClient(config);
  }

}
