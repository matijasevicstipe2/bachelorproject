import { Component, OnInit } from '@angular/core';
import { Gym } from '../gym/gym';
import { HttpClient } from '@angular/common/http';
import { CalendarOptions } from '@fullcalendar/core';
import { EventInput } from '@fullcalendar/core';
import { GroupClass } from './groupClass';
import dayGridPlugin from '@fullcalendar/daygrid';


@Component({
  selector: 'app-group-classes',
  templateUrl: './group-classes.component.html',
  styleUrls: ['./group-classes.component.css']
})
export class GroupClassesComponent implements OnInit {
  private backendUrl = 'http://localhost:8080';
  gyms: Gym[] = [];
  selectedGymId: number | null = null;
  groupClasses: GroupClass[] = [];
  calendarEvents: EventInput[] = [];
  calendarPlugins = [dayGridPlugin];

  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    events: this.calendarEvents,
    plugins: this.calendarPlugins
  };

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

  onSelectGym(event: any) {
    this.selectedGymId = event.target.value;
    this.fetchGroupClasses(this.selectedGymId);
  }

  fetchGroupClasses(gymId: number | null) {
    if (this.selectedGymId) {
      this.http.get<GroupClass[]>(this.backendUrl + `/api/group-classes/${gymId}`).subscribe(
        (response) => {
          this.groupClasses = response;
          this.updateCalendarEvents();
        },
        (error) => {
          console.log('Error occurred while fetching group classes:', error);
        }
      );
    } else {
      this.groupClasses = [];
      this.updateCalendarEvents();
    }
  }

  updateCalendarEvents() {
    this.calendarEvents = this.groupClasses.map((groupClass: GroupClass) => {
      return {
        title: groupClass.name,
        start: groupClass.schedule,
        allDay: false
      };
    });
  }
}
