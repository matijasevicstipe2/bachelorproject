import {Component} from '@angular/core';
import {Account} from "../account/account";
import {Router} from "@angular/router";
import {AccountService} from "../account/account.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  accountForm: FormGroup;
  account: Account = new Account('', '', '', '', '', null, '',
    '', '');

  constructor(
    private accountService : AccountService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.accountForm = this.formBuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      sex: ['', Validators.required],
      birthdate: ['', Validators.required],
      address: ['', Validators.required],
      cityState: ['', Validators.required]
    });
  }

  onSubmit() {
    this.account = {
      firstname: this.accountForm.get('firstname')?.value,
      lastname: this.accountForm.get('lastname')?.value,
      username: this.accountForm.get('username')?.value,
      password: this.accountForm.get('password')?.value,
      email: this.accountForm.get('email')?.value,
      sex: this.accountForm.get('sex')?.value,
      birthdate: this.accountForm.get('birthdate')?.value,
      address: this.accountForm.get('address')?.value,
      cityState: this.accountForm.get('cityState')?.value
    };
    this.accountService.createAccount(this.account).subscribe(
      response => {
        console.log('Account created successfully:', response);
        this.router.navigate(['/home']);
      },
      error => {
        console.error('Error creating account:', error);
      }
    );
  }

  clearFields() {
    this.account = new Account('', '', '', '', '', null, '',
      '', '');

  }
}
