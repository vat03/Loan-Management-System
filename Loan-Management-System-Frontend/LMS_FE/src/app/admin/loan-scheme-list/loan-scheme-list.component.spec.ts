import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanSchemeListComponent } from './loan-scheme-list.component';

describe('LoanSchemeListComponent', () => {
  let component: LoanSchemeListComponent;
  let fixture: ComponentFixture<LoanSchemeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanSchemeListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoanSchemeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
