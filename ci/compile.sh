#!/bin/bash -e

echo "cleaning build folders"
./lein.sh clean

echo "building jar"
./lein.sh jar
