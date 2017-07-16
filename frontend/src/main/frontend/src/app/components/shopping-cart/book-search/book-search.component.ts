import { Component, OnInit } from '@angular/core';
import {Book} from "../../../model/book";
import {Observable} from "rxjs/Observable";
import {BookService} from "../../../service/book.service";
import {Router} from "@angular/router";
import { Subject }           from 'rxjs/Subject';

import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';

@Component({
    selector: 'app-book-search',
    templateUrl: './book-search.component.html',
    styleUrls: ['./book-search.component.scss']
})
export class BookSearchComponent implements OnInit {
    books: Observable<Book[]>;
    private searchTerm = new Subject<string>();

    constructor(private bookService: BookService,
                private router: Router) { }


    ngOnInit() {
        this.books = this.searchTerm
            .debounceTime(500)
            .distinctUntilChanged()
            .switchMap(term => {
                return term ? this.bookService.getAll(term, -1, -1, true) :
                    Observable.of<Book[]>([]);
            })
            .catch(error => {
                console.log(error);
                return Observable.of<Book[]>([]);
            })
    }

    search(str: string) {
        this.searchTerm.next(str);
    }

    gotoDetail(book: Book) {
        this.router.navigate(['/bookDetail', book.bookId]);
    }
}
