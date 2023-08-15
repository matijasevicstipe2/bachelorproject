import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Gym } from "../gym/gym";
import {AuthenticationService} from "../security/authentication.service";

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {
  private backendUrl = 'http://localhost:8080';
  gymStats: GymStatsDto = {};
  gymVisits: GymVisitDto[] = [];
  selectedGymId: string;
  editSessionIndex: number | null = null;
  editedTitle: string | undefined = '';
  editedNotes: string | undefined = '';
  gyms: Gym[] = [];

  constructor(private http: HttpClient,
              private authService: AuthenticationService) {
    this.selectedGymId = ""
  }

  ngOnInit(): void {
    this.fetchGyms();
    this.fetchGymStats();
    this.fetchGymVisits();
  }

  fetchGymStats(): void {
    const username = this.authService.getAuthenticatedUserUsername();
    const apiUrl = '/api/stats?username=' + (username) + '&gymId=' + (this.selectedGymId || '');
    this.http.get<GymStatsDto>(this.backendUrl + apiUrl).subscribe(
      (response: GymStatsDto) => {
        this.gymStats = response;
      },
      (error) => {
        console.error('Error fetching gym stats:', error);
      }
    );
  }

  fetchGymVisits(): void {
    const username = this.authService.getAuthenticatedUserUsername();
    const apiUrl = '/api/visits?username=' + (username) + '&gymId=' + (this.selectedGymId || '');

    this.http.get<GymVisitDto[]>(this.backendUrl + apiUrl).subscribe(
      (response: GymVisitDto[]) => {
        this.gymVisits = response;
      },
      (error) => {
        console.error('Error fetching gym visits:', error);
      }
    );
  }

  fetchGyms(): void {
    this.http.get<Gym[]>(this.backendUrl + '/api/gyms').subscribe(
      (response) => {
        this.gyms = response;
        console.log(this.gyms[0].id)
      },
      (error) => {
        console.log('Error occurred while fetching gyms:', error);
      }
    );
  }

  selectGym(event: any): void {
    this.selectedGymId = event.target.value;
    this.fetchGymVisits();
  }

  startEditing(index: number): void {
    this.editSessionIndex = index;
    const visit = this.gymVisits[index];
    this.editedTitle = visit.title;
    this.editedNotes = visit.notes;
  }

  cancelEditing(): void {
    this.editSessionIndex = null;
    this.editedTitle = '';
    this.editedNotes = '';
  }

  saveChanges(): void {
    if (this.editSessionIndex !== null) {
      const visit = this.gymVisits[this.editSessionIndex];
      visit.id = this.gymVisits[this.editSessionIndex].id;
      visit.title = this.editedTitle;
      visit.notes = this.editedNotes;

      // Replace the URL with your backend API endpoint for updating gym visit
      const apiUrl = '/api/update-visit';
      this.http.put(this.backendUrl + apiUrl, visit).subscribe(
        () => {
          console.log('Gym visit updated successfully');
          this.editSessionIndex = null;
          this.editedTitle = '';
          this.editedNotes = '';
        },
        (error) => {
          console.error('Error updating gym visit:', error);
        }
      );
    }
  }
}

interface GymStatsDto {
  visitsWeek?: number;
  visitsMonth?: number;
  visitsYear?: number;
  visitsTotal?: number;
  visitsWeekHours?: number;
  visitsWeekMin?: number;
  visitsMonthHours?: number;
  visitsMonthMin?: number;
  visitsYearHours?: number;
  visitsYearMin?: number;
  visitsTotalHours?: number;
  visitsTotalMin?: number;
  visitsWeekSec?: number;
  visitsMonthSec?: number;
  visitsYearSec?: number;
  visitsTotalSec?: number;
}

interface GymVisitDto {
  id?: number;
  enterTime?: string;
  exitTime?: string;
  title?: string;
  notes?: string;
}
