import express from "express";
import Order from "../models/order.model.js"; 
import producer from "../kafka/producer.js";

const order_controller = async (req, res) => { 
    try {

        const { userId, items, totalAmount } = req.body;

        const newOrder = new Order({
            userId,
            items,
            totalAmount,
            status: 'pending'
        });

        const savedOrder = await newOrder.save();

         const messagePayload = {
            orderId: savedOrder._id.toString(),
            userId: savedOrder.userId,
            totalAmount: savedOrder.totalAmount,
            status: savedOrder.status
        };

         await producer.send({
            topic: 'order-placed-topic', // 3. This is the topic your other services will listen to
            messages: [{ value: JSON.stringify(messagePayload) }],
        });
        
        console.log(`Order created with ID: ${savedOrder._id}`);
        

        // 7. Send a successful response back
        res.status(201).json({ 
            message: "Order placed successfully!", 
            order: savedOrder 
        });

    } catch (error) {
        console.error("Error creating order:", error);
        res.status(500).json({ message: "Internal server error" });
    }
}

export default order_controller;