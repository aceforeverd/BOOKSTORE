import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {FormValidators} from "../../control/validator";
import 'rxjs/add/operator/map';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;


    constructor(private userService: UserService,
                private router: Router,
                private formBuilder: FormBuilder
               ) { }

    register() {
        return this.userService.add(
            this.registerForm.value.username.trim().toLowerCase(),
            this.registerForm.value.password,
            this.registerForm.value.email.trim())
            .subscribe(data => {
                const message = data.message || 'OK';
                alert(message);
            }, error => {
                alert(error);
            })
    }


    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(32)], this.nameConflict.bind(this)],
            password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(64)]],
            passwordRepeat: ['', Validators.required],
            email: ['', [Validators.required, Validators.email], this.emailConflict.bind(this)]
        }, {validator: FormValidators.passwordMatchValidator});
    }

    private nameConflict(g: AbstractControl) {
        return this.userService.nameConflict(g.value.trim().toLowerCase())
            .map(res =>  {
                return res != 'OK' ? {
                    nameConflict: true
                }: null;
            })
    }

    private emailConflict(g: AbstractControl) {
        return this.userService.emailConflict(g.value.trim())
            .map(res => {
                return res != 'OK' ?
                    {
                        emailConflict: true
                    }: null;
            })
    }

}
