import { Component, OnInit } from '@angular/core';
import {Book} from "../../model/book";
import {BookService} from "../../service/book.service";
import {Order} from "../../model/order";
import {OrderItem} from "../../model/orderItem";
import {OrderService} from "../../service/order.service";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {MdDialog} from "@angular/material";
import {BookDetailComponent} from "../book/book-detail/book-detail.component";
import {User} from "../../model/user";

@Component({
    selector: 'app-shopping-cart',
    templateUrl: './shopping-cart.component.html',
    styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {
    currentUser: User;
    books: Book[];
    order: Order;
    confirmOrder: boolean;

    constructor(private bookService: BookService,
                private orderService: OrderService,
                private userService: UserService,
                private router: Router,
                public dialog: MdDialog) { }

    ngOnInit() {
        this.bookService.getAll()
            .subscribe(books => this.books = books,
                        error => alert(error));

        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));

        this.order = JSON.parse(localStorage.getItem('cachedOrder'));
        if (this.order) {
            this.order.orderUser = this.currentUser;
        }

        if (!this.order) {
            this.order = new Order();
            this.order.orderUser = this.currentUser;
            this.order.orderPrice = 0;
        }


        this.confirmOrder = false;
    }

    logout() {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('cachedOrder');
        alert('OK');
        this.currentUser = null;
        this.router.navigate(['/login']);
    }

    login() {
        if (this.order.orderApplyEntities.length > 0) {
            localStorage.setItem('cachedOrder', JSON.stringify(this.order));
        }
        this.router.navigate(['/login'])
    }

    goDetail(id: number) {
        let book: Book;
        this.bookService.getBook(id)
            .subscribe(res => {
                book = res;
                this.dialog.open(BookDetailComponent, {
                    data: {action: 'View', book: book }
                })
            }, error => alert(error));
    }

    addToCart(book: Book) {
        let index: number = this.findIndexOfBook(book);
        if (index === -1) {
            let orderItem = new OrderItem();
            orderItem.bookEntity = book;
            orderItem.bookNumber = 1;
            this.order.orderApplyEntities.push(orderItem);
        } else {
            this.order.orderApplyEntities[index].bookNumber ++;
        }
        this.order.orderPrice += book.bookPrice;
    }

    decrease(book: Book) {
        let index = this.findIndexOfBook(book);
        if (index === -1) return;

        if (this.order.orderApplyEntities[index].bookNumber <= 0) {
            return;
        }
        this.order.orderApplyEntities[index].bookNumber --;
        this.order.orderPrice -= book.bookPrice;
    }

    remove(book: Book) {
        let index = this.order.orderApplyEntities.findIndex((item) => {
            return item.bookEntity === book
        });
        let num = this.order.orderApplyEntities[index].bookNumber;
        this.order.orderPrice -= book.bookPrice * num;
        this.order.orderApplyEntities.splice(index, 1);
    }

    makeOrder() {
        let price: number = 0;
        this.order.orderApplyEntities.forEach(item => {
            price += item.bookEntity.bookPrice;
        });
        this.order.orderPrice = price;
        this.confirmOrder = !this.confirmOrder;
    }

    continueShop() {
        this.confirmOrder = !this.confirmOrder;
    }

    confirm() {
        this.orderService.createOrUpdateOrder(this.order)
            .subscribe(res => {
                console.log('confirmed');
                this.router.navigate(['/orderConfirm'])
            })
    }

    findOrder(book: Book) {
        let index = this.findIndexOfBook(book);
        if (index === -1) {
            return null;
        }
        return this.order.orderApplyEntities[index];
    }

    findIndexOfBook(book: Book): number {
        let index = this.order.orderApplyEntities.findIndex((item) => {
            return item.bookEntity === book;
        });
        return index;
    }
}
