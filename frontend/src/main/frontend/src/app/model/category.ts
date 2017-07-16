export class Category {
    categoryId?: number;
    categoryName: string;
    categoryDesc?: string;


    constructor(input: {
        categoryId?: number,
        categoryName?: string,
        categoryDesc?: string
    } ={} ) {
        this.categoryId = input.categoryId || null;
        this.categoryName = input.categoryName || '';
        this.categoryDesc = input.categoryDesc || '';
    }
}
