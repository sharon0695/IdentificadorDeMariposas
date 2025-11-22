import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EspecieService, Especie, ImagenesDetalladas, CaracteristicasMorfo } from '../services/especie.service';
import { Router } from '@angular/router';
import { ImageUploadService } from '../services/image-upload.service';
import { UbicacionService } from '../services/ubicacion.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-crear-especie',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-especie.html',
  styleUrl: './crear-especie.css',
})
export class CrearEspecie {
  ubicaciones: any[] = [];
  selectedUbicacionId: string | null = null;
  mostrarModalUbicacion = false;
  isUploading = false;

  especie: any = {
    nombreComun: '',
    nombreCientifico: '',
    familia: '',
    tipoEspecie: '',
    descripcion: '',
    fechaRegistro: new Date(),
    caracteristicasMorfo: { color: '', tamanoAlas: null, formaAntenas: '' },
    imagenes: [],
    imagenesDetalladas: {},
    registradoPor: '',         // setear con el user id si aplica
    ubicacionRecoleccion: null // <-- aquí irá el ObjectId (string) antes de enviar
  };

  nuevaUbicacion: any = {
    localidad: '',
    municipio: '',
    departamento: '',
    pais: '',
    geolocalizacion: { latitud: 0, longitud: 0 },
    ecosistema: ''
  };

  constructor(
    private especieService: EspecieService,
    private router: Router,
    private ubicacionService: UbicacionService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.cargarUbicaciones();
  }

  cargarUbicaciones() {
    this.ubicacionService.listar().subscribe({
      next: (data) => {
        this.ubicaciones = data;
      },
      error: (err) => console.error(err)
    });
  }

  onUbicacionChange() {
    console.log('selectedUbicacionId changed ->', this.selectedUbicacionId);

    if (this.selectedUbicacionId === 'crear') {
      this.mostrarModalUbicacion = true;
      this.especie.ubicacionRecoleccion = null;
      return;
    }

    if (this.selectedUbicacionId) {
      this.especie.ubicacionRecoleccion = this.selectedUbicacionId;
    } else {
      this.especie.ubicacionRecoleccion = null;
    }

    console.log('especie.ubicacionRecoleccion ahora ->', this.especie.ubicacionRecoleccion);
  }

  guardarNuevaUbicacion() {
    this.ubicacionService.crear(this.nuevaUbicacion).subscribe({
      next: (res) => {
        console.log('nueva ubicacion creada ->', res);
        this.mostrarModalUbicacion = false;

        // añadir a la lista y seleccionar automáticamente
        this.ubicaciones.push(res);
        this.selectedUbicacionId = res.id;
        this.especie.ubicacionRecoleccion = res.id;

        // limpiar nuevaUbicacion
        this.nuevaUbicacion = { localidad: '', municipio: '', departamento: '', pais: '', geolocalizacion: {latitud:0,longitud:0}, ecosistema: '' };

        console.log('ubicaciones actualizadas:', this.ubicaciones);
        console.log('selectedUbicacionId asignado a:', this.selectedUbicacionId);
      },
      error: (err) => {
        console.error('error creando ubicacion', err);
      }
    });
  }

  guardar() {
    if (!this.especie.ubicacionRecoleccion) {
      this.especie.ubicacionRecoleccion = null;
    }

    const payload = { ...this.especie,
      ubicacionRecoleccion: this.especie.ubicacionRecoleccion,
      registradoPor: this.authService.getUserId(),
      
    };

    console.log('DATA A ENVIAR:', payload);

    this.especieService.createEspecie(payload).subscribe({
      next: (res) => {
        console.log('especie creada', res);
        alert('Especie guardada correctamente');
        this.resetForm();
        this.router.navigate(['/manejo-mariposas']);
      },
      error: (err) => {
        console.error('error al crear especie', err);
        alert('Error al crear especie. Ver consola.');
      }
    });
  }


  onFileSelected(event: any) {
    const files = event.target.files;

    for (let file of files) {
      this.uploadImage(file).then(url => {
        this.especie.imagenes.push(url);
      });
    }
  }

  uploadImage(file: File): Promise<string> {
    return new Promise(resolve => {
      const reader = new FileReader();
      reader.onload = () => resolve(reader.result as string);
      reader.readAsDataURL(file);
    });
  }

  onDetailedFileSelected(event: any, tipo: string) {
    const file = event.target.files[0];

    this.uploadImage(file).then(url => {
      this.especie.imagenesDetalladas[tipo] = url;
    });
  }


  volver() {
    this.router.navigate(['/manejo-mariposas']);
  }

  private resetForm() {
    this.especie = { nombreCientifico: '', nombreComun: '', familia: '', tipoEspecie: '', descripcion: '', imagenes: [], imagenesDetalladas: {} as ImagenesDetalladas, caracteristicasMorfo: {} as CaracteristicasMorfo, ubicacionRecoleccion: '', registradoPor: '' };
    this.isUploading = false;
  }
}