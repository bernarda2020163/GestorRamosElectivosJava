📚 Gestor de Asignación de Electivos (API REST)

Este repositorio contiene la implementación de una API REST para gestionar la inscripción de estudiantes a electivos, incluyendo un algoritmo de asignación masiva basado en la prioridad de postulación.  

🧭 Estructura de Endpoints
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


