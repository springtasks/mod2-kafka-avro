package org.example.avro;

import com.example.schema.Employee;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class KafkaAvroConsumer {

    private static final String OFFSET_RESET = "earliest";

    private final KafkaConsumer<String, Employee> consumer;
    private boolean keepConsuming = true;

    public KafkaAvroConsumer(String host, String port, String consumerGroupId, String topic) {
        String server = host + ":" + port;
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        properties.put("schema.registry.url", "http://0.0.0.0:2181");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_RESET);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        this.consumer = new KafkaConsumer<>(properties);
        this.consumer.subscribe(List.of(topic));
    }

    public void consume() {
        while (keepConsuming) {
            final ConsumerRecords<String, Employee> consumerRecords = this.consumer.poll(Duration.ofMillis(100L));
            if (consumerRecords != null && !consumerRecords.isEmpty()) {
                consumerRecords.iterator().forEachRemaining(
                        consumerRecord -> System.out.println(
                                consumerRecord.value().getFirstName() +
                                "\n" + consumerRecord.value().getLastName()));
            }
        }
    }

    public static void main(String[] args) {
        KafkaAvroConsumer consumer = new KafkaAvroConsumer("localhost", "9092", "employee-group", "consumer-avro");
        consumer.consume();
    }
}
