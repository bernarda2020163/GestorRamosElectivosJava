 Gestor de Asignación de Electivos (API REST)
Este repositorio contiene la implementación de una API REST para gestionar la inscripción de estudiantes a electivos, incluyendo un algoritmo de asignación masiva basado en la prioridad de postulación.
🧭 Estructura de Endpoints
La API se organiza en torno a cinco controladores principales, cada uno manejando las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para sus respectivas entidades, además de los endpoints de lógica de negocio clave.
1. EstudianteController (/api/estudiantes)


Método
URL del Endpoint
Descripción
Flujo Clave
POST
/api/estudiantes
Crea un nuevo estudiante.
CRUD
GET
/api/estudiantes
Obtiene la lista de todos los estudiantes.
CRUD
GET
/api/estudiantes/{id}
Obtiene un estudiante por su ID.
CRUD
DELETE
/api/estudiantes/{id}
Elimina un estudiante por su ID.
CRUD
POST
/api/estudiantes/postular
[CRÍTICO] Permite al estudiante enviar sus 3 preferencias de electivo con su respectiva prioridad (1, 2, 3). Crea 3 registros de Postulacion en estado PENDIENTE.
Postulación

2. AdministradorController (/api/administradores)
Método
URL del Endpoint
Descripción
Flujo Clave
POST
/api/administradores
Crea un nuevo administrador.
CRUD
GET
/api/administradores/{id}
Obtiene un administrador por su ID.
CRUD
POST
/api/administradores/asignacion-masiva
[CRÍTICO] Ejecuta el proceso de asignación masiva de electivos por prioridad.
Asignación

3. ElectivoController (/api/electivos)
Método
URL del Endpoint
Descripción
Flujo Clave
POST
/api/electivos?idProfesor={id}
Crea un nuevo electivo y lo asigna a un profesor existente.
CRUD
GET
/api/electivos
Obtiene la lista de todos los electivos.
CRUD
GET
/api/electivos/{id}
Obtiene un electivo por su ID.
CRUD
PUT
/api/electivos/{id}
Actualiza la información de un electivo (ej. cupos, descripción).
CRUD
DELETE
/api/electivos/{id}
Elimina un electivo por su ID.
CRUD

4. ProfesorController (/api/profesores)
Método
URL del Endpoint
Descripción
Flujo Clave
POST
/api/profesores
Crea un nuevo profesor.
CRUD
GET
/api/profesores
Obtiene la lista de todos los profesores.
CRUD
GET
/api/profesores/{id}
Obtiene un profesor por su ID.
CRUD
PUT
/api/profesores/{id}
Actualiza la información de un profesor.
CRUD
DELETE
/api/profesores/{id}
Elimina un profesor por su ID.
CRUD

5. PostulacionController (/api/postulaciones)
Método
URL del Endpoint
Descripción
Flujo Clave
POST
/api/postulaciones?estudianteId={idEst}&electivoId={idElect}
Crea una postulación individual. (Usado principalmente para pruebas; el flujo normal usa /api/estudiantes/postular).
CRUD
GET
/api/postulaciones
Obtiene la lista de todas las postulaciones.
CRUD
GET
/api/postulaciones/{id}
Obtiene una postulación por su ID.
CRUD
DELETE
/api/postulaciones/{id}
Elimina una postulación por su ID.
CRUD

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


