import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-reportes',
  imports: [CommonModule, FormsModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.css'
})
export class Reportes {

  apiUrl = 'http://localhost:8180/api/especies';

  fechaInicio!: string;
  fechaFin!: string;

  tipoSeleccionado: string = '';

  tipos = ['Diurna', 'Nocturna']; 
  familias = ['Nymphalidae', 'Papilionidae', 'Pieridae', 'Lycaenidae', 'Hesperiidae'];

  constructor(private http: HttpClient, private router:Router,) {}

  generarReporteGeneral() {

    if (!this.fechaInicio || !this.fechaFin) {
      alert('Debe seleccionar ambas fechas');
      return;
    }

    const url = `${this.apiUrl}/reporte?fechaInicio=${this.fechaInicio}&fechaFin=${this.fechaFin}`;

    this.http.get(url, { responseType: 'blob' }).subscribe(blob => {
      this.descargarArchivo(blob, 'reporte_general.pdf');
    });
  }

  generarReportePorTipo() {

    if (!this.tipoSeleccionado) {
      alert('Seleccione un tipo de especie');
      return;
    }

    const url = `${this.apiUrl}/reporte/tipo/${this.tipoSeleccionado}`;

    this.http.get(url, { responseType: 'blob' }).subscribe(blob => {
      this.descargarArchivo(blob, `reporte_tipo_${this.tipoSeleccionado}.pdf`);
    });
  }

  generarReportePorFamilia() {

    if (!this.tipoSeleccionado) {
      alert('Seleccione una familia de especie');
      return;
    }

    const url = `${this.apiUrl}/reporte/familia/${this.tipoSeleccionado}`;

    this.http.get(url, { responseType: 'blob' }).subscribe(blob => {
      this.descargarArchivo(blob, `reporte_familia_${this.tipoSeleccionado}.pdf`);
    });
  }

    volver() {
    this.router.navigate(['/manejo-mariposas']);
  }


  descargarReporteCompleto() {
    const url = `${this.apiUrl}/reporte/todo`;

    this.http.get(url, { responseType: 'blob' }).subscribe(blob => {
      this.descargarArchivo(blob, `reporte_especies_completo.pdf`);
    });
  }

  private descargarArchivo(blob: Blob, nombre: string) {
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = nombre;
    link.click();
  }
}
