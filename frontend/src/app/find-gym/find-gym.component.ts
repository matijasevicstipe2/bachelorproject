import {Component, OnInit} from '@angular/core';
import {Gym} from "../gym/gym";
import {HttpClient} from "@angular/common/http";
import {GymService} from "../gym/gym.service";

@Component({
  selector: 'app-find-gym',
  templateUrl: './find-gym.component.html',
  styleUrls: ['./find-gym.component.css']
})
export class FindGymComponent implements OnInit {
  gyms: Gym[] = [];
  peopleInGym: Map<number, number> = new Map<number, number>();

  private backendUrl = 'http://localhost:8080';

  constructor(private http: HttpClient,
              private gymService: GymService) { }

  ngOnInit() {
    this.gymService.getUpdatedPeopleInGym(5000).subscribe(data => {
      this.peopleInGym = data;
    });

    this.fetchGyms();
  }

  fetchGyms() {
    this.http.get<Gym[]>(`${this.backendUrl}/api/gyms`).subscribe(
      (response) => {
        this.gyms = response;
        this.linkGymsWithPeopleInGym();
      },
      (error) => {
        console.log('Error occurred while fetching gyms:', error);
      }
    );
  }

  linkGymsWithPeopleInGym() {
    for (const gym of this.gyms) {
      const gymId = gym.id;
      if (this.peopleInGym.has(gymId)) {
        gym.peopleInGym = this.peopleInGym.get(gymId);
      } else {
        gym.peopleInGym = 0; // Default value if no people count is available
      }
    }
  }
}
