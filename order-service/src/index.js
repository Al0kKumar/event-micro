import express from "express";
import dotenv from "dotenv";
import { connectDB } from "./config/db.js";
import orderRoutes from "./routes/order.routes.js"
import { connectProducer } from "./kafka/producer.js";
const app = express();

dotenv.config();

const PORT = process.env.PORT;


app.use(express.json()); 
app.use(express.urlencoded({ extended: true })); 

app.use("/api/order",orderRoutes);


Promise.all([
    connectDB(),
    connectProducer()

])
.then(() => {
    app.listen(PORT, () => {
        console.log(`order-service is running on PORT: ${PORT}`);
    });
}).catch(error => {
    console.error("Failed to start server:", error);
});