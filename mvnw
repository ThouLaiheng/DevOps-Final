#!/bin/sh
# Maven Wrapper
MAVEN_HOME="${MAVEN_HOME:-$HOME/.m2/wrapper/dists}"
MAVEN_OPTS="${MAVEN_OPTS:-}"
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
if [ -f "$PROJECT_DIR/.mvn/wrapper/maven-wrapper.jar" ]; then
  java $MAVEN_OPTS -jar "$PROJECT_DIR/.mvn/wrapper/maven-wrapper.jar" "$@"
else
  echo "Maven wrapper jar not found" >&2
  exit 1
fi
