import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleEspecie } from './detalle-especie';

describe('DetalleEspecie', () => {
  let component: DetalleEspecie;
  let fixture: ComponentFixture<DetalleEspecie>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleEspecie]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleEspecie);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
