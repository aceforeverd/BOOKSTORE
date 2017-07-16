import { Component, OnInit } from '@angular/core';
import {Book} from "../../../model/book";
import {BookService} from "../../../service/book.service";
import {MdDialogRef, MdDialog} from "@angular/material";
import {BookDetailComponent} from "../book-detail/book-detail.component";

@Component({
    selector: 'app-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
    books: Book[];

    constructor(private bookService: BookService,
                public diaRef: MdDialog) { }

    ngOnInit() {
        this.getAll();
    }

    getAll() {
        this.bookService.getAll()
            .subscribe((books: Book[]) => this.books = books,
                        error => alert(error))
    }

    edit(book: Book) {
        let dialogRef: MdDialogRef<BookDetailComponent> = this.diaRef.open(BookDetailComponent, {
            data: {
                action: 'Edit',
                book: book
            },
            width: '600px'
        });

        dialogRef.afterClosed().subscribe(data => {
            if (data.action === 'save') {
                this.bookService.createOrUpdateBook(data.book)
                    .subscribe(res => {
                        alert('saved');
                        this.getAll();
                        },
                    error => alert(error));
            }
        })
    }

    addBook() {
        let dialogRef = this.diaRef.open(BookDetailComponent, {
            data: {action: 'Create', book: new Book()},
            width: '600px'
        });

        dialogRef.afterClosed().subscribe(data => {
            if (data.action === 'save') {
                this.bookService.createOrUpdateBook(data.book)
                    .subscribe(res => {
                        alert('saved');
                        this.getAll();
                    }, error => alert(error))
            }
        })
    }

    delete(book: Book) {
        this.bookService.delBook(book.bookId)
            .subscribe(res => {
                    alert('deleted');
                    this.getAll();
                },
            error => alert(error))
    }
}
