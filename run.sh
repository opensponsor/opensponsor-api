#!/usr/bin/env bash

export PATH="/usr/local/opt/openjdk@21/bin:$PATH"
export CPPFLAGS="-I/usr/local/opt/openjdk@21/include"

export githubAppClientId=$(cat  ~/.gitapp/opensponsor/client_id)
export githubAppClientSecret=$(cat  ~/.gitapp/opensponsor/client_secret)

export http_proxy=http://127.0.0.1:1087;export https_proxy=http://127.0.0.1:1087;export ALL_PROXY=socks5://127.0.0.1:1080

./gradlew quarkusDev
