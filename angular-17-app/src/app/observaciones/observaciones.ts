import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ObservacionesService, Observacion } from '../services/observaciones.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-observaciones',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './observaciones.html',
  styleUrl: './observaciones.css',
})
export class Observaciones {

  especieId: string = "";
  observaciones: Observacion[] = [];

  nueva: Observacion = {
    especieId: '',
    usuarioId: '',
    comentario: ''
  };

  constructor(
    private route: ActivatedRoute, 
    private service: ObservacionesService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.especieId = this.route.snapshot.queryParamMap.get('especieId') || "";
    this.listar();
    const uid = this.auth.getUserId();
    if (!uid) {
      alert("No hay usuario autenticado");
      return;
    }
    this.nueva.usuarioId = uid;
  }

  listar() {
    if (!this.especieId) return;

    this.service.listarPorEspecie(this.especieId)
      .subscribe(resp => this.observaciones = resp);
  }

  
  volver() {
    this.router.navigate(['/manejo-mariposas']);
  }

  guardar() {
    const uid = this.auth.getUserId();
    if (!uid) {
      alert('No hay usuario autenticado');
      return;
    }
    this.nueva.usuarioId = uid;
    this.nueva.especieId = this.especieId;
    this.nueva.fecha = new Date();

    this.service.crearObservacion(this.nueva).subscribe(() => {
      alert('Observación registrada');
      this.nueva.comentario = '';
      this.listar();
    });
  }
}
