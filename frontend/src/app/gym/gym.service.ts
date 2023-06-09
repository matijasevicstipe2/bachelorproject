import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GymService {

  private apiUrl = 'http://localhost:8080/api/gyms';

  constructor(private http: HttpClient) { }

  getAllGyms(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  // Add other methods for creating, updating, deleting gyms, etc.
}
