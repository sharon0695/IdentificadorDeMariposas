import { Component, Inject, PLATFORM_ID, AfterViewInit, OnDestroy, OnInit, inject } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Especie, EspecieService } from '../services/especie.service';
import { ActivatedRoute, ParamMap, Router, RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { GeoLocalizacion, UbicacionService } from '../services/ubicacion.service';

@Component({
  selector: 'app-mapa-especies',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './mapa-especies.html',
  styleUrls: ['./mapa-especies.css'],
})
export class MapaEspecies implements OnInit, AfterViewInit, OnDestroy {
  private route = inject(ActivatedRoute);
  private L!: typeof import('leaflet');
  private map?: import('leaflet').Map;
  private speciesMarkersLayer?: import('leaflet').LayerGroup;
  private routeSub?: Subscription;
  isBrowser: boolean;

  // Variables para guardar la información de la ubicación
  departamento?: string;
  geolocalizacion?: GeoLocalizacion;

  // Icono por defecto para los marcadores
  private defaultIcon: import('leaflet').Icon = {} as any;


  constructor(
    private especieService: EspecieService,
    private ubicacionService: UbicacionService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  ngOnInit(): void {
    this.routeSub = this.route.queryParamMap.subscribe((params: ParamMap) => {
      const id = params.get('especieId');
      if (id) {
        this.ubicacionService.getDepartamentoYGeolocalizacion(id).subscribe(data => {
          this.departamento = data.departamento;
          this.geolocalizacion = data.geolocalizacion;
          // Una vez que tenemos los datos, actualizamos el mapa si ya está inicializado.
          this.updateMapWithLocation();
        });
      }
    });
  }

  ngAfterViewInit(): void {
    // Inicializamos el mapa base tan pronto como la vista está lista.
    this.initMap();
  }

  private initMap(): void {
    if (this.isBrowser) {
      import('leaflet').then(L => {
        this.L = L;

        this.defaultIcon = new L.Icon({
          iconUrl: 'ico/map-pin-fill.svg',
          iconSize: [25, 41],
          iconAnchor: [12, 41],
          popupAnchor: [1, -34]
        });

        // Creamos el mapa con una vista por defecto (Colombia)
        this.map = L.map('map').setView([4.5709, -74.2973], 6);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(this.map);

        // Si los datos de la ubicación llegaron antes de que el mapa se inicializara, lo actualizamos ahora.
        this.updateMapWithLocation();
      });
    }
  }

  private updateMapWithLocation(): void {
    // Este método solo se ejecuta si el mapa está listo Y tenemos una geolocalización.
    if (this.map && this.geolocalizacion?.latitud && this.geolocalizacion?.longitud) {
      const lat = this.geolocalizacion.latitud;
      const lng = this.geolocalizacion.longitud;

      console.log('Actualizando mapa con la siguiente información:', {
        departamento: this.departamento,
        geolocalizacion: this.geolocalizacion
      });

      this.map.setView([lat, lng], 13);

      this.L.marker([lat, lng], { icon: this.defaultIcon }).addTo(this.map)
        .bindPopup(`<b>${this.departamento}</b>`).openPopup();
    }
  }

  ngOnDestroy(): void {    
    this.routeSub?.unsubscribe();
    if (this.map) {
      this.map.remove();
    }
  }
}
