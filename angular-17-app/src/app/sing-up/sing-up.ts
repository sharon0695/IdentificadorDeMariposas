import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, RegistroRequest } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sing-up',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sing-up.html',
  styleUrls: ['./sing-up.css']
})
export class SingUp {
  nombre: string = '';
  correo: string = '';
  contrasena: string = '';

  mensaje: string = '';
  esError = false;

  constructor(private authService: AuthService, private router: Router) {}

  registrar() {
    const req: RegistroRequest = {
      nombre: this.nombre,
      correo: this.correo,
      contrasena: this.contrasena
    };

    this.authService.registrar(req).subscribe({
      next: () => {
        this.esError = false;
        this.mensaje = 'Cuenta creada exitosamente';
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: (err: any) => {
        console.log("Error del backend:", err);
        this.esError = true;
        this.mensaje = 
        err.error?.mesage || 
        err.error?.error
        err.message || 
        'Error al registrar';
      }
    });
  }
}
