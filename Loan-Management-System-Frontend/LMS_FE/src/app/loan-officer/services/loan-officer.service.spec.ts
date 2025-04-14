import { TestBed } from '@angular/core/testing';

import { LoanOfficerService } from './loan-officer.service';

describe('LoanOfficerService', () => {
  let service: LoanOfficerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanOfficerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
