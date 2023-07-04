import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "./security/authentication.service";
import {UserDto} from "./home/userDto";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  user: UserDto = {} as UserDto;

  private backendUrl = 'http://localhost:8080';

  constructor(
    public authenticationService: AuthenticationService,
    private router: Router,
    private http: HttpClient
  ) {
    this.fetchUserInfo();
  }

  fetchUserInfo() {
    const username = this.authenticationService.getAuthenticatedUserUsername();
    this.http.get<UserDto>(this.backendUrl + `/api/userinfo/${username}`).subscribe(
      (response) => {
        this.user = response;
        console.log(response);
      },
      (error) => {
        console.error(error);
      }
    );

  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']).then();
  }
}


