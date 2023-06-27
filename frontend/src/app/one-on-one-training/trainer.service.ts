import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Trainer } from './trainer';

@Injectable({
  providedIn: 'root'
})
export class TrainerService {
  private backendUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  getTrainers(): Observable<Trainer[]> {
    return this.http.get<Trainer[]>(`${this.backendUrl}/api/trainers`);
  }

  sendEmailToTrainer(trainer: Trainer): Observable<void> {
    return this.http.post<void>(`${this.backendUrl}/api/send-email`, trainer);
  }

}
