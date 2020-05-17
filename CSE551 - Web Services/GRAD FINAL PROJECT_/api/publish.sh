#!/usr/bin/env bash

aws lambda update-function-configuration --function-name Foo --handler index.handler
aws lambda update-function-code --function-name Foo --zip-file fileb://dist/index.zip
