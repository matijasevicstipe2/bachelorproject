import { Component, OnInit } from '@angular/core';
import { Gym } from '../gym/gym';
import { HttpClient } from '@angular/common/http';
import {Calendar, CalendarOptions, EventClickArg} from '@fullcalendar/core';
import { EventInput } from '@fullcalendar/core';
import { GroupClass } from './groupClass';
import dayGridPlugin from '@fullcalendar/daygrid';
import {EventPopupComponent} from "../event-popup/event-popup.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DatePipe} from "@angular/common";



@Component({
  selector: 'app-group-classes',
  templateUrl: './group-classes.component.html',
  styleUrls: ['./group-classes.component.css']
})
export class GroupClassesComponent implements OnInit {
  private backendUrl = 'http://localhost:8080';
  gyms: Gym[] = [];
  selectedGymId: string;
  groupClasses: GroupClass[] = [];
  calendarEvents: EventInput[] = [];
  calendarPlugins = [dayGridPlugin];
  isEventSelected: boolean = false;
  selectedEvent!: EventInput;

  calendarOptions: CalendarOptions = {
    initialView: 'dayGridWeek',
    events: this.calendarEvents,
    plugins: this.calendarPlugins,
    dayCellDidMount: this.customizeDayCell.bind(this),
    eventClick: this.handleEventClick.bind(this)
  };

  constructor(private http: HttpClient,
              private modalService: NgbModal,
              private datePipe: DatePipe) {
    this.selectedGymId = ""
  }

  ngOnInit() {
    this.fetchGyms();
    this.fetchGroupClasses(this.selectedGymId);
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

  fetchGroupClasses(gymId: string) {
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
      this.http.get<GroupClass[]>(this.backendUrl + `/api/group-classes`).subscribe(
        (response) => {
          this.groupClasses = response;
          this.updateCalendarEvents();
        },
        (error) => {
          console.log('Error occurred while fetching group classes:', error);
        }
      );
    }
  }

  updateCalendarEvents() {
    this.calendarEvents = this.groupClasses.map((groupClass: GroupClass) => {
      const currentDate = groupClass.schedule;
      const formattedDate = this.datePipe.transform(currentDate, 'dd MMMM yyyy');
      let formattedTime = this.datePipe.transform(currentDate, 'HH:mm');
      if (formattedTime == null) formattedTime = "08:00";
      const [hours, minutes] = formattedTime.split(':').map(Number);
      const totalMinutes = hours * 60 + minutes;
      const newTotalMinutes = totalMinutes + groupClass.duration;
      const newHours = Math.floor(newTotalMinutes / 60);
      const newMinutes = newTotalMinutes % 60;
      const newTimeString = `${newHours.toString().padStart(2, '0')}:${newMinutes.toString().padStart(2, '0')}`;
      return {
        title: groupClass.name,
        start:currentDate,
        allDay: false,
        extendedProps: {
          classDetails: {
            instructor: groupClass.trainer.firstName + ' ' + groupClass.trainer.lastName,
            location: groupClass.gym.name,
            date: formattedDate,
            start: formattedTime,
            end: newTimeString,
            duration: groupClass.duration + "min",
            description: 'Join us for a high-energy workout session.'
        }
      }
      };
    });
  }


  customizeDayCell(info: any) {
    const cell = info.el
    cell.classList.add('custom-day-cell');
  }

  handleEventClick(arg: EventClickArg) {
    const event = arg.event;
    const modalRef = this.modalService.open(EventPopupComponent, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
      keyboard: false
    });

    modalRef.componentInstance.event = event;
  }


}
