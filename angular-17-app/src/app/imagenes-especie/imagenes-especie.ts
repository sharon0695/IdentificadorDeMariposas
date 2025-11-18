import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EspecieService } from '../services/especie.service';

@Component({
  selector: 'app-imagenes-especie',
  imports: [CommonModule, FormsModule],
  templateUrl: './imagenes-especie.html',
  styleUrl: './imagenes-especie.css',
})
export class ImagenesEspecie {
  id = '';
  url = '';
  parte = '';

  constructor(private service: EspecieService) {}

  agregarGeneral() {
    this.service.agregarImagenGeneral(this.id, this.url)
      .subscribe(() => alert('Imagen general agregada'));
  }

  agregarDetallada() {
    this.service.agregarImagenDetallada(this.id, this.parte, this.url)
      .subscribe(() => alert('Imagen detallada agregada'));
  }
}