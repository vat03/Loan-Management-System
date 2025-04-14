import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanOfficerDashboardComponent } from './loan-officer-dashboard.component';

describe('LoanOfficerDashboardComponent', () => {
  let component: LoanOfficerDashboardComponent;
  let fixture: ComponentFixture<LoanOfficerDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoanOfficerDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoanOfficerDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
