import { Component, OnInit } from '@angular/core';
import {OrderService} from "../../../service/order.service";
import {MdDialog, MdDialogRef} from "@angular/material";
import {Order} from "../../../model/order";
import {OrderDetailComponent} from "../order-detail/order-detail.component";

@Component({
    selector: 'app-order-list',
    templateUrl: './order-list.component.html',
    styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {
    orders: Order[];

    constructor(private orderService: OrderService,
                public diaRef: MdDialog) { }

    ngOnInit() {
        this.getAll();
    }

    getAll() {
        this.orderService.getAll()
            .subscribe(res => this.orders = res,
                        error => alert(error))
    }

    createOrEdit(order?: Order) {
        let dialogRef: MdDialogRef<OrderDetailComponent>;
        if (!order) {
            dialogRef = this.diaRef.open(OrderDetailComponent, {
                data: {action: 'Create', order: new Order()}
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
