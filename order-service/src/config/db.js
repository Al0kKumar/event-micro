import mongoose from "mongoose";
import dotenv from "dotenv";


dotenv.config();

const URL = process.env.DB_URL;


export const connectDB = async () => {

    try {
        await mongoose.connect(URL);
        console.log(`Mongodb connected `);
    } catch (error) {
        console.error("error in connecting database", error);
    }

}