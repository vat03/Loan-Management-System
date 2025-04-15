import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewLoansComponent } from './view-loans.component';

describe('ViewLoansComponent', () => {
  let component: ViewLoansComponent;
  let fixture: ComponentFixture<ViewLoansComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewLoansComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewLoansComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
