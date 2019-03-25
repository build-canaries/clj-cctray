#!/bin/bash -eu

echo "update docs"
./lein.sh codox
git config --global user.email "team@nevergreen.io"
git config --global user.name "@BuildCanaries"
git add .
git commit --allow-empty -m "update docs [skip ci]"
git push
