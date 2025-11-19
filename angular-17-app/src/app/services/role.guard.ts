import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router
} from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  canActivate(): boolean {
    const rol = this.auth.getUserRole();

    if (rol === 'ADMIN') {
      return true;
    }

    // Si no es admin → redirigir a pantalla de usuario normal
    this.router.navigate(['/acceso-denegado']);
    return false;
  }
}
