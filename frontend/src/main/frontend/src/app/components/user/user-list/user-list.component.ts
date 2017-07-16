import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user";
import {MdDialog, MdDialogRef} from '@angular/material';
import {UserDetailComponent} from "../user-detail/user-detail.component";
import {Router} from "@angular/router";

@Component({
    selector: 'app-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
    users: User[];

    constructor(private userService: UserService,
                public dialog: MdDialog,
                private route: Router) { }

    getAll() {
        this.userService.getAll()
            .subscribe(users => this.users = users,
                error => alert(error))
    }

    ngOnInit() {
        this.getAll();
    }

    edit(user: User) {
        let dialogRef: MdDialogRef<UserDetailComponent> = this.dialog.open(UserDetailComponent, {
            data: {action: 'Edit', user: user}
        });

        dialogRef.afterClosed().subscribe((res: {action: string, user: User}) => {
            if (res.action === 'save') {
                this.userService.update(res.user)
                    .subscribe(user => {alert('saved'); this.getAll();},
                        error => alert(error))
            }
        } )
    }

    editNewPage(user: User) {
        this.route.navigate(['/userList', user.userId]);
    }

    addUser() {
        let diaRef: MdDialogRef<UserDetailComponent> = this.dialog.open(UserDetailComponent,
            {
                data: {action: 'Create', user: new User()}
            });

        diaRef.afterClosed().subscribe(res => {
            if (res.action === 'save') {
                this.userService.create(res.user).subscribe(
                    res => {
                        alert('created');
                        this.getAll();
                    }, error => alert(error)
                )
            }
        })
    }

    deleteUser(user: User) {
        this.userService.deleteUser(user.userId)
            .subscribe(res => {
                    alert('deleted');
                    this.getAll();
                },
                    error => alert(error)
            );
    }

    goOrders(user: User) {
        this.route.navigate(['userOrders', user.userId])
    }
}
