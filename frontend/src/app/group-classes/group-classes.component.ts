import { Component, OnInit } from '@angular/core';
import { Gym } from '../gym/gym';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-group-classes',
  templateUrl: './group-classes.component.html',
  styleUrls: ['./group-classes.component.css']
})
export class GroupClassesComponent implements OnInit {
  private backendUrl = 'http://localhost:8080';
  gyms: Gym[] = [];
  selectedGymId: number | null = null;
  groupClasses: any[] = [];

  // Define the hours of the day
  hours: string[] = ['07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00'];

  // Define the days of the week
  days: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.fetchGyms();
  }

  fetchGyms() {
    this.http.get<Gym[]>(this.backendUrl + '/api/gyms').subscribe(
      (response) => {
        this.gyms = response;
      },
      (error) => {
        console.log('Error occurred while fetching gyms:', error);
      }
    );
  }

  onSelectGym(gymId: number) {
    this.selectedGymId = gymId;
    this.fetchGroupClasses();
  }

  fetchGroupClasses() {
    if (this.selectedGymId) {
      this.http.get<any[]>(this.backendUrl + `/api/group-classes?gymId=${this.selectedGymId}`).subscribe(
        (response) => {
          this.groupClasses = response;
        },
        (error) => {
          console.log('Error occurred while fetching group classes:', error);
        }
      );
    } else {
      this.groupClasses = [];
    }
  }

  getSelectedGymName() {
    const gym = this.gyms.find(g => g.id === this.selectedGymId);
    return gym ? gym.name : '';
  }

  getGroupClasses(hour: string, day: string) {
    return this.groupClasses.filter(gc => gc.schedule === `${day} ${hour}`);
  }

  onSelectClass(hour: string, day: string) {
    const groupClasses = this.getGroupClasses(hour, day);
    // Implement logic for selecting a group class and sending an email to the trainer's email address
  }
}

