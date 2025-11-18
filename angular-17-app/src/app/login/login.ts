import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { LoginRequest } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  correo: string = '';
  contrasena: string = '';
  mensaje: string = '';
  esError = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
    const req: LoginRequest = {
      correo: this.correo,
      contrasena: this.contrasena
    };

    this.authService.login(req).subscribe({
      next: () => {
        this.mensaje = 'Iniciando';
        this.router.navigate(['/manejo-mariposas']); 
      },
      error: (err) => {
        this.esError = true;
        this.mensaje = err.error?.message || 'Credenciales inválidas';
      }
    });
  }
}