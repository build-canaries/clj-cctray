#!/bin/bash -eu

echo "update docs"
./lein.sh codox
cd docs
git config --global user.email "team@nevergreen.io"
git config --global user.name "@BuildCanaries"
git commit --allow-empty -m "update docs [skip ci]"
git push
