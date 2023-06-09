import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MembershipOption } from './membership-option';


@Component({
  selector: 'app-membership',
  templateUrl: './membership.component.html',
  styleUrls: ['./membership.component.css']
})
export class MembershipComponent implements OnInit {
  membershipOptions: MembershipOption[] = [];

  private backendUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.fetchMembershipOptions();
  }

  fetchMembershipOptions() {
    this.http.get<MembershipOption[]>(this.backendUrl + '/api/membership-options')
      .subscribe(options => {
        this.membershipOptions = options;
      });
  }
}
