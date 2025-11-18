import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EspecieService, Especie } from '../services/especie.service';

@Component({
  selector: 'app-crear-especie',
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-especie.html',
  styleUrl: './crear-especie.css',
})
export class CrearEspecie {
  especie: Especie = {
    nombreCientifico: '',
    nombreComun: '',
    familia: '',
    tipoEspecie: '',
    descripcion: '',
    imagenes: []
  };

  constructor(private service: EspecieService) {}

  guardar() {
    this.service.createEspecie(this.especie).subscribe(() => {
      alert('Especie creada correctamente');
      this.especie = {
        nombreCientifico: '',
        nombreComun: '',
        familia: '',
        tipoEspecie: '',
        descripcion: '',
        imagenes: []
      };
    });
  }
}