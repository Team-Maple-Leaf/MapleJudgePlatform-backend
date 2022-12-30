#/bin/bash

maplenet=maple-leaf-be-db
docker network ls | grep $maplenet

code=$?
if [ $code -eq 1 ] ; then
  echo Make docker network!
  docker network create $maplenet
else
  echo Network already exists!
fi

