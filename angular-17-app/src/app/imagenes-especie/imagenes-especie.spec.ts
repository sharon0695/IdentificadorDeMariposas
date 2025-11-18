import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImagenesEspecie } from './imagenes-especie';

describe('ImagenesEspecie', () => {
  let component: ImagenesEspecie;
  let fixture: ComponentFixture<ImagenesEspecie>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImagenesEspecie]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImagenesEspecie);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
