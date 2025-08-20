import { Kafka, Partitioners } from 'kafkajs';
import dotenv from 'dotenv';

dotenv.config();

const KAFKA_BROKERS = process.env.KAFKA_BROKERS?.split(','); 

const BROKERS = KAFKA_BROKERS || ['localhost:9092'];

const kafka = new Kafka({
    clientId: 'order-service',
    brokers: BROKERS
});

const producer = kafka.producer({
    createPartitioner: Partitioners.LegacyPartitioner 
});

export const connectProducer = async () => {
    try {
        await producer.connect();
        console.log("Kafka producer connected successfully");
    } catch (error) {
        console.error("Error connecting the Kafka producer:", error);
    }
};

export const disconnectProducer = async () => {
    await producer.disconnect();
    console.log("Kafka producer disconnected.");
};

export default producer;