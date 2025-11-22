import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackupService {

  private apiUrl = 'http://localhost:8180/api/backup';

  constructor(private http: HttpClient) {}

  generarBackup(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/backup`, {
      responseType: 'blob'
    });
  }

  restaurarBackup(archivo: File): Observable<any> {
    const formData = new FormData();
    formData.append("file", archivo);

    return this.http.post(`${this.apiUrl}/restore`, formData);
  }
}
