# 🦋 Identificador y Catalogador de Especies (Lepidoptera)

## 🌟 Descripción del Proyecto

Este proyecto es un **aplicativo web de identificación y catalogación de especies de mariposas (Lepidoptera)**, diseñado para facilitar la **investigación y el monitoreo biológico** a nivel nacional.

La plataforma permite a los usuarios **subir nuevas especies** con su información taxonómica completa, registrar sus **ubicaciones** geográficas exactas y añadir **observaciones** detalladas.

### Funcionalidades Clave:

* **Identificación Visual:** Búsqueda y visualización de especies catalogadas.
* **Registro de Datos:** Creación de nuevos registros de especies con campos detallados.
* **Monitoreo Nacional:** Centralización de datos para generar un **reporte continuo** de la distribución de mariposas en el país.
* **Colaboración:** Los usuarios pueden ver la información de las especies y agregar **comentarios** y observaciones.
* **Administración de Datos:** Gestión completa (CRUD) de especies y usuarios por parte del rol Administrador.

---

## 🛠️ Tecnologías Utilizadas

| Componente | Tecnología | Versión Recomendada |
| :--- | :--- | :--- |
| **Backend** | Spring Boot (Java) | 3.x |
| **Frontend** | Angular | 17+ |
| **Base de Datos** | MongoDB | Latest |
| **Estilos** | Bootstrap 5 | |

---

## 🚀 Instalación y Ejecución

Para ejecutar el proyecto en tu máquina local, sigue estos pasos:

### 1. Prerrequisitos de Desarrollo

Asegúrate de tener instalado el siguiente software:

* **Java JDK** (versión 17 o superior)
* **Node.js** (versión 18 o superior)
* **Angular CLI** (Globalmente):
    ```bash
    npm install -g @angular/cli
    ```
* Un servidor de **MongoDB** (local o en la nube).

### 2. Configuración del Proyecto

1.  **Clonar el repositorio:**
    ```bash
    git clone [URL_DEL_REPOSITORIO]
    cd [nombre-del-proyecto]
    ```

2.  **Configurar la Base de Datos (Backend):**
    * Navega a la carpeta del *backend* (`cd backend`).
    * Abre el archivo de configuración de Spring (usualmente `application.properties` o `application.yml`).
    * Asegúrate de que la conexión a tu instancia de MongoDB esté configurada correctamente.

### 3. Ejecutar el Backend (Spring Boot)

Inicia el servidor de la API para que el *frontend* pueda acceder a los datos.

```bash
# Navega a la carpeta raíz del backend
cd backend
# Compila y ejecuta el proyecto
./mvnw spring-boot:run

# Navega a la carpeta raíz del frontend
cd frontend
# Instala las dependencias
npm install
# Ejecuta la aplicación en modo desarrollo
ng serve -o

#La aplicación se abrirá en el navegador
#Otra manera de iniciarlo es con
npm start
