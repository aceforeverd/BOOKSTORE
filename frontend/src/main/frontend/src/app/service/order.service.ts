import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {Observable} from "rxjs/Observable"
import {Order} from "../model/order";

@Injectable()
export class OrderService {
    private orderUrl = '/orders/';
    private jsonHeader = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {}

   getAll(lower?: string, higher?: string) {
        let url = this.orderUrl + '?';
        if (lower) url += `lower=${lower}`;
        if (higher) url += `higher=${higher}`;

        return this.http.get(url)
            .map(res => res.json())
            .catch(this.handleError)
   }

   getOrder(id: number) {
        return this.http.get(this.orderUrl + id)
            .map(res => res.json() as Order)
            .catch(this.handleError)
   }

   delOrder(id: number) {
        return this.http.delete(this.orderUrl + id)
            .map(res => res.json())
            .catch(this.handleError)
   }

   createOrUpdateOrder(order: Order) {
        if (order.orderId) {
            return this.http.put(this.orderUrl, JSON.stringify(order), {headers: this.jsonHeader})
                .map(res => res.json())
                .catch(this.handleError)
        }
        else {
            return this.http.post(this.orderUrl, JSON.stringify(order), {headers: this.jsonHeader})
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
