pipeline {
    agent {
     node { 
        label 'node1'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                echo "Middle Stage"
            }
           // post {
             //   always {
                   // junit 'target/surefire-reports/*.xml'
              //  }
         //   }
        }
        stage('Deliver') {
            steps {
				echo "Testing phase"
                 // sh 'curl -uadmin:AP7GaTD6DrukYDezdWdmw8Zk4h3 -T /home/jenkins/node/workspace/Mule_CICD/target/cicd-1.0.0-SNAPSHOT-mule-application.jar "http://mdcassimsait.southindia.cloudapp.azure.com:8081/artifactory/generic-local/$BUILD_NUMBER/cicd-1.0.0-SNAPSHOT-mule-application.jar"'
		}
        }
    }
}
