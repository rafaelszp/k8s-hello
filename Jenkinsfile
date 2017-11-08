podTemplate(
  label: 'k8s-hello-pipeline', 
  containers: [
    containerTemplate(name: 'maven', image: 'maven:3.5.2-jdk-8-alpine', ttyEnabled: true, command: 'cat'),
    containerTemplate(name: 'helm', image: 'lachlanevenson/k8s-helm:2.7.0', command: 'cat', ttyEnabled: true),
    containerTemplate(name: 'docker', image: 'docker:1.12.6', command: 'cat', ttyEnabled: true),
    containerTemplate(name: 'curl', image: 'byrnedo/alpine-curl', command: 'cat', ttyEnabled: true),    
  ],
  volumes:[
    hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
    hostPathVolume(mountPath: '/root/.m2/repository', hostPath: '/dados/maven_repo'),
  ],
  envVars:[
      envVar(key:'HELM_HOST',value:"tiller-deploy.kube-system.svc.cluster.local:44134"),
      envVar(key:'HELMET_HOST',value:"helmet-helmet-chart.helmet.svc.cluster.local:1323")
  ]
) {

    node('k8s-hello-pipeline') {

        stage('Fetch from git'){
            git poll:true, branch: 'master', url: 'https://github.com/rafaelszp/k8s-hello.git'
        }
        
        def pom = readMavenPom file: 'pom.xml'
        echo "Jenkins Pipeline running this project version: ${pom.version}"
        echo "$REGISTRY URL: ${env.REGISTRY}"

        stage('Maven Stage') {
            
            container('maven') {
                stage('Maven Clean') {
                    sh """
                    mvn -B clean
                    mvn package -DskipTests -Dverify=true
                    """
                }
            }
        }

        stage("Docker Stage"){
            container('docker'){
                stage('Docker info'){
                    sh """
                    docker version
                    echo ${env.REGISTRY}
                    """
                }
                stage('Docker Build'){
                    sh """
                    echo 'Project version: ${pom.version}'
                    docker build -t rafaelszp/k8s-hello:${pom.version} -t rafaelszp/k8s-hello:latest .
                    """
                }
                stage('Docker tag and push'){
                    sh """
                    echo 'REGISTRY URL: ${env.REGISTRY}'
                    docker tag rafaelszp/k8s-hello:${pom.version} ${env.REGISTRY}/k8s-hello:${pom.version}
                    docker tag rafaelszp/k8s-hello:latest ${env.REGISTRY}/k8s-hello:latest
                    docker push ${env.REGISTRY}/k8s-hello:${pom.version}
                    docker push ${env.REGISTRY}/k8s-hello:latest
                    """
                }
            }
        }

        stage('Deploying with helm') {

            container('helm') {

                stage('Helm Package'){
                    sh """
                    echo 'Matching Chart.yaml version against project version'
                    sed -i 's/version:.*\$/version: ${pom.version}/g' ./charts/k8s-hello/Chart.yaml
                    echo 'Packaging'
                    helm package ./charts/k8s-hello
                    """
                }
            }

            container('curl'){
                stage('Helm POST'){
                    sh "curl -v -T k8s-hello-${pom.version}.tgz -X PUT http://${env.HELMET_HOST}/upload/"
                }
            }

            container('helm'){
                stage('Helm Install ') {
                    def helmSet="--set image.repository=${env.REGISTRY}/k8s-hello --set image.tag=${pom.version} --set service.type=NodePort"
                    def helmInstall = "helm install --name k8s-hello ${helmSet} ./charts/k8s-hello"
                    def helmUpgrade = "helm upgrade ${helmSet} k8s-hello ./charts/k8s-hello"
                    def currentList=sh (returnStdout: true, script:"helm list k8s-hello |tail -n1")
                    
                    if( currentList!=null && currentList.length()>0){
                        sh "${helmUpgrade}"
                    }else{
                        sh "${helmInstall}"
                    }
                }
            }
        }
    }
}