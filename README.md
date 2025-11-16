📚 Gestor de Asignación de Electivos (API REST)Este repositorio contiene la implementación de una API REST para gestionar la inscripción de estudiantes a electivos, incluyendo un algoritmo de asignación masiva basado en la prioridad de postulación.🧭 Estructura de EndpointsLa API se organiza en torno a cinco controladores principales, cada uno manejando las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para sus respectivas entidades, además de los endpoints de lógica de negocio clave.1. EstudianteController (/api/estudiantes)MétodoURL del EndpointDescripciónFlujo ClavePOST/api/estudiantesCrea un nuevo estudiante.CRUDGET/api/estudiantesObtiene la lista de todos los estudiantes.CRUDGET/api/estudiantes/{id}Obtiene un estudiante por su ID.CRUDDELETE/api/estudiantes/{id}Elimina un estudiante por su ID.CRUDPOST/api/estudiantes/postular[CRÍTICO] Permite al estudiante enviar sus 3 preferencias de electivo con su respectiva prioridad (1, 2, 3). Crea 3 registros de Postulacion en estado PENDIENTE.Postulación2. AdministradorController (/api/administradores)MétodoURL del EndpointDescripciónFlujo ClavePOST/api/administradoresCrea un nuevo administrador.CRUDGET/api/administradores/{id}Obtiene un administrador por su ID.CRUDPOST/api/administradores/asignacion-masiva[CRÍTICO] Ejecuta el proceso de asignación masiva de electivos por prioridad.Asignación3. ElectivoController (/api/electivos)MétodoURL del EndpointDescripciónFlujo ClavePOST/api/electivos?idProfesor={id}Crea un nuevo electivo y lo asigna a un profesor existente.CRUDGET/api/electivosObtiene la lista de todos los electivos.CRUDGET/api/electivos/{id}Obtiene un electivo por su ID.CRUDPUT/api/electivos/{id}Actualiza la información de un electivo (ej. cupos, descripción).CRUDDELETE/api/electivos/{id}Elimina un electivo por su ID.CRUD4. ProfesorController (/api/profesores)MétodoURL del EndpointDescripciónFlujo ClavePOST/api/profesoresCrea un nuevo profesor.CRUDGET/api/profesoresObtiene la lista de todos los profesores.CRUDGET/api/profesores/{id}Obtiene un profesor por su ID.CRUDPUT/api/profesores/{id}Actualiza la información de un profesor.CRUDDELETE/api/profesores/{id}Elimina un profesor por su ID.CRUD5. PostulacionController (/api/postulaciones)MétodoURL del EndpointDescripciónFlujo ClavePOST/api/postulaciones?estudianteId={idEst}&electivoId={idElect}Crea una postulación individual. (Usado principalmente para pruebas; el flujo normal usa /api/estudiantes/postular).CRUDGET/api/postulacionesObtiene la lista de todas las postulaciones.CRUDGET/api/postulaciones/{id}Obtiene una postulación por su ID.CRUDDELETE/api/postulaciones/{id}Elimina una postulación por su ID.CRUD📈 Diagrama de Flujo del Proceso CríticoEl siguiente diagrama visualiza la interacción entre los endpoints clave (/postular y /asignacion-masiva) y las entidades de Postulación y Electivo.graph TD
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
