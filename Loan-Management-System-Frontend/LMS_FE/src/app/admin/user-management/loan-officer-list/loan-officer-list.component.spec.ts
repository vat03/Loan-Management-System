import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanOfficerListComponent } from './loan-officer-list.component';

describe('LoanOfficerListComponent', () => {
  let component: LoanOfficerListComponent;
  let fixture: ComponentFixture<LoanOfficerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanOfficerListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanOfficerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
