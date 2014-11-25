#!/bin/bash

while inotifywait -q -r -e modify --exclude '#$' .; do
    sleep 1
    mvn -q install && echo "Ok."
done

