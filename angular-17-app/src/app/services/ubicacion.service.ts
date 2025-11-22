import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Ubicacion {
  id?: string; 
  localidad?: string;
  municipio?: string;
  departamento?: string;
  pais?: string;
  geolocalizacion?: GeoLocalizacion;
  ecosistema?: string;
}

export interface GeoLocalizacion {
  latitud?: number;
  longitud?: number;
}

@Injectable({
  providedIn: 'root'
})
export class UbicacionService {

  private apiUrl = 'http://localhost:8180/api/ubicaciones';

  constructor(private http: HttpClient) {}

  /** Obtener todas las ubicaciones */
  listar(): Observable<Ubicacion[]> {
    return this.http.get<Ubicacion[]>(this.apiUrl);
  }

  /** Obtener una ubicación por ID */
  getUbicacion(id: string): Observable<Ubicacion> {
    return this.http.get<Ubicacion>(`${this.apiUrl}/${id}`);
  }

  crear(ubicacion: any) {
    return this.http.post<any>(this.apiUrl, ubicacion);
  }
}