#!/bin/bash -eu

echo "update docs"
./lein.sh codox
cd docs
git commit --allow-empty -m "updates docs [skip ci]"
git push
