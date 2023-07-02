import { Component, Input } from '@angular/core';
import {EventApi} from "@fullcalendar/core";
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-event-popup',
  templateUrl: './event-popup.component.html',
  styleUrls: ['./event-popup.component.css']
})
export class EventPopupComponent {
  @Input() event!: EventApi;

  constructor(public activeModal: NgbActiveModal) {}
}
