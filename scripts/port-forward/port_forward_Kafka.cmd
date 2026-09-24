@echo off

kubectl port-forward -n common service/kafka 9092:9092

pause
