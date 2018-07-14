docker run -d -p 9091:9091 --name prom-gateway prom/pushgateway
docker run -d -p 9090:9090 -v /home/lasitha/IdeaProjects/contiki-gateway/prometheus:/etc/prometheus/ --link prom-gateway --name prometheus prom/prometheus
