
cd ~/Downloads/kafka_2.12-2.3.0

#produce on topic
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic $1
