import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule } from '@angular/common';
import { isPlatformBrowser } from '@angular/common';
import { Especie, EspecieService } from '../services/especie.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-mapa-especies',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mapa-especies.html',
  styleUrls: ['./mapa-especies.css']
})
export class MapaEspecies {
  private map!: import('leaflet').Map;
  private L!: typeof import('leaflet');


  especies: Especie[] = [];
  markersLayer: any;
  routeSub!: Subscription;

  isBrowser = false;

  sampleImageUrl = '/mnt/data/58ace760-6b0f-4367-8427-b7936ffd7ad6.png';

  constructor(
    private service: EspecieService,
    private route: ActivatedRoute,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  ngOnInit(): void {
    this.routeSub = this.route.queryParams.subscribe(params => {
      const especieId = params['especieId'];
      this.loadAndRender(especieId);
    });
  }

  async ngAfterViewInit() {
    if (typeof window !== 'undefined') {
        this.L = await import('leaflet');
        this.initMap();
      }
  }

  ngOnDestroy(): void {
    this.routeSub?.unsubscribe();
    if (this.map) this.map.remove();
  }

  private initMap() {
    if (!this.isBrowser) return;
    if (this.map) return;

    this.map = this.L.map('map', {
      center: [4.5709, -74.2973],
      zoom: 6
    });

    this.L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);

    this.markersLayer = this.L.layerGroup().addTo(this.map);

    this.map.on('click', (e: any) => {
      const lat = e.latlng.lat;
      const lng = e.latlng.lng;

      const id = prompt(`ID de la especie:\nLat: ${lat}\nLng: ${lng}`);
      if (!id) return;

      this.service.actualizarUbicacion(id, `${lat},${lng}`).subscribe({
        next: () => {
          alert('¡Ubicación guardada!');
          this.loadAndRender();
        },
        error: () => alert('Error al guardar ubicación')
      });
    });
  }

  private loadAndRender(focusEspecieId?: string) {
    this.service.getEspecies().subscribe({
      next: especies => {
        this.especies = especies || [];
        if (this.isBrowser) this.renderMarkers();

        if (focusEspecieId) {
          const esp = this.especies.find(s => s.id === focusEspecieId);
          if (!esp?.ubicacionRecoleccion) return;

          const [lat, lng] = esp.ubicacionRecoleccion.split(',').map(Number);
          if (!isNaN(lat) && !isNaN(lng)) {
            this.map.setView([lat, lng], 12);
          }
        }
      }
    });
  }

  private renderMarkers() {
    this.markersLayer.clearLayers();

    this.especies.forEach(e => {
      if (!e.ubicacionRecoleccion) return;

      const [lat, lng] = e.ubicacionRecoleccion.split(',').map(Number);
      if (isNaN(lat) || isNaN(lng)) return;

      const marker = this.L.marker([lat, lng]);

      const img = (e.imagenes?.length) ? e.imagenes[0] : this.sampleImageUrl;

      const popupHtml = `
        <div style="min-width:180px">
          <strong>${this.escapeHtml(e.nombreCientifico || '—')}</strong><br/>
          <em>${this.escapeHtml(e.nombreComun || '')}</em><br/>
          <img src="${img}" style="width:160px;height:90px;object-fit:cover;margin-top:6px;border-radius:6px;">
          <div style="margin-top:6px;">
            <button id="btn-det-${e.id}" data-id="${e.id}" style="padding:6px 8px;cursor:pointer">
              Ver detalle
            </button>
          </div>
        </div>
      `;

      marker.bindPopup(popupHtml);
      marker.addTo(this.markersLayer);

      marker.on('popupopen', () => {
        setTimeout(() => {
          const el = document.getElementById(`btn-det-${e.id}`);
          if (el) {
            el.addEventListener('click', () => {
              this.router.navigate(['/especies', e.id]);
              marker.closePopup();
            });
          }
        }, 50);
      });
    });
  }

  private escapeHtml(text: string) {
    return (text || '').replace(/[&<>"]/g, c =>
      ({ '&':'&amp;', '<':'&lt;', '>':'&gt;', '"':'&quot;' }[c] as string)
    );
  }
}
