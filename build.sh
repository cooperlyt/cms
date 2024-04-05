#!/usr/bin/env bash

cd target/dockerfile \
  &&
  sh docker buildx build . --platform linux/amd64,linux/arm64 --push -t dgsspfdjw.org.cn:443/cms:0.0.2