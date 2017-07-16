import { Component, OnInit } from '@angular/core';

import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import {User} from "../../model/user";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    errorMsg: any;
    currentUser: User;

    constructor(
                private userService: UserService,
                private router: Router
               ) { }

    login(input: any) {
         this.userService.login(input.username.trim().toLowerCase(), input.password)
             .subscribe((res) => {
                if (res.message) {
                    alert('login success');
                }
             }, error => {
                 this.errorMsg = error;
             });
    }
    ngOnInit() {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

}
