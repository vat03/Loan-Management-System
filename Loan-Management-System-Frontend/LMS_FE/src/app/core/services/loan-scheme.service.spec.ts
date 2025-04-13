import { TestBed } from '@angular/core/testing';

import { LoanSchemeService } from './loan-scheme.service';

describe('LoanSchemeService', () => {
  let service: LoanSchemeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanSchemeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
