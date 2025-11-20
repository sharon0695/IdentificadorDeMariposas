import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EspecieService, Especie, ImagenesDetalladas, CaracteristicasMorfo } from '../services/especie.service';
import { Router } from '@angular/router';
import { ImageUploadService } from '../services/image-upload.service';

@Component({
  selector: 'app-crear-especie',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-especie.html',
  styleUrl: './crear-especie.css',
})
export class CrearEspecie {
  especie: Especie = {
    nombreCientifico: '',
    nombreComun: '',
    familia: '',
    tipoEspecie: '',
    descripcion: '',
    imagenes: [],
    imagenesDetalladas: {} as ImagenesDetalladas,
    caracteristicasMorfo: {} as CaracteristicasMorfo,
    ubicacionRecoleccion: '',
    registradoPor: '' 
  };

  archivosSeleccionados: File[] = [];
  archivosDetallados: {
    alaIzquierda?: File;
    alaDerecha?: File;
    antenas?: File;
    cuerpo?: File;
    patas?: File;
    cabeza?: File;
  } = {};

  isUploading = false;

  constructor(
    private especieService: EspecieService,
    private imageUploadService: ImageUploadService, 
    private router: Router
  ) {}

  async guardar() {
    if (this.isUploading) {
      return;
    }

    this.isUploading = true;

    try {
      if (this.archivosSeleccionados.length > 0) {
        // 1. Subir imágenes generales
        const uploadPromises = this.archivosSeleccionados.map(file => 
          this.imageUploadService.uploadImage(file)
        );
        const imageUrls = await Promise.all(uploadPromises);
        this.especie.imagenes = imageUrls;
      }

      // 2. Subir imágenes detalladas
      const detailedUploadPromises = Object.entries(this.archivosDetallados)
        .map(async ([parte, file]) => {
          if (file) {
            const url = await this.imageUploadService.uploadImage(file);
            return { parte, url };
          }
          return null;
        });

      const detailedImageResults = await Promise.all(detailedUploadPromises);
      
      detailedImageResults.forEach(result => {
        if (result) {
          // Asignación segura de tipos
          (this.especie.imagenesDetalladas as any)[result.parte] = result.url;
        }
      });

      // 2. Una vez que las imágenes están subidas y las URLs obtenidas, guardar la especie.
      this.especieService.createEspecie(this.especie).subscribe({
        next: () => {
          alert('Especie creada correctamente');
          this.resetForm();
        },
        error: (err) => {
          console.error('Error al crear la especie:', err);
          alert('Hubo un error al crear la especie.');
          this.isUploading = false;
        }
      });
    } catch (error) {
      console.error('Error al subir las imágenes:', error);
      alert('Hubo un error al subir las imágenes.');
      this.isUploading = false;
    }
  }

  onFileSelected(event: any) {
    this.archivosSeleccionados = Array.from(event.target.files);
  }

  onDetailedFileSelected(event: any, parte: keyof ImagenesDetalladas) {
    const file = event.target.files[0];
    if (file) {
      this.archivosDetallados[parte] = file;
    }
  }

  volver() {
    this.router.navigate(['/manejo-mariposas']);
  }

  private resetForm() {
    this.especie = { nombreCientifico: '', nombreComun: '', familia: '', tipoEspecie: '', descripcion: '', imagenes: [], imagenesDetalladas: {} as ImagenesDetalladas, caracteristicasMorfo: {} as CaracteristicasMorfo, ubicacionRecoleccion: '', registradoPor: '' };
    this.archivosSeleccionados = [];
    this.archivosDetallados = {};
    this.isUploading = false;
    // Si usas un <form>, también puedes resetearlo nativamente.
  }
}