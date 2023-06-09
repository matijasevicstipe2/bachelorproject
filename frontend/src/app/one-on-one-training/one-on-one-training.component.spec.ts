import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneOnOneTrainingComponent } from './one-on-one-training.component';

describe('OneOnOneTrainingComponent', () => {
  let component: OneOnOneTrainingComponent;
  let fixture: ComponentFixture<OneOnOneTrainingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OneOnOneTrainingComponent]
    });
    fixture = TestBed.createComponent(OneOnOneTrainingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
