Firstly download the Apache Kafka Latest version from official Kafka website.<br><br>
**Important:** setup Apache Kafka after unziping the tar/zip and place it in c drive.
<br>Go to Home of Apache kafka folder and follow below steps if you want to test your apache Kafka manually (without Java)

**Start Zookeeper:**
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

**Start Kafka Server:** .\bin\windows\kafka-server-start.bat .\config\server.properties

**create topic :**
.\bin\windows\kafka-topics.bat --create --topic employee-avro --bootstrap-server localhost:9092

**create producer:**
.\bin\windows\kafka-console-producer.bat --topic employee-avro --bootstrap-server localhost:9092

**Create Consumer:**
.\bin\windows\kafka-console-consumer.bat --topic employee-avro --from-beginning --bootstrap-server localhost:9092 --group notif-consumer
