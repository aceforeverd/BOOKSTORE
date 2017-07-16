import {Category} from "./category";
export class Book {
    bookId?: number;
    bookName: string;
    bookDesc?: string;
    bookPrice?: number;
    bookCategory?: Category;

    constructor(input:  any = {}) {
        this.bookId = input.bookId || null;
        this.bookName = input.bookName || '';
        this.bookDesc = input.bookDesc || '';
        this.bookPrice = input.bookPrice || 0;
        this.bookCategory = input.bookCategory || null;
    }
}
