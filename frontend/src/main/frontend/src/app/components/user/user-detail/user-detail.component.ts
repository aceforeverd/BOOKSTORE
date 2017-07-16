import {Component, Input, OnInit, Inject} from '@angular/core';
import {User} from "../../../model/user";
import {Location} from '@angular/common';
import {UserService} from "../../../service/user.service";
import {ActivatedRoute, ParamMap} from "@angular/router";
import 'rxjs/add/operator/switchMap';
import {MD_DIALOG_DATA, MdDialogRef} from '@angular/material';

@Component({
    selector: 'app-user-detail',
    templateUrl: './user-detail.component.html',
    styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {

    constructor(private route: ActivatedRoute,
                private location: Location,
                public dialogRef: MdDialogRef<UserDetailComponent>,
                @Inject(MD_DIALOG_DATA) public data: {action: string, user: User}) {
        console.log(this.data);
    }

    saveUser() {
        this.dialogRef.close({
            action: 'save',
            user: this.data.user
        });
    }

    goBack() {
        this.dialogRef.close({action: 'cancel', user: null});
    }

    ngOnInit() {
        /*
        this.route.paramMap
            .switchMap((param: ParamMap) => this.userService.getUser(+param.get('id')))
            .subscribe(user => this.user = user,
                        error => alert(error));
                        */
    }

    uploadFile() {
        let data = new FormData();
        data.append("file", "androidify-1462894152525.png");

        let xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === 4) {
                console.log(this.responseText);
            }
        });

        xhr.open("POST", `/files?${this.data.user.userId}`);
        xhr.setRequestHeader("cache-control", "no-cache");

        xhr.send(data);
    }

}
