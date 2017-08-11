import pytest
import requests


@pytest.fixture()
def jenkins_url():
    return "http://127.0.0.1/api/json"


def test_is_online(jenkins_url):
    requests.get(jenkins_url, timeout=5).raise_for_status()
