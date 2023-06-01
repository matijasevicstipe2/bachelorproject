import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "./security/authentication.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  constructor(
    public authenticationService: AuthenticationService,
    private router: Router
  ) { }

  navigateToGymDashboard() {
    this.router.navigateByUrl('/gym-dashboard');
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']).then();
  }
}


