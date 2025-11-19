import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DetalleEspecie } from '../detalle-especie/detalle-especie';
import { EspecieService, Especie } from '../services/especie.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-mariposas',
  standalone: true,
  imports: [CommonModule, FormsModule, DetalleEspecie],
  templateUrl: './mariposas.html',
  styleUrl: './mariposas.css',
})
export class Mariposas {
  busqueda: string = "";
  observacion: string = "";
  mensaje: string = "";
  imgIndex: number = 0;

  especies: Especie[] = [];
  especiesFiltradas: Especie[] = [];
  especieSeleccionada: any = null;

  cargando = false;
  error: string | null = null;

  constructor(private auth: AuthService, private router: Router, private especiesService: EspecieService) {}

  ngOnInit() {
    this.especiesService.getEspecies().subscribe({
      next: data => {
        console.log("ESPECIES RECIBIDAS:", data);
        this.especies = data;
        this.especiesFiltradas = data;
      },
      error: err => {
        console.error("ERROR AL CARGAR ESPECIES:", err);
      }
    });
  }



  cargarEspecies(): void {
    this.especiesService.getEspecies().subscribe(data => {
    this.especies = data;
    this.especiesFiltradas = data;
    });
  }

  seleccionar(esp: Especie) {
    this.especieSeleccionada = esp;
    this.imgIndex = 0;
  }

  irADetalle() {
    this.router.navigate(['/especies', this.especieSeleccionada!.id]);
  }

  irAImagenes() {
    this.router.navigate(['/especies', this.especieSeleccionada!.id, 'imagenes']);
  }

  irAObservaciones() {
    this.router.navigate(['/observaciones'], {
      queryParams: {
        especieId: this.especieSeleccionada?.id || null
      }
    });
  }

  irAMapa() {
    this.router.navigate(['/mapa'], {
      queryParams: {
        especieId: this.especieSeleccionada?.id || null
      }
    });
  }

  ngOnChanges() {
    this.filtrar();
  }

  filtrar(): void {
    const texto = this.busqueda.toLowerCase();
    this.especiesFiltradas = this.especies.filter(e =>
    e.nombreComun.toLowerCase().includes(texto) ||
    e.nombreCientifico?.toLowerCase().includes(texto)
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

  agregarImagenGeneral(url: string) {
    if (!this.especieSeleccionada?.id) return alert('Selecciona una especie primero');
    this.especiesService.agregarImagenGeneral(this.especieSeleccionada.id, url).subscribe({
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

  getImagenActual(): string {
  if (!this.especieSeleccionada || !this.especieSeleccionada.imagenes) {
    return 'assets/default-mariposa.png';
  }
  return this.especieSeleccionada.imagenes[this.imgIndex] || 'assets/default-mariposa.png';
}

  verMapa() {
    window.open('/mapa', '_blank');
  }

  logout() {
    if (!confirm('¿Seguro que quieres cerrar sesión?')) return;
    this.auth.logoutRemote().subscribe({
      next: () => {
        this.auth.logout();
        this.mensaje = 'Sesión cerrada';
        this.router.navigateByUrl('/login');
      },
      error: () => {
        this.auth.logout();
        this.router.navigateByUrl('/login');
      }
    });
  }

  // Eliminar especie
  eliminarEspecie(id?: string) {
    if (!id) return;
    if (!confirm('¿Eliminar esta especie?')) return;
    this.especiesService.deleteEspecie(id).subscribe({
      next: () => {
        this.especies = this.especies.filter(e => e.id !== id);
      },
      error: (err) => {
        console.error(err);
        alert('Error eliminando especie.');
      }
    });
  }
}