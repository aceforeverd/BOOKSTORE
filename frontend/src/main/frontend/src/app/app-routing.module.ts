import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {UserListComponent} from './components/user/user-list/user-list.component';
import {UserDetailComponent} from './components/user/user-detail/user-detail.component'
import {BookListComponent} from './components/book/book-list/book-list.component';
import {OrderListComponent} from './components/order/order-list/order-list.component';
import {UserOrdersComponent} from './components/order/user-orders/user-orders.component';
import {ShoppingCartComponent} from  './components/shopping-cart/shopping-cart.component';
import {NgModule} from "@angular/core";
import {BookViewComponent} from "./components/shopping-cart/book-view/book-view.component";
import {OrderConfirmComponent} from "./components/shopping-cart/order-confirm/order-confirm.component";
import {DetailComponent} from "./components/user/detail/detail.component";
import {AuthGuards} from "./components/user/guards/guards";

const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'userList', component: UserListComponent, canActivate: [AuthGuards]},
    {path: 'userList/:id', component: DetailComponent, canActivate: [AuthGuards]},
    {path: 'userOrders/:id', component: UserOrdersComponent, canActivate: [AuthGuards]},
    {path: 'bookList', component: BookListComponent, canActivate: [AuthGuards]},
    {path: 'orderList', component: OrderListComponent, canActivate: [AuthGuards]},
    {path: 'shop', component: ShoppingCartComponent},
    {path: 'bookDetail/:id', component: BookViewComponent},
    {path: 'orderConfirm', component: OrderConfirmComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {}

