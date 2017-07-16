import {User} from "./user";
import {OrderItem} from "./orderItem";
export class Order {
    orderId?: number;
    orderPrice?: number;
    orderTime?: Date;
    orderUser?: User;
    orderApplyEntities?: OrderItem[];

    constructor(input: any = {}) {
        this.orderId = input.orderId || null;
        this.orderPrice = input.orderPrice || null;
        this.orderUser = input.orderUser || null;
        this.orderTime = input.orderTime || null;
        this.orderApplyEntities = input.orderApplyEntities || [];
    }
}
