import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GymDashboardComponent } from './gym-dashboard.component';

describe('GymDashboardComponent', () => {
  let component: GymDashboardComponent;
  let fixture: ComponentFixture<GymDashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GymDashboardComponent]
    });
    fixture = TestBed.createComponent(GymDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
