import {Component, Inject, OnInit} from '@angular/core';
import {MD_DIALOG_DATA, MdDialogRef} from "@angular/material";
import {Order} from "../../../model/order";

@Component({
    selector: 'app-order-detail',
    templateUrl: './order-detail.component.html',
    styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent implements OnInit {

    constructor(public dialogRef: MdDialogRef<OrderDetailComponent>,
                @Inject(MD_DIALOG_DATA) public data: {action: string, order: Order}) { }

    ngOnInit() {
    }

    save() {
        this.dialogRef.close({
            action: 'save',
            order: this.data.order
        })
    }

    back() {
        this.dialogRef.close({
            data: 'cancel',
            order: null
        })
    }

}
