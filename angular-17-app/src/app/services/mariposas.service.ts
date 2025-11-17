import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MariposasService {

  private apiUrl = 'http://localhost:8080/api/especies';

  constructor(private http: HttpClient) { }

  getEspecies(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getEspecie(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
}
