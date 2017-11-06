node{
    def maven = tool 'mvn-3.5.2'
    def mvn = "${maven}/bin/mvn"
    
    
    stage('Docker Info'){
        echo "\n\n\nDisplaying version\n\n\n"
        sh 'docker version'    
        sh "echo '${env.REGISTRY}'"
    }
    
    stage('Maven Info'){
        echo "\n\n\nDisplay maven version\n\n\n"
        sh "${mvn} --version"        
    }
    
    stage('pull and clean'){
        try{
            echo "Downloading the project"
            git poll:true, branch: 'master', url: 'https://github.com/rafaelszp/k8s-hello.git'
            sh "${mvn} clean"
        }catch (Exception e){
            //notifyFailure(e.getMessage())
            echo e.getMessage()
            throw e

        }
    }
    
    stage('MavenBuild'){
        echo "\n\n\nBuilding project...\n\n\n"
        try{
            sh "${mvn} package -DskipTests"

        }catch(Exception error){
            
            throw error
        }
    }

    stage('DockerBuild'){
        echo "\n\n\nBuilding docker image\n\n\n"
        sh "docker build --tag rafaelszp/k8s-hello ."
    }

    stage('DockerPush'){
        sh "docker tag rafaelszp/k8s-hello ${env.REGISTRY}/k8s-hello"
        sh "docker tag rafaelszp/k8s-hello ${env.REGISTRY}/k8s-hello"
    }
    
    

}