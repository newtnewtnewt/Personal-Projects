#!/usr/bin/env bash

cd dist/
FILES=$(ls *.js)

for FILE in ${FILES}
do
    FILE="${FILE%.*}"
    zip "${FILE}.zip" "${FILE}.js" "${FILE}.js.map"
done
