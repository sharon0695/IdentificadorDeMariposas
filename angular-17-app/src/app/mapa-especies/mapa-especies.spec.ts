import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapaEspecies } from './mapa-especies';

describe('MapaEspecies', () => {
  let component: MapaEspecies;
  let fixture: ComponentFixture<MapaEspecies>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapaEspecies]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapaEspecies);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
