def call(Map config=[:]) {

  sh """

if [ "${config.DEBUG}" != "true" ]; then
  echo "Debugging is disabled"
else
  echo "Debugging is enabled"
  set -x
fi

echo "Download roxctl binary from ACS Central"

curl -s -k -L -H "Authorization: Bearer ${config.ROX_API_TOKEN}" \\
  "https://${config.ENDPOINT}/api/cli/download/roxctl-linux" \\
  --output ./roxctl  \\
  > /dev/null

chmod +x ./roxctl  > /dev/null

if [ "${config.SKIP_TLS_VERIFY}" = "true" ]; then
  export ROX_INSECURE_CLIENT_SKIP_TLS_VERIFY=true
fi

./roxctl version

echo "Scanning the image for vulnerabilities (./roxctl image check -e "${config.ENDPOINT}" --image "${config.IMAGE_REGISTRY}"/"${config.IMAGE_NAME}" --output "${config.SCAN_OUTPUT_FORMAT}")"

./roxctl image check -e "${config.ENDPOINT}" --image "${config.IMAGE_REGISTRY}"/"${config.IMAGE_NAME}" --output "${config.SCAN_OUTPUT_FORMAT}"
        """
 
}
