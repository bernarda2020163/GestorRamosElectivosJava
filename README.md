📚 # Gestor de Asignación de Electivos (API REST)

Este repositorio contiene la implementación de una API REST para gestionar la inscripción de estudiantes a electivos, incluyendo un algoritmo de asignación masiva basado en la prioridad de postulación.  

🧭 # Estructura de Endpoints
La API se organiza en torno a cinco controladores principales, cada uno manejando las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para sus respectivas entidades, además de los endpoints de lógica de negocio clave.  

## 1. EstudianteController (/api/estudiantes)

| Método | URL del Endpoint | Descripción | Cuerpo (Body) |
| :---: | :---: | :---: | :---: |
| **POST** | /api/estudiantes | Crea un nuevo estudiante en el sistema. | Estudiante (JSON) |
| **GET** | /api/estudiantes | Lista todos los estudiantes registrados. | Ninguno |
| **GET** | /api/estudiantes/{id} | Obtiene un estudiante específico por su ID. | Ninguno |
| **POST** | /api/estudiantes/postular | Permite al estudiante realizar su postulación con 3 preferencias y prioridades (1, 2, 3). | PostulacionRequestDTO (JSON) |
| **DELETE** | /api/estudiantes/{id} |Elimina un estudiante por su ID. | Ninguno|

rofesorController (Ruta Base: /api/profesores)
Método
Endpoint
Descripción
Cuerpo (Body)
POST
/api/profesores
Crea un nuevo profesor.
Profesor (JSON)
GET
/api/profesores
Lista todos los profesores registrados.
Ninguno
GET
/api/profesores/{id}
Obtiene un profesor específico por su ID.
Ninguno
PUT
/api/profesores/{id}
Actualiza los datos de un profesor existente.
Profesor (JSON)
DELETE
/api/profesores/{id}
Elimina un profesor por su ID.
Ninguno

3. ElectivoController (Ruta Base: /api/electivos)
Método
Endpoint
Descripción
Parámetros / Body
POST
/api/electivos
Crea un nuevo electivo y lo asocia a un profesor.
Query Param: idProfesor / Body: Electivo (JSON)
GET
/api/electivos
Lista todos los electivos.
Ninguno
GET
/api/electivos/{id}
Obtiene un electivo específico por su ID.
Ninguno
PUT
/api/electivos/{id}
Actualiza los datos (nombre, cupos, etc.) de un electivo.
Electivo (JSON)
DELETE
/api/electivos/{id}
Elimina un electivo por su ID.
Ninguno

4. PostulacionController (Ruta Base: /api/postulaciones)
Método
Endpoint
Descripción
Parámetros / Body
POST
/api/postulaciones
[CRUD BÁSICO] Crea una postulación individual para un electivo.
Query Params: estudianteId, electivoId
GET
/api/postulaciones
Lista todas las postulaciones creadas en el sistema.
Ninguno
GET
/api/postulaciones/{id}
Obtiene una postulación específica por su ID.
Ninguno
DELETE
/api/postulaciones/{id}
Elimina una postulación por su ID.
Ninguno

5. AdministradorController (Ruta Base: /api/administradores)
Método
Endpoint
Descripción
Cuerpo (Body)
POST
/api/administradores
Crea un nuevo administrador.
Administrador (JSON)
GET
/api/administradores
Lista todos los administradores.
Ninguno
GET
/api/administradores/{id}
Obtiene un administrador específico por su ID.
Ninguno
POST
/api/administradores/asignacion-masiva
[CRÍTICO] Ejecuta la lógica central de asignación de cupos por prioridad a todas las postulaciones PENDIENTES.
Ninguno
PUT
/api/administradores/{id}
Actualiza los datos de un administrador.
Administrador (JSON)
DELETE
/api/administradores/{id}
Elimina un administrador por su ID.
Ninguno








📈 Diagrama de Flujo del Proceso Crítico
El siguiente diagrama visualiza la interacción entre los endpoints clave (/postular y /asignacion-masiva) y las entidades de Postulación y Electivo.
graph TD
    %% Controllers (Rutas Base)
    subgraph 1. EstudianteController [/api/estudiantes]
        E_POST[POST /estudiantes: Crear Estudiante]
        E_GET[GET /estudiantes / {id}]
        E_DEL[DELETE /estudiantes/{id}]
        E_POSTULAR(POST /estudiantes/postular: Enviar Preferencias)
    end

    subgraph 2. ElectivoController [/api/electivos]
        EL_POST[POST /electivos?idProfesor={id}: Crear Electivo]
        EL_CRUD[CRUD Electivo: /api/electivos/{id}]
    end

    subgraph 3. ProfesorController [/api/profesores]
        P_CRUD[CRUD Profesor: /api/profesores / {id}]
    end

    subgraph 4. PostulacionController [/api/postulaciones]
        PO_CRUD[CRUD Postulacion: /api/postulaciones / {id}]
    end

    subgraph 5. AdministradorController [/api/administradores]
        A_CRUD[CRUD Admin: /api/administradores / {id}]
        A_ASIGNAR(POST /administradores/asignacion-masiva)
    end

    %% Flujo Crítico de Asignación

    % 1. El estudiante postula
    E_POSTULAR -->|Crea 3 Postulaciones| PO_CRUD
    PO_CRUD -->|Estado: PENDIENTE| A_ASIGNAR

    % 2. El administrador ejecuta la asignación masiva
    A_ASIGNAR -->|1. Procesa por Prioridad (1, 2, 3)| PO_CRUD
    A_ASIGNAR -->|2. Valida Cupos| EL_CRUD
    A_ASIGNAR -->|3. Actualiza Estado (ACEPTADA/RECHAZADA)| PO_CRUD
    EL_CRUD -->|Disminuye Cupos| A_ASIGNAR


# SIMULACIÓN COMPLETA DE ENDPOINTS  

Este documento contiene las peticiones RAW (JSON) necesarias para simular el flujo completo de la aplicación, incluyendo el Setup, el CRUD básico, las Operaciones de Negocio (Postulación Masiva y Asignación) y el Manejo de Errores.  

Base URL: http://localhost:8080/api  

## FASE 1: SETUP INICIAL DE DATOS (40 Peticiones)

### A. CREACIÓN DE PROFESORES (IDs: 1, 2, 3)

| # | Método | Endpoint | Descripción | Body (RAW - JSON) |
| 1 | POST | /profesores | Prof. 1 (Área A - Historia) | {"nombre": "Prof. Ana Castro", "email": "ana.c@uni.cl", "password": "pass1", "especialidad": "Historia"} |
| 2 | POST | /profesores | Prof. 2 (Área B - Matemáticas) | {"nombre": "Prof. Luis Pérez", "email": "luis.p@uni.cl", "password": "pass2", "especialidad": "Matemáticas"} |
| 3 | POST | /profesores | Prof. 3 (Área C - Artes) | {"nombre": "Prof. Carla Diaz", "email": "carla.d@uni.cl", "password": "pass3", "especialidad": "Artes Visuales"} |  

### B. CREACIÓN DE 10 ELECTIVOS (Con Cupos Limitados)  

Usamos 10 electivos de las 3 áreas con cupos bajos para forzar la asignación por prioridad. Nota: idProfesor corresponde a los IDs creados arriba.

| # | Método | Endpoint | Electivo (ID / Área / Cupos) | Body (RAW - JSON) |
| 4 | POST | /electivos?idProfesor=1 | ID: 1 / A: Estética / Cupos: 2 | {"nombre": "Estética", "descripcion": "Estudio de la belleza y el arte.", "cupos": 2} |
| 5 | POST | /electivos?idProfesor=1 | ID: 2 / A: Comprensión Histórica / Cupos: 3 | {"nombre": "Comprensión Histórica del Presente", "descripcion": "Análisis de eventos actuales.", "cupos": 3} |
| 6 | POST | /electivos?idProfesor=2 | ID: 3 / B: Límites, Derivadas e Integrales / Cupos: 2 | {"nombre": "Límites, Derivadas e Integrales", "descripcion": "Cálculo avanzado.", "cupos": 2} |
| 7 | POST | /electivos?idProfesor=2 | ID: 4 / B: Física / Cupos: 3 | {"nombre": "Física", "descripcion": "Conceptos de termodinámica y mecánica.", "cupos": 3} |
| 8 | POST | /electivos?idProfesor=3 | ID: 5 / C: Artes Visuales / Cupos: 2 | {"nombre": "Artes Visuales, Audiovisuales y Multimediales", "descripcion": "Técnicas de producción visual.", "cupos": 2} |
| 9 | POST | /electivos?idProfesor=3 | ID: 6 / C: Diseño y Arquitectura / Cupos: 3 | {"nombre": "Diseño y Arquitectura", "descripcion": "Principios básicos de diseño espacial.", "cupos": 3} |
| 10 | POST | /electivos?idProfesor=1 | ID: 7 / A: Taller de Literatura / Cupos: 1 | {"nombre": "Taller de Literatura", "descripcion": "Escritura creativa y análisis de textos.", "cupos": 1} |
| 11 | POST | /electivos?idProfesor=2 | ID: 8 / B: Geometría 3D / Cupos: 1 | {"nombre": "Geometría 3D", "descripcion": "Modelado de figuras espaciales.", "cupos": 1} |
| 12 | POST | /electivos?idProfesor=3 | ID: 9 / C: Expresión Corporal / Cupos: 2 | {"nombre": "Expresión Corporal", "descripcion": "Movimiento y conciencia corporal.", "cupos": 2} |
| 13 | POST | /electivos?idProfesor=1 | ID: 10 / A: Filosofía Política / Cupos: 1 | {"nombre": "Filosofía Política", "descripcion": "Teorías del poder y el Estado.", "cupos": 1} 

### C. CREACIÓN DE 40 ESTUDIANTES (20 de 3° Medio, 20 de 4° Medio)  

Simulamos la carrera como el nivel (3° Medio o 4° Medio) para propósitos de simulación. Los IDs van del 1 al 40. 

| # | Método | Endpoint | Estudiante (ID) | Carrera/Nivel |
| 14-33 | POST | /estudiantes | 1 a 20 | 3° Medio (10 Ingeniería, 10 Medicina) |
| 34-53 | POST | /estudiantes | 21 a 40 | 4° Medio (10 Derecho, 10 Periodismo) |

Ejemplo Estudiante 3° Medio (ID: 1): {"nombre": "Estudiante 3M-01", "email": "e3m01@cole.cl", "password": "pass", "carrera": "3° Medio - Ingeniería"}
Ejemplo Estudiante 4° Medio (ID: 21): {"nombre": "Estudiante 4M-01", "email": "e4m01@cole.cl", "password": "pass", "carrera": "4° Medio - Derecho"}

(Nota: Debes replicar estas peticiones 40 veces, variando el nombre, email y carrera para cada estudiante.)

## FASE 2: CRUD BÁSICO Y ERRORES (10 Peticiones)

| # | Método | Endpoint | Descripción | Body (RAW - JSON) |
| 54 | GET | /profesores/1 | Lectura por ID (OK) | (No Body) |
| 55 | PUT | /electivos/5 | Actualización (Electivo): Cambia cupos | {"nombre": "Artes Visuales, Audiovisuales y Multimediales", "cupos": 30} |
| 56 | GET | /electivos | Lectura (Lista): Verifica el cambio | (No Body) |
| 57 | DELETE | /estudiantes/40 | Eliminación (Estudiante): Estudiante 40 | (No Body) |
| 58 | GET | /estudiantes/40 | Error 404 (Recurso No Encontrado) | (No Body) |
| 59 | GET | /profesores/999 | Error 404 (Recurso No Encontrado) | (No Body) |
| 60 | POST | /postulaciones?estudianteId=1&electivoId=999 | Error 404 (Crear Postulación con Electivo inexistente) | (No Body) 

### FASE 3: POSTULACIÓN Y ASIGNACIÓN (10 Peticiones)

Aquí se simulan 10 postulaciones masivas para los primeros 10 estudiantes. Usaremos el criterio de a lo menos dos áreas distintas para que sean válidas.

| # | Estudiante (ID) | Método | Endpoint | Preferencias (Electivo ID, Prioridad) | Áreas (Válido: A, B, C) |
| 61 | E-1 (3°M) | POST | /estudiantes/postular | A(1), B(3), C(5) | A, B, C (Válido) |
| 62 | E-2 (3°M) | POST | /estudiantes/postular | A(2), B(4), C(6) | A, B, C (Válido) |
| 63 | E-3 (3°M) | POST | /estudiantes/postular | B(3), C(5), A(7) | B, C, A (Válido) |
| 64 | E-4 (3°M) | POST | /estudiantes/postular | C(6), A(2), B(4) | C, A, B (Válido) |
| 65 | E-5 (3°M) | POST | /estudiantes/postular | B(4), A(7), C(9) | B, A, C (Válido) |
| 66 | E-6 (3°M) | POST | /estudiantes/postular | A(10), B(3), C(5) | A, B, C (Válido) |
| 67 | E-7 (3°M) | POST | /estudiantes/postular | B(8), C(9), A(1) | B, C, A (Válido) |
| 68 | E-8 (3°M) | POST | /estudiantes/postular | A(2), C(6), B(4) | A, C, B (Válido) |
| 69 | E-9 (3°M) | POST | /estudiantes/postular | B(3), A(7), C(9) | B, A, C (Válido) |
| 70 | E-10 (3°M) | POST | /estudiantes/postular | C(5), B(4), A(10) | C, B, A (Válido) |

Cuerpo de Postulación (Ejemplo Estudiante 1):
{
    "estudianteId": 1,
    "preferencias": [
        {"electivoId": 1, "prioridad": 1},
        {"electivoId": 3, "prioridad": 2},
        {"electivoId": 5, "prioridad": 3}
    ]
}

### 4. Ejecución de la Asignación y Error de Lógica

| # | Método | Endpoint | Descripción | Body (RAW - JSON) |
| 71 | POST | /administradores/asignacion-masiva | Ejecutar Asignación: Esto acepta/rechaza las postulaciones basándose en la prioridad y los cupos (ver FASE 1, Electivos). | (No Body) |
| 72 | GET | /postulaciones | Verificar Resultados: Muestra el estado final (ACEPTADA/RECHAZADA) de las 30 postulaciones creadas. | (No Body) |
| 73 | POST | /estudiantes/postular | Error 400 (Estado Inválido): Prioridades duplicadas (Invalida la regla 1, 2, 3) | {"estudianteId": 11, "preferencias": [ {"electivoId": 1, "prioridad": 1}, {"electivoId": 2, "prioridad": 1}, {"electivoId": 3, "prioridad": 2} ]} |


