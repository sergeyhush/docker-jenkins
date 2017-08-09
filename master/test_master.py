import pytest
import requests


@pytest.fixture()
def jenkins_url():
    return "http://127.0.0.1:80"


def is_url_up(u):
    return requests.get(u).status_code == requests.codes.ok


def test_is_online(jenkins_url):
    assert is_url_up(jenkins_url)
