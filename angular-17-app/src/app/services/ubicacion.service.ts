import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

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

  /** Transformar MongoDB → Angular */
  private mapUbicacion(u: any): Ubicacion {
    return {
      id: u._id || u.id,
      localidad: u.localidad,
      municipio: u.municipio,
      departamento: u.departamento,
      pais: u.pais,
      geolocalizacion: u.geolocalizacion,
      ecosistema: u.ecosistema
    };
  }

  /** Obtener todas las ubicaciones */
  listar(): Observable<Ubicacion[]> {
    return this.http.get<Ubicacion[]>(this.apiUrl);}

  getUbicaciones(): Observable<Ubicacion[]> {
    return this.http.get<any[]>(this.apiUrl)
      .pipe(map(list => list.map(u => this.mapUbicacion(u))));
  }

  /** Obtener una ubicación por ID */
  getUbicacion(id: string): Observable<Ubicacion> {
    return this.http.get<any>(`${this.apiUrl}/${id}`)
      .pipe(map(u => this.mapUbicacion(u)));
  }

  /** Obtener departamento y geolocalización por ID de ubicación */
  getDepartamentoYGeolocalizacion(id: string): Observable<{ departamento?: string; geolocalizacion?: GeoLocalizacion }> {
    return this.getUbicacion(id).pipe(
      map(ubicacion => ({
        departamento: ubicacion.departamento,
        geolocalizacion: ubicacion.geolocalizacion
      }))
    );
  }

  crear(ubicacion: any) {
    return this.http.post<any>(this.apiUrl, ubicacion);
  }
}