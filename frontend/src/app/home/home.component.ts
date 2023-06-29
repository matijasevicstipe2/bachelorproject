import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../security/authentication.service";
import {HttpClient} from "@angular/common/http";
import {UserDto} from "./userDto";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user: UserDto = {} as UserDto;

  private backendUrl = 'http://localhost:8080';

  constructor(private http: HttpClient,
              private authService: AuthenticationService,
              private router: Router) { }

  ngOnInit() {
    this.fetchUserInfo();
  }

  fetchUserInfo() {
    const username = this.authService.getAuthenticatedUserUsername();
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

  uploadProfilePicture() {
    const fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.accept = 'image/*';
    fileInput.addEventListener('change', (event) => {
      const files = (event.target as HTMLInputElement).files;
      if (files && files.length > 0) {
        const file = files[0];
        const formData = new FormData();
        formData.append('profilePicture', file);
        this.http
          .post<any>(this.backendUrl + '/api/uploadpicture', formData)
          .subscribe(
            (response) => {
              // Update the user profile picture
              this.user.profilePicture = response.profilePictureUrl;
            },
            (error) => {
              console.error(error);
            }
          );
      }
    });
    fileInput.click();
  }

  updateMembership() {
    this.router.navigate(['/membership']);
  }

  getQRCodeSource(qrCode: ArrayBuffer | string | null): string {
    if (qrCode instanceof ArrayBuffer) {
      const uintArray = new Uint8Array(qrCode);
      const regularArray = Array.from(uintArray); // Convert Uint8Array to regular array
      const base64String = btoa(String.fromCharCode.apply(null, regularArray));
      return 'data:image/png;base64,' + base64String;
    } else if (typeof qrCode === 'string') {
      return qrCode; // Assume it's already a URL or Base64 string
    } else {
      return ''; // Empty string if qrCode is null or undefined
    }
  }



}
