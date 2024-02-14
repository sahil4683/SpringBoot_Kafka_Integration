http://localhost:8080/send?message=dsjvlvsdlfvsld


##Start zookeeper
>>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

##Start kafka	server
>>.\bin\windows\kafka-server-start.bat .\config\server.properties

##Create topic
>>kafka-topics.bat --create --topic test --bootstrap-server localhost:9092

##Describe topic
>>kafka-topics.bat --describe --topic test --bootstrap-server localhost:9092

##List all topics
>>kafka-topics.bat --list --bootstrap-server localhost:9092

##Start producer
>>kafka-console-producer.bat --topic test --bootstrap-server localhost:9092

##Start consumer
>>kafka-console-consumer.bat --topic test --from-beginning --bootstrap-server localhost:9092

##Delete topic
>>kafka-run-class.bat kafka.admin.TopicCommand --delete --topic test --bootstrap-server localhost:9092



*Zookeeper:* Zookeeper serves as the coordination interface between the Kafka brokers and consumers. The Kafka servers share information via a Zookeeper cluster. Kafka stores basic metadata in Zookeeper such as information about topics, brokers, consumer offsets (queue readers) and so on.
*Producer:* A producer is a client that sends messages to the Kafka server to the specified topic.
*Consumer:* Consumers are the recipients who receive messages from the Kafka server.
*Broker:* Brokers can create a Kafka cluster by sharing information using Zookeeper. A broker receives messages from producers and consumers fetch messages from the broker by topic, partition, and offset.
*Cluster:* Kafka is a distributed system. A Kafka cluster contains multiple brokers sharing the workload.
*Topic:* A topic is a category name to which messages are published and from which consumers can receive messages.
*Partition:* Messages published to a topic are spread across a Kafka cluster into several partitions. Each partition can be associated with a broker to allow consumers to read from a topic in parallel.
*Offset:* Offset is a pointer to the last message that Kafka has already sent to a consumer.












------------------------------------------------------

Workflow of Pub-Sub Messaging

Producers send message to a topic at regular intervals.

Kafka broker stores all messages in the partitions configured for that particular topic. It ensures the messages are equally shared between partitions. If the producer sends two messages and there are two partitions, Kafka will store one message in the first partition and the second message in the second partition.

Consumer subscribes to a specific topic.

Once the consumer subscribes to a topic, Kafka will provide the current offset of the topic to the consumer and also saves the offset in the Zookeeper ensemble.

Consumer will request the Kafka in a regular interval (like 100 Ms) for new messages.

Once Kafka receives the messages from producers, it forwards these messages to the consumers.

Consumer will receive the message and process it.

Once the messages are processed, consumer will send an acknowledgement to the Kafka broker.

Once Kafka receives an acknowledgement, it changes the offset to the new value and updates it in the Zookeeper. Since offsets are maintained in the Zookeeper, the consumer can read next message correctly even during server outrages.

This above flow will repeat until the consumer stops the request.

Consumer has the option to rewind/skip to the desired offset of a topic at any time and read all the subsequent messages.





-----------------------------------------------------------------


Create Multiple Kafka Brokers

We have one Kafka broker instance already in con-fig/server.properties. Now we need multiple broker instances, so copy the existing server.prop-erties file into two new config files and rename it as server-one.properties and server-two.prop-erties. Then edit both new files and assign the following changes 


config/server-one.properties

# The id of the broker. This must be set to a unique integer for each broker.
broker.id=1
# The port the socket server listens on
port=9093
# A comma seperated list of directories under which to store log files
log.dirs=/tmp/kafka-logs-1

bin/kafka-server-start.sh config/server-one.properties


bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 
-partitions 1 --topic topic-name