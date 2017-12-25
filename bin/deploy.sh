#!/usr/bin/env bash

set -eu

export ANSIBLE_NOCOWS=1

ansible-playbook -i "localhost," ansible/deploy.yml "$@"
