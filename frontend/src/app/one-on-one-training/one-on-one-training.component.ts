import {Component, OnInit} from '@angular/core';
import {Trainer} from "./trainer";
import {TrainerService} from "./trainer.service";

@Component({
  selector: 'app-one-on-one-training',
  templateUrl: './one-on-one-training.component.html',
  styleUrls: ['./one-on-one-training.component.css']
})
export class OneOnOneTrainingComponent implements OnInit {
  trainers: Trainer[] = [];

  constructor(private trainerService: TrainerService) { }

  ngOnInit() {
    this.fetchTrainers();
  }

  fetchTrainers() {
    this.trainerService.getTrainers().subscribe(
      (response) => {
        this.trainers = response;
      },
      (error) => {
        console.log('Error occurred while fetching trainers:', error);
      }
    );
  }

  selectTrainer(trainer: Trainer) {
    this.trainerService.sendEmailToTrainer(trainer).subscribe(
      () => {
        console.log('Email sent to trainer:', trainer.email);
      },
      (error) => {
        console.log('Error occurred while sending email to trainer:', error);
      }
    );
  }
}
