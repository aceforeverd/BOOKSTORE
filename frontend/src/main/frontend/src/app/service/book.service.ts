import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {Observable} from "rxjs/Observable"
import {Book} from "../model/book";

@Injectable()
export class BookService {
    private bookUrl = '/books/';
    private jsonHeader = new Headers({'Content-Type': 'application/json'});
    private formHeader = new Headers({"Content-Type": "application/x-www-form-urlencoded"});

    constructor(private http: Http) {}

    getAll(bookName?: string, lower?: number, higher?: number, fuzzy: boolean = false) {
        let url = this.bookUrl + `?fuzzy=${fuzzy}`;
        if (bookName) url += `&bookName=${bookName}`;
        if (lower) url += `&lower=${lower}`;
        if (higher) url += `&higher=${higher}`;

        return this.http.get(url)
            .map(res => res.json() )
            .catch(this.handleError)
    }

    getBook(id: number) {
        return this.http.get(this.bookUrl + id)
            .map(res => res.json())
            .catch(this.handleError)
    }

    delBook(id: number) {
        return this.http.delete(this.bookUrl + id)
            .map(res => res.json())
            .catch(this.handleError)
    }

    createOrUpdateBook(book: Book) {
        if (!book.bookId) {
            return this.http.post(this.bookUrl, JSON.stringify(book), {headers: this.jsonHeader})
                .map(res => res.json())
                .catch(this.handleError)
        }
        else {
            return this.http.put(this.bookUrl, JSON.stringify(book), {headers: this.jsonHeader})
                .map(res => res.json())
                .catch(this.handleError)
        }
    }


    private handleError(error: Response | any)  {
        let errMsg: string;
        if (error instanceof Response) {
            console.error(error.text());
            const body = error.json() || '';
            const err = body.errorMsg || JSON.stringify(error);
            errMsg =`${error.status} - ${error.statusText || ''}: ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        return Observable.throw(errMsg);
    }
}
