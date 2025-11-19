import { HttpErrorResponse, HttpEvent, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';
import { catchError, tap } from 'rxjs/operators';
import { throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const token = auth.getToken();

  console.log('%c[INTERCEPTOR] Token obtenido:', 'color: #0a7;', token);

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  console.log('%c[INTERCEPTOR] Headers enviados:', 'color: #07a;', req.headers);

  return next(req).pipe(
    tap((event: HttpEvent<any>) => {
      console.log('%c[INTERCEPTOR] Respuesta del servidor:', 'color: #a70;', event);
    }),
    catchError((err: HttpErrorResponse) => {
      console.error('%c[INTERCEPTOR ERROR] Error detectado:', 'color: red; font-weight: bold;', err);

      if (err.status === 401 || err.status === 403) {
        console.warn('%c[INTERCEPTOR] Token inválido → logout automático', 'color: orange;');
        auth.logout();
      }

      return throwError(() => err);
    })
  );
};
