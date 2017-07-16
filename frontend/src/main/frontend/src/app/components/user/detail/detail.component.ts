import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user";

@Component({
    selector: 'app-detail',
    templateUrl: './detail.component.html',
    styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
    user: User;

    constructor(private router: ActivatedRoute,
                private userService: UserService) { }

    ngOnInit() {
        this.router.paramMap
            .switchMap((params: ParamMap) => {
                return this.userService.getUser(+params.get('id'))
            })
            .subscribe(user => {
                this.user = user;
            })
    }

}
