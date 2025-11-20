import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

export interface Especie {
  id?: string;
  nombreCientifico?: string;
  nombreComun: string;
  familia?: string;
  tipoEspecie?: string;
  descripcion?: string;
  imagenes: string[];
  imagenesDetalladas?: ImagenesDetalladas;
  caracteristicasMorfo: CaracteristicasMorfo;
  ubicacionRecoleccion?: string;
  fechaRegistro?: Date;
  registradoPor?: string;
}
  export interface ImagenesDetalladas {
    alaIzquierda?: string;
    alaDerecha?: string;
    antenas?: string;
    cuerpo?: string;
    patas?: string;
    cabeza?: string;
  }
  export interface CaracteristicasMorfo {
    color?: string;
    tamanoAlas?: number;
    formaAntenas?: string;
  }
  

@Injectable({
  providedIn: 'root'
})
export class EspecieService {

  private apiUrl = 'http://localhost:8180/api/especies';

  constructor(private http: HttpClient) {}

  /** Transformar MongoDB → Angular */
  private mapEspecie(e: any): Especie {
    return {
      id: e._id || e.id,
      nombreCientifico: e.nombreCientifico,
      nombreComun: e.nombreComun,
      familia: e.familia,
      tipoEspecie: e.tipoEspecie,
      descripcion: e.descripcion,
      imagenes: e.imagenes,
      imagenesDetalladas: e.imagenesDetalladas,
      caracteristicasMorfo: e.caracteristicasMorfo,
      ubicacionRecoleccion: e.ubicacionRecoleccion,
      fechaRegistro: e.fechaRegistro,
      registradoPor: e.registradoPor
    };
  }

  /** Obtener todas */
  getEspecies(): Observable<Especie[]> {
    console.log('%c[SERVICE] Llamando a /api/especies/listar', 'color: blue;');
    return this.http.get<any[]>(`${this.apiUrl}/listar`)
      .pipe(map(list => list.map(e => this.mapEspecie(e))));
  }

  /** Obtener por ID */
  getEspecie(id: string): Observable<Especie> {
    return this.http.get<any>(`${this.apiUrl}/${id}`)
      .pipe(map(e => this.mapEspecie(e)));
  }

  /** Crear */
  createEspecie(especie: Especie): Observable<Especie> {
    return this.http.post<any>(this.apiUrl, especie)
      .pipe(map(e => this.mapEspecie(e)));
  }

  /** Eliminar */
  deleteEspecie(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  /** Agregar imagen general */
  agregarImagenGeneral(id: string, urlImagen: string): Observable<any> {
    const params = { url: urlImagen };
    return this.http.post(`${this.apiUrl}/${id}/imagen-general`, null, { params });
  }

  /** Agregar imagen detallada */
  agregarImagenDetallada(id: string, parte: string, urlImagen: string): Observable<any> {
    const params = { parte, url: urlImagen };
    return this.http.post(`${this.apiUrl}/${id}/imagen-detallada`, null, { params });
  }

  actualizarUbicacion(id: string, ubicacion: string) {
    return this.http.patch(`${this.apiUrl}/${id}`, {
      ubicacionRecoleccion: ubicacion
    });
  }
}
