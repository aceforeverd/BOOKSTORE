import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {Location} from "@angular/common";
import {OrderService} from "../../../service/order.service";
import {UserService} from "../../../service/user.service";
import {Order} from "../../../model/order";
import {MdDialog, MdDialogRef} from "@angular/material";
import {OrderDetailComponent} from "../order-detail/order-detail.component";
import {User} from "../../../model/user";

@Component({
    selector: 'app-user-orders',
    templateUrl: './user-orders.component.html',
    styleUrls: ['./user-orders.component.scss']
})
export class UserOrdersComponent implements OnInit {
    user: User;
    orders: Order[];

    constructor(private router: ActivatedRoute,
                private location: Location,
                private orderService: OrderService,
                private userService: UserService,
                public diaRef: MdDialog) { }

    ngOnInit() {
        this.getAll();
        this.router.paramMap
            .switchMap((params: ParamMap) => {
                return this.userService.getUser(+params.get('id'))
            })
            .subscribe(user => this.user = user)
    }

    getAll() {
        this.router.paramMap
            .switchMap((params: ParamMap) => {
                return this.userService.getOrders(+params.get('id'))
            })
            .subscribe((orders: Order[]) => {
                this.orders = orders;
            });
    }

    goBack() {
        this.location.back();
    }

    createOrEdit(order?: Order) {
        let dialogRef: MdDialogRef<OrderDetailComponent>;
        if (!order) {
            let newOrder = new Order();
            newOrder.orderUser = this.user;
            console.log(newOrder);
            dialogRef = this.diaRef.open(OrderDetailComponent, {
                data: {action: 'Create', order: newOrder}
            })
        } else {
            dialogRef = this.diaRef.open(OrderDetailComponent, {
                data: {action: 'Edit', order: order}
            });
        }

        dialogRef.afterClosed()
            .subscribe(res => {
                if (res.action === 'save') {
                    this.orderService.createOrUpdateOrder(res.order)
                        .subscribe(res => {
                                alert('saved')
                                this.getAll();
                            },
                            error => alert(error))
                }
            });
    }

    delete(order: Order) {
        this.orderService.delOrder(order.orderId)
            .subscribe(res => {
                alert('deleted');
                this.getAll();
            }, error => alert(error))
    }
}
