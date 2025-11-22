import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DetalleEspecie } from '../detalle-especie/detalle-especie';
import { EspecieService, Especie } from '../services/especie.service';
import { AuthService } from '../services/auth.service';
import { UbicacionService } from '../services/ubicacion.service';
import { Observacion, ObservacionesService } from '../services/observaciones.service';

@Component({
  selector: 'app-mariposas',
  standalone: true,
  imports: [CommonModule, FormsModule, DetalleEspecie],
  templateUrl: './mariposas.html',
  styleUrl: './mariposas.css',
})
export class Mariposas {
  busqueda: string = "";
  nuevaObservacion: string = "";
  mensaje: string = "";
  imgIndex: number = 0;

  especies: Especie[] = [];
  especiesFiltradas: Especie[] = [];
  especieSeleccionada: any = null;
  observaciones: Observacion[] = [];

  cargando = false;
  error: string | null = null;
  userRole: string | null = null;
  backendUrl: string = 'http://localhost:8180';

    constructor(
    protected auth: AuthService,
    private router: Router,
    private especiesService: EspecieService,
    private observacionesService: ObservacionesService,
    private ubicacionService: UbicacionService
  ) {}
  
  ngOnInit() {
    this.userRole = this.auth.getUserRole();
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

  guardarObservacion() {
    const userId = this.auth.getUserId();
    if (!userId) {
      alert("Usuario no autenticado");
      return;
    }

    if (!this.especieSeleccionada?.id) {
      alert("Selecciona una especie primero");
      return;
    }

    const data = {
      comentario: this.nuevaObservacion,
      usuarioId: userId,
      especieId: this.especieSeleccionada.id,
      fecha: new Date()
    };

    this.observacionesService.crearObservacion(data).subscribe({
      next: () => {
        this.nuevaObservacion = "";
        this.seleccionar(this.especieSeleccionada); 
      },
      error: err => console.error(err)
    });
  }

  cargarEspecies(): void {
    this.especiesService.getEspecies().subscribe(data => {
    this.especies = data;
    this.especiesFiltradas = data;
    });
  }

  seleccionar(esp: Especie) {
    console.log('Datos completos de la especie seleccionada:', esp);
    this.especieSeleccionada = esp;
    this.imgIndex = 0;

    if (esp.ubicacionRecoleccion) {
      this.ubicacionService.getUbicacion(esp.ubicacionRecoleccion).subscribe({
        next: (ubicacion) => {
          console.log('Datos de la ubicación:', ubicacion);
        },
        error: (err) => console.error('Error al obtener la ubicación:', err),
      });
    }

    if (esp.id) {
      this.observacionesService.listarPorEspecie(esp.id).subscribe({
        next: obs => this.observaciones = obs,
        error: err => console.error(err)
      });
    }
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
    if (!this.especieSeleccionada) {
      alert('Por favor, selecciona una especie para ver su ubicación en el mapa.');
      return;
    }

    if (!this.especieSeleccionada.ubicacionRecoleccion) {
      alert('Esta especie no tiene una ubicación de recolección registrada.');
      return;
    }

    const url = this.router.serializeUrl(
      this.router.createUrlTree(['/mapa'], {
        queryParams: {
          especieId: this.especieSeleccionada?.id || null,
          departamento: this.especieSeleccionada.ubicacionRecoleccion
        }
      })
    );
    window.open(url, '_blank');
  }

  ngOnChanges() {
    this.filtrar();
  }

  filtrar(): void {
  const texto = this.busqueda.trim().toLowerCase();

  if (!texto) {
    this.especiesFiltradas = [...this.especies];
    return;
  }

  this.especiesFiltradas = this.especies.filter(e =>
    (e.nombreComun ?? '').toLowerCase().includes(texto) ||
    (e.nombreCientifico ?? '').toLowerCase().includes(texto)
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

  crearEspecie() {
    this.router.navigate(['/especies/crear']);
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
      return this.backendUrl + "/images/default.png"; 
    }

    const img = this.especieSeleccionada.imagenes[this.imgIndex];
    return this.backendUrl + "/" + img;
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

  eliminarEspecie(id?: string) {
    if (!id) return;
    if (!confirm('¿Eliminar esta especie?')) return;
    this.especiesService.deleteEspecie(id).subscribe({
      next: () => {
        this.especies = this.especies.filter(e => e.id !== id);
        this.filtrar(); 
      },
      error: (err) => {
        console.error(err);
        alert('Error eliminando especie.');
      }
    });
  }

}