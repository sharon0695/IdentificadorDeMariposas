import { Routes } from '@angular/router';
import { Login } from './login/login';
import { SingUp } from './sing-up/sing-up';
import { Mariposas } from './mariposas/mariposas';
import { CrearEspecie } from './crear-especie/crear-especie';
import { Observaciones } from './observaciones/observaciones';
import { MapaEspecies } from './mapa-especies/mapa-especies';




export const routes: Routes = [
    {path: '', component: Login},
    {path: 'login', component: Login},
    {path: 'sing-up', component: SingUp},
    {path: 'manejo-mariposas', component: Mariposas},    
    { path: 'especies/crear', component: CrearEspecie },
    { path: 'observaciones', component: Observaciones },
    { path: 'mapa', component: MapaEspecies },
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: Login },
    { path: 'sing-up', component: SingUp },
];
