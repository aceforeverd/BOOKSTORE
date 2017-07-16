import {Component, Inject, OnInit} from '@angular/core';
import {MD_DIALOG_DATA, MdDialogRef} from '@angular/material';
import {Book} from "../../../model/book";

@Component({
    selector: 'app-book-detail',
    templateUrl: './book-detail.component.html',
    styleUrls: ['./book-detail.component.scss']
})
export class BookDetailComponent implements OnInit {

    constructor(public dialogRef: MdDialogRef<BookDetailComponent>,
                @Inject(MD_DIALOG_DATA) public data: {action: string, book: Book}) {
    }

    ngOnInit() {
    }

    save() {
        this.dialogRef.close({
            action: 'save',
            book: this.data.book
        });
    }

    back() {
        this.dialogRef.close({
            action: 'cancel',
            book: null
        })
    }

    /*
    upload(e: Event) {

        let formTemp: HTMLFormElement <HTMLFormElement>document.querySelector('#form');

        let formData: FormData = new FormData(formTemp);

        let xhr: XMLHttpRequest = this.foo(formData);

        xhr.onreadystatechange = () => {
            if(xhr.readyState === 4) {
                if(xhr.status === 201) {
                    console.log("Success");
                } else {
                    console.log("Error");
                }
            }
        }
    }
    */

}
