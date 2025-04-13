import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanSchemeFormComponent } from './loan-scheme-form.component';

describe('LoanSchemeFormComponent', () => {
  let component: LoanSchemeFormComponent;
  let fixture: ComponentFixture<LoanSchemeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoanSchemeFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoanSchemeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
