import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Mariposas } from '../mariposas/mariposas';

@Component({
  selector: 'app-detalle-especie',
  imports: [CommonModule],
  templateUrl: './detalle-especie.html',
  styleUrl: './detalle-especie.css',
})
export class DetalleEspecie {
  @Input() especie: any;
  constructor(private mariposas: Mariposas) {}

  getImagenDetallada(tipo: string): string {
    return this.mariposas.getImagenDetallada(tipo);
  }
}
