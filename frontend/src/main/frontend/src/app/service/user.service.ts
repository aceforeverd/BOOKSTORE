import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import { User } from '../model/user';
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {Order} from "../model/order";
import {Address} from "../model/address";

@Injectable()
export class UserService {
    private userUrl = '/users/';
    private jsonHeader = new Headers({'Content-Type': 'application/json'});
    private formHeader = new Headers({"Content-Type": "application/x-www-form-urlencoded"});

    constructor(private http: Http,
                private router: Router) { }

    getAll(name?: string, email?: string, fuzzy: boolean = false) : Observable<User[]> {
        let url: string = this.userUrl + '?fuzzy=' + fuzzy;
        if (name) {
            url += ('&username=' + name);
        }
        if (email) {
            url += ('&email=' + email);
        }
        return this.http.get(url)
            .map((res: Response) => res.json() )
            .catch(this.handleError);
    }

    getUser(id: number)  {
        const url = this.userUrl + id;
        return this.http.get(url)
            .map((response: Response) => {
                return response.json();
            })
            .catch(this.handleError);
    }


    login(userName: string, userPassword: string) {
        let body: string = "username=" + userName + "&password=" + userPassword;
        return this.http.post(this.userUrl + 'login', body, {headers: this.formHeader})
            .map((res: Response) => {
                localStorage.setItem('currentUser', JSON.stringify(res.json()))
                return res.json();
            })
            .catch(this.handleError);
    }

    logout() {
        localStorage.removeItem('currentUser');
    }


    add(username: string, password: string, email: string) {
        let body: string = JSON.stringify({
            username: username,
            password: password,
            email: email
        });
        return this.http.post(this.userUrl, body, {headers: this.jsonHeader})
            .map(res => res.json())
            .catch(this.handleError)
    }

    create(user: User) {
        return this.http.post(this.userUrl, JSON.stringify(user), {headers: this.jsonHeader})
            .map(res => res.json())
            .catch(this.handleError)
    }

    deleteUser(id: number) {
        const url = this.userUrl + id;
        return this.http.delete(url, {headers: this.formHeader})
            .map((response: Response) => {
                return response;
            })
            .catch(this.handleError);
    }

    update(user: User) {
        return this.http.put(this.userUrl, JSON.stringify(user), {headers: this.jsonHeader})
            .map(res => res.json())
            .catch(this.handleError)
    }

    getOrders(id: number) {
        return this.http.get(this.userUrl + id + '/orders')
            .map(res => res.json() as Order[])
            .catch(this.handleError)
    }

    createOrder(id: number, order: Order) {
        return this.http.post(this.userUrl + id + '/orders', JSON.stringify(order), {headers: this.jsonHeader})
            .map(res => res.json())
            .catch(this.handleError)
    }

    getAddrs(id: number) {
        return this.http.get(this.userUrl + id + '/addrs')
            .map(res => res.json() as Address[])
            .catch(this.handleError)
    }

    createOrUpdateAddr(id: number, addr: Address) {
        if (addr.addrId) {
            return this.http.put(this.userUrl + id + '/addrs', JSON.stringify(addr), {headers: this.jsonHeader})
                .map(res => res.json())
                .catch(this.handleError)
        }
        else {
            return this.http.post(this.userUrl + id + '/addrs', JSON.stringify(addr), {headers: this.jsonHeader})
                .map(res => res.json())
                .catch(this.handleError)
        }
    }

    nameConflict(name: string): Observable<string> {
        const url = this.userUrl + `validate?username=${name}`;
        return this.http.get(url)
            .debounceTime(1000)
            .distinctUntilChanged()
            .map(res => res.text() as string);
    }

    emailConflict(email: string): Observable<string> {
        const  url = this.userUrl + `validate?email=${email}`;
        return this.http.get(url)
            .debounceTime(1000)
            .distinctUntilChanged()
            .map((res: Response) => res.text() as string);
    }

    obtainAccessToken(loginData) {
        let param = new URLSearchParams();
        param.append('username', loginData.username);
        param.append('password', loginData.password);
        param.append('grant_type', 'password');
        param.append('client_id', 'ClientIdPassword');

    }

    private jwt() {
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.token) {
            let headers = new Headers({'Authorization': 'Bearer' + currentUser.token});
            return new RequestOptions({headers: headers});
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
