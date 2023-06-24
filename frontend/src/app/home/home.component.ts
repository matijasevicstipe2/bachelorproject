import {Component} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {AuthenticationService} from "../security/authentication.service";
import {MembershipOption} from "../membership/membership-option";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  membershipOptions: MembershipOption[] = [];

  private backendUrl = 'http://localhost:8080';

  constructor(private http: HttpClient,
              private authService: AuthenticationService) { }

  ngOnInit() {
    this.fetchUserInfo();
  }

  fetchUserInfo() {
    const username = this.authService.getAuthenticatedUserUsername():
    this.http.get<MembershipOption[]>(this.backendUrl + '/api/membership-options')
      .subscribe(options => {
        this.membershipOptions = options;
      });
  }
}
