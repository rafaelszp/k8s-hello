node {
    def maven = tool 'mvn-3.5.2'
    def mvn = "${maven}/bin/mvn"
    
    stage('Docker Info'){
        echo "\n\n\nDisplaying version\n\n\n"
        sh 'docker version'    
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
    
    stage('BUILD'){
        echo "Building project..."
        try{
            sh "${mvn} install -DskipTests"

        }catch(Exception error){
            
            throw error
        }
    }
}