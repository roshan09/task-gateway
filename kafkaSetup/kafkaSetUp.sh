
#!/bin/bash  


echo welcome1

cd ~/Downloads/kafka_2.12-2.3.0/
# start zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties & bin/kafka-server-start.sh config/server.properties


# create a topic
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic $1

