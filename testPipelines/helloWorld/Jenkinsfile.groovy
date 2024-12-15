@Library('shared-library') _
def config = [name: 'PQ Tester', dayOfWeek: 'Sunday']
pipeline {
    agent {
        kubernetes {
            yaml '''
                apiVersion: v1
                kind: Pod
                spec:
                  containers:
                    - name: python
                      image: python:3.9-slim
                      command:
                        - sleep
                      args:
                        - infinity
                 '''
            defaultContainer 'python'
        }
    }
    stages {
        stage('Example') {
            steps {
                helloWorld(config)
            }
        }
    }
}
