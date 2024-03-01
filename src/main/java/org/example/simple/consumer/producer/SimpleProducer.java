package org.example.simple.consumer.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleProducer {

    private final KafkaProducer<String, String> producer;


    public SimpleProducer(String host, String port) throws UnknownHostException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host+":"+port);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        this.producer = new KafkaProducer<>(properties);
    }

    public void produce(String topic, String message) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        final Future<RecordMetadata> send = this.producer.send(record);
        final RecordMetadata recordMetadata = send.get();
        System.out.println("Message has been produced ! Metadata Details - " + recordMetadata);

    }

    public static void main(String[] args) throws Exception{
        SimpleProducer producer1 = new SimpleProducer("localhost", "9092");
        producer1.produce("simple-topic", "Hello Vihaan ");

    }
}
