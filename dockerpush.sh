cd docker
pwd
sudo service docker start
sudo docker build .
$(aws ecr get-login --region us-west-2)
sudo docker build -t kmt .
sudo docker tag kmt:latest 142328397574.dkr.ecr.us-west-2.amazonaws.com/kmt:latest
sudo docker push 142328397574.dkr.ecr.us-west-2.amazonaws.com/kmt:latest
