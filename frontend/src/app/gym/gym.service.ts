import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, interval } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GymService {

  private backendUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  getAllGyms(): Observable<any> {
    return this.http.get<any>(this.backendUrl);
  }

  getPeopleInGym(): Observable<Map<number, number>> {
    // Make a GET request to fetch the number of people in each gym
    return this.http.get<Map<number, number>>(`${this.backendUrl}/api/people-in-gym`);
  }

  getUpdatedPeopleInGym(intervalTime: number): Observable<Map<number, number>> {
    // Fetch the initial data and then continue updating it at the specified interval
    return this.getPeopleInGym().pipe(
      switchMap((initialData: Map<number, number>) =>
        interval(intervalTime).pipe(
          switchMap(() => this.getPeopleInGym()),
          map(updatedData => new Map([...initialData, ...updatedData]))
        )
      )
    );
  }
}
