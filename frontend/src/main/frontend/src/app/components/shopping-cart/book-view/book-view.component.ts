import {Component, Input, OnInit} from '@angular/core';
import {Book} from "../../../model/book";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {Location} from "@angular/common";
import {BookService} from "../../../service/book.service";

@Component({
    selector: 'app-book-view',
    templateUrl: './book-view.component.html',
    styleUrls: ['./book-view.component.scss']
})
export class BookViewComponent implements OnInit {
     book: Book;

    constructor(private router: ActivatedRoute,
                private location: Location,
                private bookService: BookService) { }

    ngOnInit() {
        this.router.paramMap
            .switchMap((params: ParamMap) => {
                return this.bookService.getBook(+params.get('id'))
            })
            .subscribe(book => {
                this.book = book;
            });
    }

    goBack() {
        this.location.back();
    }

}
