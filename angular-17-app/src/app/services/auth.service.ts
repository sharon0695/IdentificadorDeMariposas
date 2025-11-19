import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

export interface LoginRequest {
  correo: string;
  contrasena: string;
}

export interface LoginResponse {
  accessToken: string;
  tokenType: string;
  expiresInMillis: number;
  id: string;
  nombre: string;
  correo: string;
  rol: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8180/api/usuarios';
  private storageKey = 'auth_token';
  private expiryKey = 'auth_expires_at';
  private expiryTimer: any;
  private isBrowser: boolean;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);

    if (this.isBrowser) {
      const exp = this.getExpiry();
      if (exp) this.scheduleExpiryLogout(exp);
    }
  }

  // =============================
  // Helpers seguros para localStorage
  // =============================
  private lsGet(key: string): string | null {
    return this.isBrowser ? localStorage.getItem(key) : null;
  }

  private lsSet(key: string, value: string): void {
    if (this.isBrowser) localStorage.setItem(key, value);
  }

  private lsRemove(key: string): void {
    if (this.isBrowser) localStorage.removeItem(key);
  }

  // =============================
  // Métodos de usuario
  // =============================

  getUserRaw(): any | null {
    if (!this.isBrowser) return null;
    try {
      const raw = this.lsGet('auth_user');
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  }

  getUserRole(): string | null {
    const u = this.getUserRaw();
    return u?.rol?.toString().trim() ?? null;
  }

  private getExpiry(): number | null {
    const raw = this.lsGet(this.expiryKey);
    if (!raw) return null;
    const n = Number(raw);
    return Number.isFinite(n) ? n : null;
  }

  login(body: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, body).pipe(
      tap((res) => {
        if (!this.isBrowser) return;

        const token = `${res.accessToken}`;
        this.lsSet(this.storageKey, token);

        const expiresAt = Date.now() + (res.expiresInMillis || 0);
        this.lsSet(this.expiryKey, String(expiresAt));
        this.scheduleExpiryLogout(expiresAt);

        this.lsSet(
          'auth_user',
          JSON.stringify({
            id: res.id,
            nombre: res.nombre,
            correoInstitucional: res.correo,
            rol: res.rol
          })
        );
      })
    );
  }

  logout(showMessage: boolean = false): void {
    if (!this.isBrowser) return;

    this.lsRemove(this.storageKey);
    this.lsRemove(this.expiryKey);

    if (this.expiryTimer) {
      clearTimeout(this.expiryTimer);
      this.expiryTimer = null;
    }
  }

  logoutRemote() {
    return this.http.post<void>(`${this.baseUrl}/logout`, {});
  }

  getToken(): string | null {
    return this.lsGet(this.storageKey);
  }

  getUserId(): string | null {
    if (!this.isBrowser) return null;

    try {
      const raw = this.lsGet('auth_user');
      if (!raw) return null;
      const obj = JSON.parse(raw);
      return obj?.id ?? null;
    } catch {
      return null;
    }
  }

  getUser() {
    const userData = this.lsGet('auth_user');
    return userData ? JSON.parse(userData) : null;
  }

  isAuthenticated(): boolean {
    if (!this.isBrowser) return false;

    const token = this.getToken();
    if (!token) return false;

    const exp = this.getExpiry();
    if (exp && exp <= Date.now()) {
      this.logout(true);
      return false;
    }
    return true;
  }

  private scheduleExpiryLogout(expiresAt: number) {
    if (!this.isBrowser) return;

    if (this.expiryTimer) clearTimeout(this.expiryTimer);

    const delay = Math.max(0, expiresAt - Date.now());

    if (delay === 0) {
      this.logout();
      alert('Tu sesión ha expirado. Por favor, inicia sesión nuevamente.');
      this.router.navigateByUrl('/login');
      return;
    }

    this.expiryTimer = setTimeout(() => {
      this.logout();
      alert('Tu sesión ha expirado. Por favor, inicia sesión nuevamente.');
      this.router.navigateByUrl('/login');
    }, delay);
  }
}
