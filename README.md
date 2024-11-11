# Chapter 12: Monitoring Your Applications: Observability in the Java Ecosystem

This repository contains the code from the book for Chapter 12, Monitoring Your Applications: Observability in the Java Ecosystem. See the [main book page](https://github.com/realworldjava) for the list of all chapter specific repositories.

# How this repository is organized

In this repository, all code is in the main branch in one folder. It is a sample Spring application used for working with the tools in the chapter.

# How to run the examples in this chapter

If you want to use an IDE, import the repository as a Maven project so you have the proper  dependencies.

If you want to use the command line, cd to the directory for that example and run 
```
mvn clean verify
java -jar target/observability-0.0.1-SNAPSHOT.jar
```

# Docker commands

The following Docker commands are mentioned in the chapter and included here for easy copy/pasting:

```
docker pull prom/node-exporter
docker run --name=node_exporter -p 9100:9100 prom/node-exporter

docker pull prom/prometheus
```

### Windows
``` docker run --name prometheus -p 9090:9090 -v .\prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus```

### Mac/Linux
```docker run --name prometheus -p 9090:9090 -v ./prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus ```

# Request.json


Following is a sample request.json payload for the Spring Boot MVC project:   
Request type: POST  
URL: http://localhost:8081/mtg/payment  
Json Body:
```json
[
  {
    "user": {
      "name": "John Jones",
      "location": "Miami, Florida"
    },
    "principal": 250000,
    "years": 30,
    "interest": 6.5
  },
  {
    "user": {
      "name": "Mary Michaels",
      "location": "New York, NY"
    },
    "principal": 100000,
    "years": 30,
    "interest": 6.25
  }
]
```

# Clickable Links from the Further References Section

Prometheus
* [Prometheus documentation](https://prometheus.io/docs/introduction/overview)
* [Prometheus and Linux Node Exporter](https://prometheus.io/download)
* [Windows Exporter](https://github.com/prometheus-community/windows_exporter/releases)
* [Node Exporter flags](https://github.com/prometheus/node_exporter)
* [Windows Exporter flags](https://github.com/prometheus-community/windows_exporter/blob/master/README.md)
* [Alert Manager](https://prometheus.io/download/#alertmanager)

Other Tools
* [Download Docker](https://docs.docker.com/engine/install)
* [Grafana](https://grafana.com/grafana/download)
* [Elastic Search](https://www.elastic.co/downloads/elasticsearch)
* [Logstash](https://www.elastic.co/downloads/logstash)
* [Kibana](https://www.elastic.co/downloads/kibana)
* [7-Zip](https://www.7-zip.org/download.html)
* [OpenTelemetry Documentation](https://opentelemetry.io)
* [Micrometer tracing](https://docs.micrometer.io/tracing/reference/index.html)
* [Jaeger Documentation](https://www.jaegertracing.io)
* [Zipkin Documentation](https://zipkin.io)


