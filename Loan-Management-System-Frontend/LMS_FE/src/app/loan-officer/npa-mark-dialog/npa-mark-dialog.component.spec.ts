import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NpaMarkDialogComponent } from './npa-mark-dialog.component';

describe('NpaMarkDialogComponent', () => {
  let component: NpaMarkDialogComponent;
  let fixture: ComponentFixture<NpaMarkDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NpaMarkDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NpaMarkDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
