import {Component} from '@angular/core';
import {Account} from "../account/account";
import {Router} from "@angular/router";
import {AccountService} from "../account/account.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  account: Account = new Account('', '', '', '', null, '', '', '', '');

  constructor(
    private accountService : AccountService,
    private router: Router
  ) {
  }

  onSubmit() {
    this.accountService.createAccount(this.account).subscribe(
      response => {
        console.log('Account created successfully:', response);
      },
      error => {
        console.error('Error creating account:', error);
      }
    );
  }

  clearFields() {
    this.account = new Account('', '', '', '', null, '', '', '', '');

  }
}
