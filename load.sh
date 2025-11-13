#!/usr/bin/env bash
# 100 llamadas, guarda status y tiempo total
inicio=$(date +%s%N)
for i in {1..100}; do
    curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8081/Person/$i
done > status.txt
fin=$(date +%s%N)
duracion=$(( (fin - inicio) / 1000000 ))  # ms
echo "Duración total: ${duracion} ms"
echo "Peticiones 400:"
grep -c "400" status.txt
echo "Peticiones 200:"
grep -c "200" status.txt