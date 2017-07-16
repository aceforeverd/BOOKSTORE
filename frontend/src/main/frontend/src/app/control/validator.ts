import {AbstractControl} from '@angular/forms';
export class FormValidators {
    static passwordMatchValidator(g: AbstractControl) {
        return g.get('password').value === g.get('passwordRepeat').value
            ? null : {
                mismatch: {
                    valid: false
                }
            };
    }
}
