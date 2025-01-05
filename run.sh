#!/usr/bin/env bash

export githubAppClientId=$(cat  ~/.gitapp/opensponsor/client_id)
export githubAppClientSecret=$(cat  ~/.gitapp/opensponsor/client_secret)

export http_proxy=http://127.0.0.1:1087;export https_proxy=http://127.0.0.1:1087;export ALL_PROXY=socks5://127.0.0.1:1080

./gradlew quarkusDev
