import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, interval, Observable, Subject, takeUntil} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GymService {

  private backendUrl = 'http://localhost:8080';

  private stopInterval$ = new Subject<void>();

  constructor(private http: HttpClient) { }

  getAllGyms(): Observable<any> {
    return this.http.get<any>(this.backendUrl);
  }

  getPeopleInGym(): Observable<Map<number, number>> {
    return this.http.get<{ [key: number]: number }>(`${this.backendUrl}/api/people-in-gym`).pipe(
      map(responseData => {
        // Convert the response data object into a Map
        const dataMap = new Map<number, number>();
        for (const key in responseData) {
          if (responseData.hasOwnProperty(key)) {
            dataMap.set(parseInt(key), responseData[key]);
          }
        }
        return dataMap;
      }),
      catchError(error => {
        console.error('Error fetching data:', error);
        throw error; // Rethrow the error for further handling
      })
    );
  }


  getUpdatedPeopleInGym(intervalTime: number): Observable<Map<number, number>> {
    return interval(intervalTime).pipe(
      switchMap(() => this.getPeopleInGym()),
      map(updatedData => {
        const mergedData = new Map<number, number>([...updatedData]);
        return mergedData;
      }),
      takeUntil(this.stopInterval$)
    );
  }

  // Call this method to stop the interval
  stopInterval() {
    this.stopInterval$.next();
  }
}
