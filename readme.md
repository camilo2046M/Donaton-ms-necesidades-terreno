
## Pruebas Unitarias
Para ejecutar los tests de cobertura y asegurar la calidad del código:
```mvn test ```

##  Cobertura de Código con JaCoCo

Este microservicio utiliza **JaCoCo (Java Code Coverage)** para medir la cobertura de las pruebas unitarias y de integración, asegurando que se cumpla con el estándar mínimo requerido del 60%.

### 🛠 Paso a Paso para Inicializar y Ver el Reporte

Sigue estos pasos para ejecutar la suite de pruebas y generar el reporte analítico y gráfico:

#### 1. Agregar el Plugin al Proyecto (Ya configurado)
El archivo `pom.xml` incluye el plugin oficial de JaCoCo en la sección de construcción (`<build>` -> `<plugins>`):
```xml`

    <plugin>
       <groupId>org.jacoco</groupId>
       <artifactId>jacoco-maven-plugin</artifactId>
       <version>0.8.11</version>
       <executions>
           <execution>
               <goals>
                   <goal>prepare-agent</goal>
               </goals>
           </execution>
           <execution>
               <id>report</id>
               <phase>test</phase>
               <goals>
                   <goal>report</goal>
               </goals>
           </execution>
       </executions>
      </plugin> 

## 2. Ejecutar los Tests y Generar las Métricas

En Windows (PowerShell):

```.\mvnw clean test ```

En Linux / macOS o Git Bash:

```./mvnw clean test```

## Alternativa Gráfica (IntelliJ IDEA):
Abre la pestaña lateral Maven ➡️ Despliega Lifecycle ➡️ Presiona Ctrl y haz doble clic en clean y luego en test.

Al finalizar con éxito, verás el mensaje BUILD SUCCESS en la consola.

## 3. Visualizar el Reporte Gráfico Interactivos
Una vez finalizados los tests, JaCoCo compila un sitio web local con tablas y gráficos de barras de cobertura.

Navega en tu explorador de archivos hasta la siguiente ruta dentro del proyecto:
target/site/jacoco/

Busca y abre el archivo index.html haciendo doble clic (se abrirá en tu navegador web como Chrome o Edge).

Ahí podrás analizar el desglose detallado por paquetes (controller, service, repository), líneas ejecutadas y caminos lógicos cubiertos.