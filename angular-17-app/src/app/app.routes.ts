import { Routes } from '@angular/router';
import { Login } from './login/login';
import { SingUp } from './sing-up/sing-up';


export const routes: Routes = [
    {
        path: '', component: Login
    },
    {path: 'Login', component: Login},
    {path: 'sing-up', component: SingUp}
];
