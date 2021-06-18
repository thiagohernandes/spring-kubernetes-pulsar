# Before Start
    * install Minikube: https://minikube.sigs.k8s.io/docs/start/
    * follow the instructions: https://pulsar.apache.org/
    * use: Kubernetes or Docker initilization 

# Kubernetes
    * after install, important commands:

    $ kubectl get pods -n pulsar (get pulsar-mini-toolset-0 to create partitions in bash mode )
    $ kubectl exec -it -n pulsar pulsar-mini-toolset-0 -- /bin/bash

    [ATTENTION!] format/pattern pulsar:

    TENANTS/NAMESPACES/TOPICS
    
    [TENANT]
    $ root@pulsar-mini-toolset-0:/pulsar# bin/pulsar-admin tenants create playing 

    [NAMESPACE]
    $ root@pulsar-mini-toolset-0:/pulsar# bin/pulsar-admin namespaces create playing/api

    [TOPIC]
    $ root@pulsar-mini-toolset-0:/pulsar# bin/pulsar-admin topics create-partitioned-topic playing/api/our-objects -p 2

    [CERTIFY - CREATION]
    $ root@pulsar-mini-toolset-0:/pulsar# bin/pulsar-admin topics list-partitioned-topics playing/api
    result: "persistent://playing/api/our-objects"

# Docker

    $ docker run -it \
    -p 6650:6650 \
    -p 8080:8080 \
    --mount source=pulsardata,target=/pulsar/data \
    --mount source=pulsarconf,target=/pulsar/conf \
    apachepulsar/pulsar:2.8.0 \
    bin/pulsar standalone


