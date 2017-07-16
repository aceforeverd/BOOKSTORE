import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {AppRoutingModule} from "./app-routing.module";
import {HttpModule} from "@angular/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
    MdButtonModule, MdButtonToggleModule, MdCheckboxModule, MdInputModule,
    MdNativeDateModule, MdAutocompleteModule, MdRadioModule, MdDatepickerModule,
    MdSelectModule, MdSliderModule, MdSlideToggleModule, MdMenuModule, MdSidenavModule,
    MdToolbarModule, MdListModule, MdGridListModule, MdCardModule, MdTabsModule,
    MdIconModule, MdProgressBarModule, MdProgressSpinnerModule, MdDialogModule,
    MdTooltipModule, MdSnackBarModule, MdTableModule, MdSortModule, MdPaginatorModule
} from "@angular/material";
import { RegisterComponent } from './components/register/register.component';
import 'hammerjs';
import {UserService} from "./service/user.service";
import { UserDetailComponent } from './components/user/user-detail/user-detail.component';
import { UserListComponent } from './components/user/user-list/user-list.component';
import { BookListComponent } from './components/book/book-list/book-list.component';
import { BookDetailComponent } from './components/book/book-detail/book-detail.component';
import {BookService} from "./service/book.service";
import {OrderService} from "./service/order.service";
import { OrderListComponent } from './components/order/order-list/order-list.component';
import { OrderDetailComponent } from './components/order/order-detail/order-detail.component';
import { UserOrdersComponent } from './components/order/user-orders/user-orders.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { OrderViewComponent } from './components/shopping-cart/order-view/order-view.component';
import { BookViewComponent } from './components/shopping-cart/book-view/book-view.component';
import { OrderConfirmComponent } from './components/shopping-cart/order-confirm/order-confirm.component';
import { DetailComponent } from './components/user/detail/detail.component';
import { BookSearchComponent } from './components/shopping-cart/book-search/book-search.component';
import {AuthGuards} from "./components/user/guards/guards";

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        UserDetailComponent,
        UserListComponent,
        BookListComponent,
        BookDetailComponent,
        OrderListComponent,
        OrderDetailComponent,
        UserOrdersComponent,
        ShoppingCartComponent,
        OrderViewComponent,
        BookViewComponent,
        OrderConfirmComponent,
        DetailComponent,
        BookSearchComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        AppRoutingModule,
        HttpModule,
        BrowserAnimationsModule,
        MdButtonModule,
        MdButtonToggleModule,
        MdCheckboxModule,
        MdInputModule,
        MdNativeDateModule,
        MdAutocompleteModule,
        MdRadioModule,
        MdDatepickerModule,
        MdSelectModule,
        MdSliderModule,
        MdSlideToggleModule,
        MdMenuModule,
        MdSidenavModule,
        MdToolbarModule,
        MdListModule,
        MdGridListModule,
        MdCardModule,
        MdTabsModule,
        MdIconModule,
        MdProgressBarModule,
        MdProgressSpinnerModule,
        MdDialogModule,
        MdTooltipModule,
        MdSnackBarModule,
        MdTableModule,
        MdSortModule,
        MdPaginatorModule
    ],
    entryComponents: [
        UserDetailComponent,
        BookDetailComponent,
        OrderDetailComponent
    ],
    providers: [UserService, BookService, OrderService, AuthGuards],
    bootstrap: [AppComponent]
})
export class AppModule { }
