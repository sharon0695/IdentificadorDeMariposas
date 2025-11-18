import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ObservacionesService, Observacion } from '../services/observaciones.service';

@Component({
  selector: 'app-observaciones',
  imports: [CommonModule, FormsModule],
  templateUrl: './observaciones.html',
  styleUrl: './observaciones.css',
})
export class Observaciones {
  observaciones: Observacion[] = [];
  nueva: Observacion = {
    especieId: '',
    usuarioId: '',
    comentario: ''
  };

  constructor(private service: ObservacionesService) {}

  ngOnInit() {
    this.listar();
  }

  listar() {
    this.service.listarObservaciones().subscribe(resp => this.observaciones = resp);
  }

  guardar() {
    this.nueva.fecha = new Date();
    this.service.crearObservacion(this.nueva).subscribe(() => {
      alert('Observación registrada');
      this.nueva = { especieId: '', usuarioId: '', comentario: '' };
      this.listar();
    });
  }
}