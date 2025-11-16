#!/usr/bin/env bash
# 1000 llamadas a /Person/1
start=$(date +%s%N)
for i in {1..1000}; do
    curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8081/Person/1
done > status_spring.txt
end=$(date +%s%N)
dur_ms=$(( (end - start) / 1000000 ))
echo "Spring MVC: ${dur_ms} ms total"