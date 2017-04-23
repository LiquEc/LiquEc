#!/usr/bin/env bash

export GIT_TAG=$(git describe --abbrev=0 --tags)
export GITHUB_USER=LiquEc
export GITHUB_REPO=LiquEc

echo "User/Organisation: $GITHUB_USER, Repo: $GITHUB_REPO, Tag: $GIT_TAG"
