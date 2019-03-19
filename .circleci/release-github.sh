#!/bin/bash -eu

version=$(head -n 1 project.clj | cut -d'"' -f 2)

echo "Creating a GitHub release with tag [${version}]"

responseJson=$(curl \
  -u ${GITHUB_USERNAME}:${GITHUB_TOKEN} \
  -H "Content-Type: application/vnd.github.v3+json" \
  -d "{\"tag_name\": \"v${version}\", \"target_commitish\": \"master\", \"name\": \"v${version}\", \"draft\": true, \"prerelease\": false}" \
  https://api.github.com/repos/build-canaries/clj-cctray/releases)

echo "Got response [${responseJson}]"

uploadUrl=$(echo ${responseJson} | jq -r '.upload_url' | sed "s|{?name,label}||")

jar="clj-cctray-${version}.jar"

echo "Adding the ${jar} as an asset using URL [${uploadUrl}]"

curl \
  -u ${GITHUB_USERNAME}:${GITHUB_TOKEN} \
  -H "Content-Type: application/zip" \
  --data-binary "@./target/${jar}" \
  "${uploadUrl}?name=${jar}"
