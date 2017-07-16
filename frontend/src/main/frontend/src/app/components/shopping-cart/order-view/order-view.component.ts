import {Component, Input, OnInit} from '@angular/core';
import {Order} from "../../../model/order";
import {Book} from "../../../model/book";
import {Router} from "@angular/router";

@Component({
    selector: 'app-order-view',
    templateUrl: './order-view.component.html',
    styleUrls: ['./order-view.component.scss']
})
export class OrderViewComponent implements OnInit {
    @Input() order: Order;

    constructor(private router: Router) { }

    ngOnInit() {

    }

    goDetail(book: Book) {
        this.router.navigate([
            '/bookDetail', book.bookId
        ])
    }
}
