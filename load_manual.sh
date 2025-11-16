#!/usr/bin/env bash
# 1000 llamadas a /manual
start=$(date +%s%N)
for i in {1..1000}; do
    curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8081/manual
done > status_manual.txt
end=$(date +%s%N)
dur_ms=$(( (end - start) / 1000000 ))
echo "Manual servlet: ${dur_ms} ms total"