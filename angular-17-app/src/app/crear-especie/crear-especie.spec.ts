import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearEspecie } from './crear-especie';

describe('CrearEspecie', () => {
  let component: CrearEspecie;
  let fixture: ComponentFixture<CrearEspecie>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearEspecie]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearEspecie);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
