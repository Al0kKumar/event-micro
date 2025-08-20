import { Router } from "express";
import order_controller from "../controllers/order.controller.js";
const router = Router();

router.post('/',order_controller);

export default router;