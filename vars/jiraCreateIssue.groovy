def call(Map config=[:]) {

    def CURRENT_DATE = sh(script: "date +%Y%m%d", returnStdout: true).trim()

  sh """

echo "Today: ${CURRENT_DATE}"
echo "Creating new Jira Ticket: "

curl -k --request POST --url 'https://${config.JIRA_URL}/rest/api/2/issue' \
  -H 'Authorization: Bearer ${config.JIRA_TOKEN}' \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '{"fields": {"project":{"key": "${config.JIRA_PROJECT}"},"summary": "${config.JIRA_PROJECT_SUMMARY} - ${CURRENT_DATE}","description": "${config.JIRA_PROJECT_DESCRIPTION}","issuetype": {"name": "${config.JIRA_ISSUE_TYPE}"}}}'

        """
}

