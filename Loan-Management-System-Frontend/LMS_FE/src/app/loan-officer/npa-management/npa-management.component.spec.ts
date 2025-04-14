import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NpaManagementComponent } from './npa-management.component';

describe('NpaManagementComponent', () => {
  let component: NpaManagementComponent;
  let fixture: ComponentFixture<NpaManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NpaManagementComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NpaManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
