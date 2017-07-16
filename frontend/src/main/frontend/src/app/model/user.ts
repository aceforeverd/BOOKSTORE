import {Address} from "./address";
export class User {
    userId?: number;
    username: string;
    password: string;
    email: string;
    phone?: string;
    createTime?: Date;
    addressEntities?: Address[];

    constructor(input: {
        userId?: number,
        username?: string,
        email?: string,
        password?: string,
        phone?: string,
        createTime?: Date,
        addressEntities?: Address[]
    } = {} ) {
        this.username = input.username || null;
        this.password = input.password || null;
        this.email = input.email || null;
        this.phone = input.phone || null;
        this.userId = input.userId || null;
        this.createTime = input.createTime || null;
        this.addressEntities = input.addressEntities || [];
    }
}
