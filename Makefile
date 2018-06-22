TAG_PREFIX = cumulus-docker-
DOCKER_REPO = netqbuild-02.mvlab.cumulusnetworks.com/repository/docker-jenkins-base

.PHONY: jenkins-master
jenkins-master:
	docker build -t $(TAG_PREFIX)$@ -t $(DOCKER_REPO)/$(TAG_PREFIX)$@ master/.
	docker push $(DOCKER_REPO)/$(TAG_PREFIX)$@
