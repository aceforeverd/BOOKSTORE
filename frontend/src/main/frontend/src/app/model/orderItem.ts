import {Book} from "./book";
export class OrderItem {
    bookNumber?: number;
    bookEntity?: Book;

    constructor(input: any = {}) {
        this.bookNumber = input.bookNumber || 1;
        this.bookEntity = input.bookEntity || null;
    }
}
