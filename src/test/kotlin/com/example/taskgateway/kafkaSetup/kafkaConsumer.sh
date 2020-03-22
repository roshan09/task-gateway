
cd ~/Downloads/kafka_2.12-2.3.0

# consumer on topic
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic $1 --from-beginning
