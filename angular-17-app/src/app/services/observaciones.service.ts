import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Observacion {
  id?: string;
  especieId: string;
  usuarioId: string;
  comentario: string;
  fecha?: Date;
}

@Injectable({
  providedIn: 'root'
})
export class ObservacionesService {

  private apiUrl = 'http://localhost:8180/api/observaciones';

  constructor(private http: HttpClient) {}

  /** Crear */
  crearObservacion(data: Observacion): Observable<Observacion> {
    return this.http.post<Observacion>(this.apiUrl, data);
  }

  /** Listar */
  listarObservaciones(): Observable<Observacion[]> {
    return this.http.get<Observacion[]>(this.apiUrl);
  }

  /** Listar por especie */
  listarPorEspecie(especieId: string): Observable<Observacion[]> {
    return this.http.get<Observacion[]>(`${this.apiUrl}/especie/${especieId}`);
  }

  /** Obtener por ID */
  obtenerPorId(id: string): Observable<Observacion> {
    return this.http.get<Observacion>(`${this.apiUrl}/${id}`);
  }

  /** Eliminar */
  eliminar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
