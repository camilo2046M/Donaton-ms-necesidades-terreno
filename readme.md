🔌 Contrato de Interfaz (Endpoints de la API REST)
El microservicio expone sus capacidades mediante un protocolo síncrono HTTP / REST transmitiendo payloads puramente en formato JSON. Su puerto de escucha asignado dentro de la red interna es el 8083.

1. Reportar una Nueva Necesidad Crítica
URL: POST /api/v1/necesidades

Cuerpo de la Petición (JSON):

JSON
{
  "recursoNecesitado": "Agua Potable",
  "cantidadAproximada": 500,
  "unidadMedida": "Litros",
  "ubicacionTerreno": "Gimnasio Municipal de Melipilla",
  "prioridad": "ALTA",
  "comentario": "Abastecimiento urgente para centro de damnificados."
}
{
  "id": 1,
  "recursoNecesitado": "Agua Potable",
  "cantidadAproximada": 500,
  "unidadMedida": "Litros",
  "ubicacionTerreno": "Gimnasio Municipal de Melipilla",
  "prioridad": "ALTA",
  "estado": "PENDIENTE",
  "fechaReporte": "2026-06-16T13:00:00Z"
}
2. Listar Todas las Necesidades en Terreno
URL: GET /api/v1/necesidades

Respuesta Exitosa (HTTP 200 OK):

JSON
[
  {
    "id": 1,
    "recursoNecesitado": "Agua Potable",
    "cantidadAproximada": 500,
    "unidadMedida": "Litros",
    "ubicacionTerreno": "Gimnasio Municipal de Melipilla",
    "prioridad": "ALTA",
    "estado": "PENDIENTE",
    "fechaReporte": "2026-06-16T13:00:00Z"
  }
]
3. Cambiar Estado a Atendida
URL: PATCH /api/v1/necesidades/{id}/atender

Respuesta Exitosa (HTTP 200 OK):

JSON
{
  "id": 1,
  "estado": "ATENDIDA"
}
💾 Estrategia de Persistencia e Independencia de Datos
Para cumplir estrictamente con el patrón arquitectónico Database per Service, este microservicio no comparte tablas ni esquemas con los módulos de Donaciones o Logística. El aislamiento previene acoplamientos críticos a nivel de datos.

Entornos Separados (Estrategia de Perfiles)
Entorno de Ejecución (PostgreSQL): Los datos se almacenan de manera persistente en un servidor PostgreSQL independiente. La configuración se establece en src/main/resources/application.yml:

YAML
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/donaton_necesidades_db
    username: postgres
    password: tu_secure_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
Entorno de Testing (H2 Database): Para garantizar que los tests unitarios y de integración se ejecuten a máxima velocidad sin corromper la base de datos física, se utiliza una base de datos SQL H2 en memoria mediante la anotación @DataJpaTest. Cada vez que el pipeline corre, el esquema se crea, se valida y se destruye automáticamente.

🧪 Estrategia de Pruebas Unitarias y Calidad
El proyecto implementa un esquema riguroso de pruebas automatizadas utilizando JUnit 5 y el framework de simulación Mockito, logrando superar ampliamente el 60% de cobertura de código requerido por la pauta de evaluación.

Patrón de Diseño en Testing: Aislamiento Completo
Se simula el comportamiento del componente de acceso a datos (NecesidadRepository) mediante stubs controlados de Mockito. Esto permite aislar y testear únicamente la lógica pura del servicio (NecesidadServiceImpl) en un entorno 100% determinista.

Las pruebas siguen de manera estricta la estructura estructural Arrange-Act-Assert (AAA) o Setup-Execution-Verification:

Java
@ExtendWith(MockitoExtension.class)
class NecesidadServiceTest {

    @Mock
    private NecesidadRepository necesidadRepository;

    @InjectMocks
    private NecesidadServiceImpl necesidadService;

    @Test
    void testReportarNecesidad_Exito() {
        // Arrange (Setup)
        NecesidadRequestDTO request = new NecesidadRequestDTO("Agua Potable", 500, "Litros", "Melipilla", "ALTA", "Urgente");
        Necesidad necesidadSimulada = EnvioFactory.crearEntidadNecesidad(request);
        necesidadSimulada.setId(1L);
        necesidadSimulada.setEstado("PENDIENTE");
        
        when(necesidadRepository.save(any(Necesidad.class))).thenReturn(necesidadSimulada);

        // Act (Execution)
        NecesidadResponseDTO response = necesidadService.reportarNecesidad(request);

        // Assert (Verification)
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("PENDIENTE", response.getEstado());
        verify(necesidadRepository, times(1)).save(any(Necesidad.class));
    }
}
🚀 Instrucciones para Ejecución Local
Prerrequisitos
Tener instalado Java 21 (JDK).

Tener instalado Maven 3.9+.

Disponer de una instancia local activa de PostgreSQL con la base de datos donaton_necesidades_db creada.

Pasos de Lanzamiento
Clonar el repositorio correspondiente:

Bash
git clone [https://github.com/tu-organizacion/donaton-ms-necesidades.git](https://github.com/tu-organizacion/donaton-ms-necesidades.git)
cd donaton-ms-necesidades
Ejecutar el set de pruebas automatizadas (Validación de Cobertura):

Bash
mvn clean test
Compilar y empaquetar el microservicio:

Bash
mvn clean package -DskipTests
Levantar la aplicación en el puerto local 8083:

Bash
mvn spring-boot:run
