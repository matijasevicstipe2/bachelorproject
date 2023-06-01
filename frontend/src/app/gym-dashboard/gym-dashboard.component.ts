
import { Component, OnInit } from '@angular/core';
import { GymService } from '../gym/gym.service';
import {Gym} from "../gym/gym"; // Replace with your gym service path

@Component({
  selector: 'app-gym-dashboard',
  templateUrl: './gym-dashboard.component.html',
  styleUrls: ['./gym-dashboard.component.css']
})
export class GymDashboardComponent implements OnInit {
  gyms: Gym[];

  constructor(private gymService: GymService) {
    this.gyms = [];
  }

  ngOnInit() {
    this.getGyms();
  }

  getGyms() {
    this.gymService.getAllGyms().subscribe((gyms: Gym[]) => {
      this.gyms = gyms;
    });
  }

  selectGym(gym: Gym) {
    // Handle the selection logic here, such as storing the selected gym ID
    // in a service or shared state, and navigating to the gym details page
  }
}
