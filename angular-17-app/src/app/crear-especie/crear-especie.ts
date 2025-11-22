import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EspecieService, Especie, ImagenesDetalladas, CaracteristicasMorfo } from '../services/especie.service';
import { Router } from '@angular/router';
import { ImageUploadService } from '../services/image-upload.service';
import { UbicacionService } from '../services/ubicacion.service';

@Component({
  selector: 'app-crear-especie',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-especie.html',
  styleUrl: './crear-especie.css',
})
export class CrearEspecie {
  especie: any = {
  nombreComun: "",
  nombreCientifico: "",
  familia: "",
  tipoEspecie: "",
  descripcion: "",
  ubicacionRecoleccion: "",
  imagenes: [],
  imagenesDetalladas: {
    alaIzquierda: "",
    alaDerecha: "",
    antenas: "",
    cuerpo: "",
    patas: "",
    cabeza: ""
  },
  caracteristicasMorfo: {
    color: "",
    tamanoAlas: "",
    formaAntenas: ""
  }
};

isUploading = false;

ubicaciones: any[] = [];
mostrarNuevaUbicacion = false;

nuevaUbicacion = {
  localidad: '',
  municipio: '',
  departamento: '',
  pais: '',
  geolocalizacion: { latitud: 0, longitud: 0 },
  ecosistema: ''
};


  constructor(
    private especieService: EspecieService,
    private imageUploadService: ImageUploadService, 
    private router: Router,
    private ubicacionService: UbicacionService
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

  crearUbicacion() {
    this.ubicacionService.crear(this.nuevaUbicacion).subscribe({
      next: (res) => {
        alert("Ubicación creada");
        this.mostrarNuevaUbicacion = false;

        // Recargar lista
        this.cargarUbicaciones();

        // Seleccionar automáticamente la nueva
        this.especie.ubicacionRecoleccion = res.id;
      },
      error: (err) => console.error(err)
    });
  }

  guardar() {
    this.isUploading = true;

    const especieEnviar = {
      ...this.especie,
      fechaRegistro: new Date(),
      registradoPor: localStorage.getItem("userId") || null,
      ubicacionRecoleccion: this.especie.ubicacionRecoleccion
    };

    this.especieService.createEspecie(especieEnviar).subscribe({
      next: (res) => {
        alert("Especie registrada con éxito");
        this.isUploading = false;
      },
      error: (err) => {
        console.error(err);
        alert("Error al registrar la especie");
        this.isUploading = false;
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