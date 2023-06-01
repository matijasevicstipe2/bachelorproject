import {Component} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {AuthenticationService} from "../security/authentication.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  title = 'Blast from the past! - VHS Rental Shop';
  isUserAdmin: boolean;

  public constructor(private titleService: Title,
                     private authenticationService: AuthenticationService) {
    this.titleService.setTitle('Blast from the Past!');
    this.isUserAdmin = this.authenticationService.isUserAdmin();
  }
}
