import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MariposasService } from '../services/mariposas.service';
import { EspecieService, Especie } from '../services/especie.service';

@Component({
  selector: 'app-mariposas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mariposas.html',
  styleUrl: './mariposas.css',
})
export class Mariposas {

  mariposas: any[] = [];
  mariposasFiltradas: any[] = [];

  busqueda: string = "";
  observacion: string = "";
  imgIndex: number = 0;

  especies: Especie[] = [];
  especieSeleccionada: any = null;

  cargando = false;
  error: string | null = null;

  constructor(private especieService: EspecieService, private servicio: MariposasService) {}

  ngOnInit(): void {
    this.cargarEspecies();
  }

  cargarEspecies() {
    this.servicio.getEspecies().subscribe(data => {
      this.mariposas = data;
      this.mariposasFiltradas = [...data];
    });
  }

  seleccionar(esp: any) {
    this.especieSeleccionada = esp;
    this.observacion = esp.observacion || "";
    this.imgIndex = 0;
  }

  ngOnChanges() {
    this.filtrar();
  }

  filtrar() {
    this.mariposasFiltradas = this.mariposas.filter(m =>
      m.nombreComun.toLowerCase().includes(this.busqueda.toLowerCase())
    );
  }

  identificar() {
    alert("Función de identificación ejecutada. (Aquí pones tu lógica)");
  }

  anterior() {
    if (!this.especieSeleccionada?.imagenes?.length) return;
    this.imgIndex = (this.imgIndex - 1 + this.especieSeleccionada.imagenes.length) % this.especieSeleccionada.imagenes.length;
  }

  siguiente() {
    if (!this.especieSeleccionada?.imagenes?.length) return;
    this.imgIndex = (this.imgIndex + 1) % this.especieSeleccionada.imagenes.length;
  }

  // Seleccionar una especie para ver en el panel derecho
  verDetalles(especie: Especie) {
    this.especieSeleccionada = especie;
    // opcional: recargar detalle desde backend:
    if (especie.id) {
      this.especieService.getEspecie(especie.id).subscribe({
        next: full => this.especieSeleccionada = full,
        error: err => console.warn('No se pudo recargar detalle', err)
      });
    }
  }

  editarEspecie() {
    if (!this.especieSeleccionada || !this.especieSeleccionada.id) return;
    const nuevoNombreComun = prompt('Nuevo nombre común:', this.especieSeleccionada.nombreComun || '');
    if (nuevoNombreComun === null) return; // cancelado
    const payload: Partial<Especie> = { ...this.especieSeleccionada, nombreComun: nuevoNombreComun };
    this.especieService.updateEspecie(this.especieSeleccionada.id!, payload).subscribe({
      next: () => {
        // actualizar localmente para reflejar el cambio sin recargar todo
        this.especieSeleccionada = { ...this.especieSeleccionada!, nombreComun: nuevoNombreComun };
        this.especies = this.especies.map(e => e.id === this.especieSeleccionada!.id ? this.especieSeleccionada! : e);
      },
      error: (err) => {
        console.error(err);
        alert('Error actualizando especie.');
      }
    });
  }

  agregarImagenGeneral(url: string) {
    if (!this.especieSeleccionada?.id) return alert('Selecciona una especie primero');
    this.especieService.agregarImagenGeneral(this.especieSeleccionada.id, url).subscribe({
      next: () => {
        if (!this.especieSeleccionada!.imagenes) this.especieSeleccionada!.imagenes = [];
        this.especieSeleccionada!.imagenes!.push(url);
        alert('Imagen agregada correctamente');
      },
      error: (err) => {
        console.error(err);
        alert('Error al agregar imagen');
      }
    });
  }

  verMapa() {
    window.open('/mapa', '_blank');
  }

  logout() {
    localStorage.removeItem('usuario');
    window.location.href = '/login';
  }

  // Eliminar especie
  eliminarEspecie(id?: string) {
    if (!id) return;
    if (!confirm('¿Eliminar esta especie?')) return;
    this.especieService.deleteEspecie(id).subscribe({
      next: () => {
        this.especies = this.especies.filter(e => e.id !== id);
        if (this.especieSeleccionada?.id === id) this.especieSeleccionada = null;
      },
      error: (err) => {
        console.error(err);
        alert('Error eliminando especie.');
      }
    });
  }
}