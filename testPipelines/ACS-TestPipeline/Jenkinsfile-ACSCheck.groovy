@Library('shared-library') _
def config = [
    ROX_API_TOKEN: <TOKEN>
    ENDPOINT: <ENDPOINT>,
    DEBUG: 'false',
    SKIP_TLS_VERIFY: 'false',
    IMAGE_REGISTRY: 'registry.access.redhat.com',
    IMAGE_NAME: 'ubi9@sha256:089bd3b82a78ac45c0eed231bb58bfb43bfcd0560d9bba240fc6355502c92976',
    SCAN_OUTPUT_FORMAT: 'table',
    ]
pipeline {
    agent {
        kubernetes {
            yaml '''
                apiVersion: v1
                kind: Pod
                spec:
                  containers:
                    - name: roxctl
                      image: registry.access.redhat.com/ubi9@sha256:089bd3b82a78ac45c0eed231bb58bfb43bfcd0560d9bba240fc6355502c92976
                      command:
                        - sleep
                      args:
                        - infinity
                      env:
                        - name: ROX_API_TOKEN
                          value: <TOKEN>
                 '''
            defaultContainer 'roxctl'
        }
    }
    stages {
        stage('ScanImage') {
            steps {
                ACSImageCheck(config)
            }
        }
    }
}
