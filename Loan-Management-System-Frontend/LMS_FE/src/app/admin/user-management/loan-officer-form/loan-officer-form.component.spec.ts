import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanOfficerFormComponent } from './loan-officer-form.component';

describe('LoanOfficerFormComponent', () => {
  let component: LoanOfficerFormComponent;
  let fixture: ComponentFixture<LoanOfficerFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanOfficerFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanOfficerFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
