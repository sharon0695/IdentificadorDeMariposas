import { Component, Inject, PLATFORM_ID, AfterViewInit, OnDestroy } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Especie, EspecieService } from '../services/especie.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-mapa-especies',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mapa-especies.html',
  styleUrls: ['./mapa-especies.css'],
})
export class MapaEspecies implements AfterViewInit, OnDestroy {
  private L!: typeof import('leaflet');
  private map?: import('leaflet').Map;
  private speciesMarkersLayer?: import('leaflet').LayerGroup;
  private routeSub?: Subscription;
  isBrowser: boolean;

  // Icono por defecto para los marcadores
  private defaultIcon: import('leaflet').Icon = {} as any;

  private departamentosCoordenadas = new Map<string, { lat: number; lng: number }>([
    ['amazonas', { lat: -1.257, lng: -71.953 }],
    ['antioquia', { lat: 6.244, lng: -75.581 }],
    ['arauca', { lat: 7.084, lng: -70.761 }],
    ['atlantico', { lat: 10.963, lng: -74.796 }],
    ['bolivar', { lat: 8.933, lng: -74.533 }],
    ['boyaca', { lat: 5.535, lng: -73.367 }],
    ['caldas', { lat: 5.067, lng: -75.517 }],
    ['caqueta', { lat: 1.613, lng: -75.605 }],
    ['casanare', { lat: 5.333, lng: -72.417 }],
    ['cauca', { lat: 2.441, lng: -76.614 }],
    ['cesar', { lat: 10.483, lng: -73.25 }],
    ['choco', { lat: 5.694, lng: -76.658 }],
    ['cordoba', { lat: 8.76, lng: -75.88 }],
    ['cundinamarca', { lat: 4.609, lng: -74.081 }],
    ['bogota dc', { lat: 4.711, lng: -74.072 }],
    ['guainia', { lat: 1.983, lng: -67.917 }],
    ['guaviare', { lat: 2.572, lng: -72.645 }],
    ['huila', { lat: 2.927, lng: -75.281 }],
    ['la guajira', { lat: 11.544, lng: -72.907 }],
    ['magdalena', { lat: 10.963, lng: -74.796 }],
    ['meta', { lat: 4.142, lng: -73.626 }],
    ['narino', { lat: 1.213, lng: -77.281 }],
    ['norte de santander', { lat: 7.893, lng: -72.507 }],
    ['putumayo', { lat: 1.147, lng: -76.645 }],
    ['quindio', { lat: 4.533, lng: -75.683 }],
    ['risaralda', { lat: 4.813, lng: -75.696 }],
    ['san andres y providencia', { lat: 12.584, lng: -81.700 }],
    ['santander', { lat: 7.125, lng: -73.119 }],
    ['sucre', { lat: 9.304, lng: -75.397 }],
    ['tolima', { lat: 4.438, lng: -75.232 }],
    ['valle del cauca', { lat: 3.451, lng: -76.532 }],
    ['vaupes', { lat: 1.133, lng: -70.217 }],
    ['vichada', { lat: 6.183, lng: -67.467 }],
  ]);

  constructor(
    private especieService: EspecieService,
    private route: ActivatedRoute,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  async ngAfterViewInit(): Promise<void> {
    if (this.isBrowser) {
      // Carga dinámica de Leaflet solo en el navegador
      this.L = await import('leaflet');

      // Inicializa el ícono después de cargar Leaflet
      this.defaultIcon = this.L.icon({
        iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41],
      });

      this.routeSub = this.route.queryParams.subscribe(params => {
        // Inicializar el mapa si aún no se ha hecho
        if (!this.map) this.initMap(params);

        const departamentoParam = params['departamento'];
        const especieIdParam = params['especieId'];

        if (departamentoParam) {
          // Si se pasa un nombre de departamento explícitamente, centrar en él y añadir un marcador
          this.centrarEnDepartamento(departamentoParam);
          // Luego cargar todos los marcadores de especies
          this.loadAndRenderSpecies();
        } else if (especieIdParam) {
          // Si solo se pasa especieId, cargar especies e intentar centrar en el departamento de esa especie
          this.especieService.getEspecies().subscribe({
            next: especies => {
              this.renderSpeciesMarkers(especies || []);
              this.focusOnEspecie(especies, especieIdParam);
            },
            error: err => console.error('Error al cargar especies:', err),
          });
        } else {
          // Si no hay parámetros específicos, simplemente cargar todos los marcadores de especies
          this.loadAndRenderSpecies();
        }
      });
    }
  }

  ngOnDestroy(): void {
    this.routeSub?.unsubscribe();
    this.map?.remove();
  }

  private initMap(params: any): void {
    const lat = params['lat'] ? +params['lat'] : 4.5709; // Centro de Colombia por defecto
    const lng = params['lng'] ? +params['lng'] : -74.2973;
    const zoom = params['zoom'] ? +params['zoom'] : 6;

    this.map = this.L.map('map').setView([lat, lng], zoom);

    this.L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; OpenStreetMap contributors',
    }).addTo(this.map);

    this.speciesMarkersLayer = this.L.layerGroup().addTo(this.map);
  }

  private centrarEnDepartamento(departamento: string): void {
    if (!this.map) return;

    // Normaliza el nombre del departamento para la búsqueda (minúsculas, sin tildes)
    const normalizedDepartamento = departamento.trim().toLowerCase()
      .normalize("NFD").replace(/[\u0300-\u036f]/g, "");

    const coords = this.departamentosCoordenadas.get(normalizedDepartamento);

    if (coords) {
      this.map.setView([coords.lat, coords.lng], 8); // Zoom para ver un departamento
      this.L.marker([coords.lat, coords.lng], { icon: this.defaultIcon })
        .addTo(this.map)
        .bindPopup(`<b>${departamento}</b>`).openPopup();
    } else {
      console.warn(`No se encontraron coordenadas para el departamento: "${departamento}". Mostrando vista general.`);
      // Si no se encuentra, centrar en Colombia como fallback
      this.map.setView([4.5709, -74.2973], 6);
    }
  }

  private loadAndRenderSpecies(): void {
    this.especieService.getEspecies().subscribe({
      next: especies => {
        this.renderSpeciesMarkers(especies || []);
      },
      error: err => console.error('Error al cargar especies:', err),
    });
  }

  private renderSpeciesMarkers(especies: Especie[]): void {
    if (!this.speciesMarkersLayer) return;

    this.speciesMarkersLayer.clearLayers();

    especies.forEach(e => {
      if (!e.ubicacionRecoleccion) return;

      // Normaliza el nombre del departamento para la búsqueda
      const normalizedDepartamento = e.ubicacionRecoleccion.trim().toLowerCase()
        .normalize("NFD").replace(/[\u0300-\u036f]/g, "");

      const coords = this.departamentosCoordenadas.get(normalizedDepartamento);

      // Si no se encuentran coordenadas para el departamento, no se muestra el marcador.
      if (!coords) return;

      const marker = this.L.marker([coords.lat, coords.lng], { icon: this.defaultIcon });
      const img = e.imagenes?.length ? e.imagenes[0] : 'assets/default-mariposa.png';

      const popupHtml = `
        <div style="min-width:180px">
          <strong>${this.escapeHtml(e.nombreCientifico || '—')}</strong><br/>
          <em>${this.escapeHtml(e.nombreComun || '')}</em><br/>
          <img src="${img}" style="width:160px;height:90px;object-fit:cover;margin-top:6px;border-radius:6px;">
          <div style="margin-top:8px; text-align: center;">
            <button id="btn-det-${e.id}" class="popup-button" style="padding:6px 12px;cursor:pointer; border: 1px solid #ccc; border-radius: 4px;">
              Ver detalle
            </button>
          </div>
        </div>
      `;

      marker.bindPopup(popupHtml).on('popupopen', () => {
        const btn = document.getElementById(`btn-det-${e.id}`);
        btn?.addEventListener('click', () => {
          this.router.navigate(['/especies', e.id]);
        });
      });

      marker.addTo(this.speciesMarkersLayer!);
    });
  }

  private focusOnEspecie(especies: Especie[], especieId: string): void {
    const especie = especies.find(s => s.id === especieId);
    if (!especie?.ubicacionRecoleccion || !this.map) return;

    const normalizedDepartamento = especie.ubicacionRecoleccion.trim().toLowerCase()
      .normalize("NFD").replace(/[\u0300-\u036f]/g, "");

    const coords = this.departamentosCoordenadas.get(normalizedDepartamento);

    if (coords) {
      this.map.setView([coords.lat, coords.lng], 13); // Zoom más cercano para la especie
    } else {
      console.warn(`No se encontraron coordenadas para el departamento de la especie: "${especie.ubicacionRecoleccion}". No se pudo centrar el mapa en la especie.`);
    }
  }

  private escapeHtml(text: string): string {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
  }
}
