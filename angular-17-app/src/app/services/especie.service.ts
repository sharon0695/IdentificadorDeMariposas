import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Especie {
  id?: string;
  nombreCientifico?: string;
  nombreComun?: string;
  familia?: string;
  tipoEspecie?: string;
  descripcion?: string;
  imagenes?: string[];
  imagenesDetalladas?: {
    alaIzquierda?: string;
    alaDerecha?: string;
    antenas?: string;
    cuerpo?: string;
    patas?: string;
    cabeza?: string;
  };
  caracteristicasMorfo?: {
    color?: string[];
    tamanoAlasCm?: number;
    formaAntenas?: string;
  };
  ubicacionRecoleccion?: string;
  fechaRegistro?: string;
  registradoPor?: string;
}

@Injectable({
  providedIn: 'root'
})
export class EspecieService {

  // Ajusta la URL base si tu backend corre en otra parte
  private baseUrl = 'http://localhost:8080/api/especies';

  constructor(private http: HttpClient) {}

  getEspecies(): Observable<Especie[]> {
    return this.http.get<Especie[]>(this.baseUrl);
  }

  getEspecie(id: string): Observable<Especie> {
    return this.http.get<Especie>(`${this.baseUrl}/${id}`);
  }

  createEspecie(payload: Partial<Especie>) {
    return this.http.post<Especie>(this.baseUrl, payload);
  }

  updateEspecie(id: string, payload: Partial<Especie>) {
    return this.http.put<Especie>(`${this.baseUrl}/${id}`, payload);
  }

  deleteEspecie(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  // Endpoints que definimos en el backend para agregar imágenes (URL)
  agregarImagenGeneral(id: string, url: string) {
    // POST /api/especies/{id}/imagen-general?url=...
    return this.http.post(`${this.baseUrl}/${id}/imagen-general`, null, { params: { url } });
  }

  agregarImagenDetallada(id: string, parte: string, url: string) {
    // POST /api/especies/{id}/imagen-detallada?parte=...&url=...
    return this.http.post(`${this.baseUrl}/${id}/imagen-detallada`, null, { params: { parte, url } });
  }
}
