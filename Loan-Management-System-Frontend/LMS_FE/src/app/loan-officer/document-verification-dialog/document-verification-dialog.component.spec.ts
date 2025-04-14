import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentVerificationDialogComponent } from './document-verification-dialog.component';

describe('DocumentVerificationDialogComponent', () => {
  let component: DocumentVerificationDialogComponent;
  let fixture: ComponentFixture<DocumentVerificationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocumentVerificationDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DocumentVerificationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
