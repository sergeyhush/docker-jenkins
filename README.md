docker-jenkins
==============

REPO=sergey/jenkins
docker build -t $REPO .
ID=$(docker run -p 48080:8080 -d $REPO)
docker ps | grep $ID
docker logs $ID
curl -i http://localhost:48080/api/xml