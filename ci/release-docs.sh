#!/bin/bash -eu

echo "update docs"
./lein.sh codox
cd docs
git commit --allow-empty -m "[CI] updates docs"
git push
