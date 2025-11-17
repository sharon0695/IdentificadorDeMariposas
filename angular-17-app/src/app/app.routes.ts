import { Routes } from '@angular/router';
import { Login } from './login/login';
import { SingUp } from './sing-up/sing-up';
import { Mariposas } from './mariposas/mariposas';


export const routes: Routes = [
    {
        path: '', component: Login
    },
    {path: 'Login', component: Login},
    {path: 'sing-up', component: SingUp},
    {path: 'manejo-mariposas', component: Mariposas}
];
