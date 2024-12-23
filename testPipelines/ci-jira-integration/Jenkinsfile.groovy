@Library('shared-library') _
def config = [
    JIRA_URL: '<JIRA_URL>',
    JIRA_TOKEN: '<TOKEN>',
    JIRA_PROJECT: '<PROJECT ID>',
    JIRA_PROJECT_DESCRIPTION: ">DESCRIPTION>",
    JIRA_PROJECT_SUMMARY: "<SUMMARY>",
    JIRA_ISSUE_TYPE: "Task",
    ]
pipeline {
    agent {
        kubernetes {
            yaml '''
                apiVersion: v1
                kind: Pod
                spec:
                  containers:
                    - name: shell
                      image: curlimages/curl:latest
                      command:
                        - sleep
                      args:
                        - infinity
                 '''
            defaultContainer 'shell'
        }
    }
    stages {
        stage('CreateJiraIssue') {
            steps {
                jiraCreateIssue(config)
            }
        }
    }
}
