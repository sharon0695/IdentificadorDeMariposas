import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Especie } from '../services/especie.service';

@Component({
  selector: 'app-detalle-especie',
  imports: [CommonModule],
  templateUrl: './detalle-especie.html',
  styleUrl: './detalle-especie.css',
})
export class DetalleEspecie {
  @Input() especie: any;
}
