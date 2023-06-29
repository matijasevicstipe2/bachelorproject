import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Gym } from "../gym/gym";

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {
  gymStats: GymStatsDto = {};
  gymVisits: GymVisitDto[] = [];
  selectedGymId: number | null = null;
  editSessionIndex: number | null = null;
  editedTitle: string | undefined = '';
  editedNotes: string | undefined = '';
  gyms: Gym[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchGymStats();
    this.fetchGymVisits();
    this.fetchGyms();
  }

  fetchGymStats(): void {
    // Replace the URL with your backend API endpoint for fetching gym stats
    const apiUrl = '/api/stats?username=username';

    this.http.get<GymStatsDto>(apiUrl).subscribe(
      (response: GymStatsDto) => {
        this.gymStats = response;
      },
      (error) => {
        console.error('Error fetching gym stats:', error);
      }
    );
  }

  fetchGymVisits(): void {
    // Replace the URL with your backend API endpoint for fetching gym visits
    const apiUrl = '/api/visits?username=username&gymId=' + (this.selectedGymId || '');

    this.http.get<GymVisitDto[]>(apiUrl).subscribe(
      (response: GymVisitDto[]) => {
        this.gymVisits = response;
      },
      (error) => {
        console.error('Error fetching gym visits:', error);
      }
    );
  }

  fetchGyms(): void {
    this.http.get<Gym[]>('/api/gyms').subscribe(
      (response) => {
        this.gyms = response;
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
      visit.title = this.editedTitle;
      visit.notes = this.editedNotes;

      // Replace the URL with your backend API endpoint for updating gym visit
      const apiUrl = '/api/update-visit';
      this.http.put(apiUrl, visit).subscribe(
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
}

interface GymVisitDto {
  enterTime?: string;
  exitTime?: string;
  title?: string;
  notes?: string;
}
