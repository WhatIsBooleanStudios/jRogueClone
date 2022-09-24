#!/bin/zsh

while true
do
./app/build/install/app/bin/app
if [ $? -ne 0 ]
then
	exit 1
fi
done
