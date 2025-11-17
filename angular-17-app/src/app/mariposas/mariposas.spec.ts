import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Mariposas } from './mariposas';

describe('Mariposas', () => {
  let component: Mariposas;
  let fixture: ComponentFixture<Mariposas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Mariposas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Mariposas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
