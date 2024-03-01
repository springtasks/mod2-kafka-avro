package org.example.avro;

import com.example.schema.Employee;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class KafkaAvroProducer {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put("schema.registry.url", "http://0.0.0.0:2181");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

        KafkaProducer<String, Employee> kafkaProducer = new KafkaProducer<>(properties);
        String topic = "employee-avro";

        Employee employee = Employee.newBuilder()
                .setId(11)
                .setFirstName("Rakesh")
                .setLastName("Maurya")
                .build();

        ProducerRecord<String, Employee> producerRecord = new ProducerRecord<>(topic, employee);

        kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
            if(e == null) {
                System.out.println("Success");
                System.out.println("Metadata: "+recordMetadata);
            } else
                e.printStackTrace();
        });

        kafkaProducer.flush();
        kafkaProducer.close();
    }
}