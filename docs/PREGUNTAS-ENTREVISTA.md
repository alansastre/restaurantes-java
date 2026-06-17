# Preguntas de entrevista — Java + Spring Boot

> Material de repaso basado **en este proyecto** (`restaurantes-java`, Spring Boot 4.0.5, Java 25).
> Cada respuesta enlaza con el código real para que puedas ir a verlo y, en una entrevista,
> contar "esto lo hice yo aquí".

## Cómo usar este documento

1. Primero leed la **Parte 1** (qué es un starter) y la **Parte 2** (recorrido por el `pom.xml`).
   Saber "de qué va cada dependencia" es de lo que más se pregunta y casi nadie lo sabe explicar.
2. Luego la **Parte 3**: preguntas y respuestas por bloques. Tapad la respuesta e intentad contestar en voz alta.
3. La **Parte 4** son preguntas abiertas de "defiende tu proyecto": no hay respuesta única, se trata de saber explicar **vuestro** código.

Índice:
- [Parte 1 — ¿Qué es un *starter*? Autoconfiguración y el *parent*](#parte-1)
- [Parte 2 — Recorrido por el `pom.xml` de este proyecto](#parte-2)
- [Parte 3 — Batería de preguntas y respuestas](#parte-3)
  - [A. Spring Core: IoC, inyección de dependencias y beans](#a-core)
  - [B. Spring Boot: autoconfiguración, properties y perfiles](#b-boot)
  - [C. JPA / Spring Data: entidades, relaciones y repositorios](#c-jpa)
  - [D. Spring MVC + Thymeleaf](#d-mvc)
  - [E. Validación de formularios](#e-validacion)
  - [F. Spring Security](#f-security)
  - [G. API REST + OpenAPI/Swagger](#g-rest)
  - [H. Perfiles, Docker, PostgreSQL y despliegue](#h-deploy)
  - [I. Lombok](#i-lombok)
- [Parte 4 — "Defiende tu proyecto"](#parte-4)
- [Chuleta rápida de anotaciones](#chuleta)

---

<a name="parte-1"></a>
## Parte 1 — ¿Qué es un *starter*? Autoconfiguración y el *parent*

**¿Qué es un "starter" de Spring Boot?**
Es una **dependencia paraguas**: un único `artifactId` que arrastra de forma transitiva todas las librerías que normalmente necesitas juntas para una funcionalidad, con versiones ya compatibles entre sí. Por ejemplo `spring-boot-starter-data-jpa` te trae Hibernate, la API de JPA, Spring Data, el pool de conexiones HikariCP, etc. Tú pones **una** línea en vez de diez.

**¿Y la "autoconfiguración"?**
Spring Boot detecta qué tienes en el *classpath* y **configura beans por ti** con valores por defecto razonables. Si ve JPA + un driver de base de datos, te monta el `DataSource`, el `EntityManager` y las transacciones sin que escribas nada. Es la magia de "funciona sin configurar". Se activa con `@EnableAutoConfiguration` (incluida dentro de `@SpringBootApplication`). Todo lo autoconfigurado se puede **sobrescribir** por `application.properties` o definiendo tu propio bean.

**¿Para qué sirve el `<parent>` (`spring-boot-starter-parent`)?**
Es un **BOM** (*Bill of Materials*): centraliza las **versiones** de todas las dependencias. Por eso en este `pom.xml` la mayoría de starters **no llevan `<version>`** (líneas 33-110): la versión la decide el parent (4.0.5). Solo ponemos versión en lo que no gestiona el parent (springdoc, webjars). Esto evita el "infierno de versiones" y que dos librerías choquen.

---

<a name="parte-2"></a>
## Parte 2 — Recorrido por el `pom.xml` de este proyecto

Estas son **todas** las dependencias del proyecto y para qué sirve cada una. (Mirad `pom.xml`.)

| Dependencia | Qué es / para qué sirve | Dónde se ve en el proyecto |
|---|---|---|
| `spring-boot-starter-webmvc` | El núcleo web: Spring MVC + servidor **Tomcat** embebido + Jackson (JSON). Te da `@Controller`, `@GetMapping`, etc. | Todos los controllers |
| `spring-boot-starter-data-jpa` | JPA + **Hibernate** + Spring Data + HikariCP. Te da `@Entity` y los `JpaRepository`. | `model/`, `repository/` |
| `spring-boot-starter-thymeleaf` | Motor de plantillas **server-side** para generar el HTML. | `templates/` |
| `spring-boot-starter-security` | **Spring Security**: login, filtros, control de acceso por rol. | `config/SecurityConfig.java` |
| `thymeleaf-extras-springsecurity6` | Integra Security **dentro** de Thymeleaf: `sec:authorize`, `sec:authentication`. | `navbar.html` y plantillas |
| `spring-boot-starter-validation` | **Bean Validation** (Hibernate Validator): `@NotBlank`, `@Email`, `@Valid`. | `dto/RegisterForm`, formularios |
| `springdoc-openapi-starter-webmvc-ui` | Genera la **documentación OpenAPI** y la UI de **Swagger** automáticamente. | `controller/api/`, `config/OpenApiConfig.java` |
| `spring-boot-h2console` | La **consola web de H2** (`/h2-console`) para ver la BD en memoria. | dev/test |
| `h2` *(runtime)* | Base de datos **en memoria**, para desarrollo y tests. | perfil `dev`/`test` |
| `postgresql` *(runtime)* | **Driver JDBC** de PostgreSQL, para producción. | perfil `prod`, `application-prod.properties` |
| `lombok` *(opcional)* | Genera getters/setters/constructores en tiempo de compilación. | `model/`, servicios |
| `bootstrap` (webjar) | El CSS de **Bootstrap 5** servido desde el propio `.jar`. | `layout/head.html` |
| `font-awesome` (webjar) | Los **iconos**. | plantillas |
| `*-test` (jpa, thymeleaf, webmvc, security) | Las versiones **de test** de cada starter: JUnit 5, Mockito, MockMvc, `spring-security-test`. | carpeta `src/test` |

### Detalle importante: esto es **Spring Boot 4**, y los starters cambiaron de nombre

Si en una entrevista enseñas un proyecto de Boot 4, conviene saber esto (es un "plus"):

- **`spring-boot-starter-web` se llama ahora `spring-boot-starter-webmvc`.** (En el proyecto, `pom.xml:46`.)
- **El antiguo `spring-boot-starter-test` (monolítico) se ha partido por tecnología:** ahora hay `spring-boot-starter-data-jpa-test`, `spring-boot-starter-thymeleaf-test`, `spring-boot-starter-webmvc-test`, `spring-boot-starter-security-test`. Solo te traes el test de lo que usas. (`pom.xml:61-104`.)
- **La consola H2 es un starter propio** (`spring-boot-h2console`), antes venía suelta con la dependencia `h2`. (`pom.xml:34`.)

> Idea para soltar en la entrevista: *"el proyecto va sobre Spring Boot 4, donde los starters de test se dividieron por módulos y `web` pasó a llamarse `webmvc`"*. Demuestra que entiendes qué hay debajo, no que copiaste un `pom`.

---

<a name="parte-3"></a>
## Parte 3 — Batería de preguntas y respuestas

<a name="a-core"></a>
### A. Spring Core: IoC, inyección de dependencias y beans

**¿Qué es la Inversión de Control (IoC) y el contenedor de Spring?**
En vez de crear tú los objetos con `new`, es **Spring** quien los crea, los guarda y te los entrega cuando los necesitas. Ese "almacén de objetos" es el **contexto** (`ApplicationContext`). Los objetos que gestiona se llaman **beans**.

**¿Qué es la inyección de dependencias (DI)?**
Es cómo Spring te **pasa** las dependencias que un bean necesita. Por ejemplo, `UserService` necesita un `UserRepository`: no lo crea, lo **recibe** ya construido. (`service/UserService.java:28`.)

**¿Qué tipos de inyección hay y cuál usáis?**
Por **constructor** (recomendada), por *setter* y por campo (`@Autowired` en el atributo, desaconsejada). En este proyecto se usa **inyección por constructor**, pero el constructor lo genera Lombok con `@AllArgsConstructor` sobre campos `private final`. (`service/UserService.java:25-32`.)

**¿Por qué es mejor inyectar por constructor?**
Porque las dependencias pueden ser `final` (inmutables), el objeto nace **completo y válido**, y se ve clarísimo qué necesita la clase. Además facilita los tests (le pasas un mock por el constructor sin Spring).

**¿Qué diferencia hay entre `@Component`, `@Service`, `@Repository` y `@Controller`?**
Todas registran un bean; cambia la **intención semántica** y, en algún caso, el comportamiento:
- `@Controller` / `@RestController` → capa web.
- `@Service` → lógica de negocio (`UserService`, `FavoriteService`).
- `@Repository` → acceso a datos; además **traduce excepciones** de la BD a las de Spring.
- `@Component` → genérico, cuando no encaja en las anteriores.

**¿Cómo encuentra Spring esos beans?**
Por **component scan**: `@SpringBootApplication` está en `com.restaurantes`, así que Spring escanea ese paquete y sus subpaquetes buscando clases anotadas.

---

<a name="b-boot"></a>
### B. Spring Boot: autoconfiguración, properties y perfiles

**¿Qué hace `@SpringBootApplication`?**
Es un "3 en 1": `@Configuration` (es una clase de configuración) + `@EnableAutoConfiguration` (activa la autoconfiguración) + `@ComponentScan` (escanea el paquete). (`RestaurantesJavaApplication.java`.)

**¿Dónde se configura la aplicación?**
En `src/main/resources/application.properties` (común) y en ficheros por perfil: `application-dev.properties`, `application-prod.properties`.

**¿Qué es un perfil (*profile*) de Spring?**
Un conjunto de configuración que se activa según el entorno. Aquí hay **dev** (H2 en memoria) y **prod** (PostgreSQL). El perfil por defecto se fija con `spring.profiles.default=dev` (`application.properties:9`), y se cambia sin tocar código con la variable de entorno `SPRING_PROFILES_ACTIVE=prod` o el parámetro de arranque.

**`spring.profiles.default` vs `spring.profiles.active`, ¿diferencia?**
`default` es el perfil que se usa **si no activas ninguno**; `active` lo **fuerza**. Usamos `default` para que en local arranque en `dev` solo, pero se pueda sobreescribir en el servidor.

---

<a name="c-jpa"></a>
### C. JPA / Spring Data: entidades, relaciones y repositorios

**¿Qué es una entidad JPA?**
Una clase Java mapeada a una **tabla**. Se marca con `@Entity` y necesita un `@Id`. Ejemplo: `User`, `Order`, `Dish`, `Restaurant`...

**¿Cómo se genera la clave primaria?**
Con `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)`: la BD autoincrementa el id. (`model/User.java:23-25`.)

**Tenéis `@Table(name = "users")` y `@Table(name = "pedidos")`. ¿Por qué?**
Porque `user` y `order` son **palabras reservadas** en SQL. Si dejas que la tabla se llame igual que la entidad, la BD da error de sintaxis. Por eso se renombra la tabla. (`model/Order.java:9-12`.)

**¿Qué relaciones JPA usáis?**
`@ManyToOne` sobre todo: un `Order` pertenece a un `Restaurant` y a un `User` (`model/Order.java:33-37`); un `Dish` pertenece a un `Restaurant`. Las inversas serían `@OneToMany`.

**¿Cuál es el `FetchType` por defecto de cada relación?**
`@ManyToOne` y `@OneToOne` → **EAGER** (se cargan siempre). `@OneToMany` y `@ManyToMany` → **LAZY** (se cargan cuando las usas). Saber esto es clave para el problema siguiente.

**¿Qué es el problema N+1 y cómo se evita?**
Cargas una lista de N pedidos (1 consulta) y, al recorrer y acceder a `pedido.getRestaurant()` de cada uno, se lanza **una consulta por pedido** → N+1 consultas. Se evita con un **`JOIN FETCH`** en una `@Query`, con `@EntityGraph`, o con DTOs que traigan ya lo necesario.

**¿Por qué `@Enumerated(EnumType.STRING)` y no `ORDINAL`?**
Con `STRING` se guarda el **nombre** del enum (`"PENDING"`); con `ORDINAL` se guarda su **posición** (0, 1, 2). El problema de `ORDINAL` es que si reordenas el enum, los datos antiguos pasan a significar otra cosa. `STRING` es legible y robusto. (`model/Order.java:24-25`.)

**¿Qué es un `JpaRepository` y qué te da gratis?**
Una interfaz que **tú no implementas**: Spring Data te genera `findAll`, `findById`, `save`, `deleteById`, `count`, `existsById`... solo con declarar `interface DishRepository extends JpaRepository<Dish, Long>`. (`repository/DishRepository.java`.)

**¿Qué son las *derived queries* (consultas derivadas)?**
Métodos cuyo **nombre** describe la consulta y Spring genera el SQL. Ejemplos reales:
- `findByPriceLessThanEqual(Double price)` → `WHERE price <= ?`
- `findByRestaurantIdOrderByPrice(Long id)` → filtra por el id del restaurante y ordena por precio. (`repository/DishRepository.java:9-13`.)
- `existsByUsername(...)`, `findByUser_IdOrderByDateDesc(...)`, `countByUser_Id(...)` (en otros repos).

**¿Y si la consulta es muy compleja para el nombre?**
Se usa `@Query` con JPQL (o SQL nativo). En el proyecto, por ejemplo, `calculateTotalMoneySpentByUserId` para sumar el gasto de un usuario. (Se usa en `service/UserService.java:98`.)

**¿Qué es `ddl-auto`?**
Le dice a Hibernate qué hacer con el **esquema** al arrancar:
- `create` → borra y crea las tablas.
- `create-drop` → como create y además borra al apagar (típico en tests con H2).
- `update` → crea/añade lo que falte y **conserva** los datos (lo usamos en `prod`, `application-prod.properties:17`).
- `validate` → solo valida que el esquema cuadra, no toca nada.
- `none` → no hace nada.

---

<a name="d-mvc"></a>
### D. Spring MVC + Thymeleaf

**¿Qué diferencia hay entre `@Controller` y `@RestController`?**
`@Controller` devuelve el **nombre de una plantilla** (Thymeleaf) que se renderiza a HTML. `@RestController` (= `@Controller` + `@ResponseBody`) devuelve **datos** (JSON) directamente. En el proyecto conviven: `RestaurantController` (web) y `RestaurantRestController` (API). 

**¿Cómo se pasan datos del controller a la vista?**
Con el objeto `Model`: `model.addAttribute("restaurants", ...)` y en el HTML se leen con `${restaurants}`. (Ver cualquier controller + su plantilla.)

**¿Para qué sirven `@GetMapping`, `@PostMapping`, etc.?**
Mapean una URL + un verbo HTTP a un método. `@GetMapping("/restaurants/{id}")` responde a `GET /restaurants/5`.

**¿`@PathVariable` vs `@RequestParam` vs `@ModelAttribute`?**
- `@PathVariable` → parte de la **ruta**: `/restaurants/{id}`.
- `@RequestParam` → parámetro de query o de formulario: `?price=10`.
- `@ModelAttribute` → **liga un formulario completo** a un objeto (binding de todos los campos del `<form>`).

**¿Qué hace un `@ControllerAdvice`?**
Es código transversal a **todos** los controllers, para no repetirlo. En el proyecto, `GlobalModelAttributes` usa `@ControllerAdvice` + `@ModelAttribute` para inyectar en **todas** las vistas los favoritos del usuario y el perfil activo, sin ensuciar cada controller. (`controller/GlobalModelAttributes.java`.) También se usa para manejo global de errores con `@ExceptionHandler`.

**¿Qué aporta `thymeleaf-extras-springsecurity6`?**
Permite usar seguridad dentro del HTML: `sec:authorize="hasRole('ADMIN')"` para mostrar/ocultar botones, o `sec:authentication="name"` para pintar el usuario logueado.

---

<a name="e-validacion"></a>
### E. Validación de formularios

**¿Cómo validáis los formularios?**
Con **Bean Validation** (`spring-boot-starter-validation`): se anotan los campos del DTO (`@NotBlank`, `@Email`, `@Size`...) y en el controller se recibe con `@Valid` + un `BindingResult` justo después para recoger los errores y volver a mostrar el formulario. El DTO de registro es `RegisterForm`. (`dto/RegisterForm`.)

**¿Por qué un DTO (`RegisterForm`) y no la entidad `User` directamente en el formulario?**
Porque el formulario tiene campos que **no** están en la entidad (p. ej. `passwordConfirm`) y porque no quieres exponer ni bindear toda la entidad. El DTO modela exactamente lo que el formulario necesita; luego el servicio lo convierte en `User`. (`service/UserService.java:53`.)

**¿Validación en el DTO y también en el servicio?**
Sí: las anotaciones cubren el **formato** (campo vacío, email mal), pero reglas de negocio como "el username ya existe" o "las contraseñas no coinciden" se comprueban en el **servicio** lanzando excepciones. (`service/UserService.java:55-62`.)

---

<a name="f-security"></a>
### F. Spring Security

**Explica el flujo de login de la aplicación.**
1. El usuario envía usuario/contraseña en `/login` (formulario, `formLogin`).
2. Spring llama a `UserDetailsService.loadUserByUsername(...)`, que aquí implementa `UserService`: busca el `User` en la BD por username. (`service/UserService.java:36`.)
3. Compara la contraseña enviada (cifrada con BCrypt) con la guardada usando el `PasswordEncoder`.
4. Si todo va bien, crea la sesión y aplica las reglas de acceso del `SecurityFilterChain`.

**¿Qué es el `SecurityFilterChain`?**
El bean donde defines **qué se protege y cómo**: rutas públicas, rutas por rol, el formulario de login, CSRF... (`config/SecurityConfig.java:23-75`.) Sustituye al antiguo `WebSecurityConfigurerAdapter` (ya eliminado).

**¿Qué es `UserDetails` y por qué `User` lo implementa?**
`UserDetails` es la interfaz que Spring Security entiende como "usuario": expone `getUsername()`, `getPassword()`, `getAuthorities()` y banderas como `isEnabled()`. Vuestra entidad `User` la implementa para que **la misma clase** sea entidad JPA y usuario de seguridad. (`model/User.java:21`.)

**En `User.isEnabled()` devolvéis `active`. ¿Qué efecto tiene?**
Si `active` es `false`, Spring Security **no deja iniciar sesión** a ese usuario aunque la contraseña sea correcta. Es el "borrado lógico" / baneo. (`model/User.java:49-52`.)

**¿Cómo se guardan las contraseñas?**
**Nunca en texto plano.** Se cifran con **BCrypt** a través del bean `PasswordEncoder`. Al registrar: `passwordEncoder.encode(...)` (`service/UserService.java:71`); al hacer login, Spring compara con el mismo encoder. (`config/SecurityConfig.java:16-19`.)

**¿Por qué BCrypt y no un hash normal (MD5/SHA)?**
Porque es **lento a propósito** y lleva *salt* incorporado, lo que lo hace resistente a ataques de fuerza bruta y a tablas precalculadas (*rainbow tables*).

**Pregunta trampa: usáis `.hasRole("ADMIN")` pero el enum es `ROLE_ADMIN`. ¿No falta el prefijo?**
No, y aquí está el truco: `getAuthorities()` devuelve la authority con el nombre completo `ROLE_ADMIN` (`model/User.java:45-47`), y **`hasRole("ADMIN")` le añade automáticamente el prefijo `ROLE_`** por debajo. Si usaras `hasAuthority(...)` tendrías que escribir el nombre completo `hasAuthority("ROLE_ADMIN")` sin prefijo automático. (`config/SecurityConfig.java:40`.)

**¿Cómo proteges rutas por método HTTP?**
Con `requestMatchers(HttpMethod.POST, "/restaurants").hasRole("ADMIN")`: cualquiera ve el listado (`GET ... permitAll`), pero solo un admin puede crear (`POST`). (`config/SecurityConfig.java:39-44`.)

**¿Qué es CSRF y por qué lo desactiváis en `/api/v1/**` y `/h2-console`?**
CSRF protege los formularios web de peticiones falsificadas. La **API REST** es sin estado (no usa la sesión por cookies del navegador del mismo modo) y la consola H2 es una herramienta de desarrollo, así que se excluyen del filtro CSRF para que funcionen. (`config/SecurityConfig.java:25`.)

**¿Cómo obtienes el usuario logueado dentro de un controller?**
Con `@AuthenticationPrincipal User user`: Spring te inyecta la entidad del usuario actual (o `null` si es anónimo). (`controller/GlobalModelAttributes.java:30`.)

---

<a name="g-rest"></a>
### G. API REST + OpenAPI/Swagger

**¿Qué hace `@RestController` + `@RequestMapping("/api/v1/restaurants")`?**
Define un controlador que devuelve **JSON** y agrupa todas sus rutas bajo `/api/v1/restaurants`. (`controller/api/RestaurantRestController.java:21-22`.)

**¿Qué verbos HTTP implementáis y qué hace cada uno?**
- `GET` → leer (lista / por id).
- `POST` → crear (devuelve **201 Created** con la cabecera `Location`).
- `PUT` → reemplazo **completo** del recurso.
- `PATCH` → actualización **parcial** (solo los campos que llegan no nulos).
- `DELETE` → borrar (devuelve **204 No Content**).
(Todo en `controller/api/RestaurantRestController.java`.)

**Diferencia entre PUT y PATCH (en vuestro código).**
`PUT` **setea todos** los campos, así que si mandas uno a `null` lo guarda como `null`. `PATCH` solo toca **los que llegan no nulos** (`if (restaurant.getName() != null) ...`). (`RestaurantRestController.java:74-105`.)

**¿Para qué sirve `ResponseEntity`?**
Para controlar la respuesta HTTP completa: **código de estado**, cabeceras y cuerpo. Ej.: `ResponseEntity.created(URI...).body(saved)` devuelve 201 + `Location`. (`RestaurantRestController.java:70`.)

**¿Cómo devolvéis un 404 cuando no existe el recurso?**
Lanzando `new ResponseStatusException(HttpStatus.NOT_FOUND, "...")` en el `orElseThrow`. (`RestaurantRestController.java:46-48`.)

**¿Qué es `@RestControllerAdvice` y para qué lo usáis?**
Manejo de errores **global** para la API: traduce excepciones técnicas a una respuesta JSON limpia con `status`, `message`, `timestamp` y `path`. Por ejemplo, una violación de integridad (`DataIntegrityViolationException`) se convierte en una respuesta de error controlada en vez de un 500 feo. (`controller/api/ApiExceptionAdvice.java`.)

**¿Qué aporta springdoc / Swagger?**
Lee tus controllers y genera **sola** la documentación interactiva de la API en `/swagger-ui/index.html`. Las anotaciones `@Tag`, `@Operation`, `@ApiResponses`, `@Parameter` enriquecen esa documentación. (`RestaurantRestController.java:24-45`, `config/OpenApiConfig.java`.)

**Código de estado: ¿200, 201, 204, 400, 404, 409?**
200 OK · 201 Created (creado, con Location) · 204 No Content (borrado, sin cuerpo) · 400 Bad Request (petición mal formada) · 404 Not Found · 409 Conflict (choque, p. ej. integridad de datos). Todos aparecen en la API del proyecto.

---

<a name="h-deploy"></a>
### H. Perfiles, Docker, PostgreSQL y despliegue

**¿Qué base de datos usáis en cada entorno?**
**H2 en memoria** en `dev`/`test` (rápida, se recrea en cada arranque, tiene consola web) y **PostgreSQL** en `prod`. El cambio es solo de configuración: la misma JPA funciona contra ambas. (`application-prod.properties`.)

**¿Cómo se configura la conexión en prod sin meter credenciales en el código?**
Con **variables de entorno** y valores por defecto: `${POSTGRES_HOST:localhost}`, `${POSTGRES_PASSWORD:restaurantes}`... Así el servidor (o Render) inyecta los secretos sin tocar el repo. (`application-prod.properties:11-13`.)

**¿Para qué el `compose.yaml`?**
Levanta PostgreSQL en un contenedor Docker con un comando, para que prod tenga su BD sin instalar nada a mano. (Ver `docs/DOCKER.md` y `compose.yaml`.)

**Cuidado conocido del proyecto: ¿por qué en prod se pueden duplicar los datos?**
Porque el `main()` y el `DataInitializer` **siembran datos en cada arranque**. Con H2 da igual (la BD se recrea), pero con PostgreSQL persistente y `ddl-auto=update`, un segundo arranque vuelve a insertar → duplicados o error de `UNIQUE`. Solución: `docker compose down -v` para empezar limpio, o saltar el *seeding* en prod. (`application-prod.properties:4-9`.)

---

<a name="i-lombok"></a>
### I. Lombok

**¿Qué es Lombok y qué genera?**
Una librería que **genera código repetitivo en compilación** a partir de anotaciones: `@Getter`/`@Setter`, `@NoArgsConstructor`/`@AllArgsConstructor`, `@Builder`, `@ToString`, `@Slf4j` (logger)... Reduce el *boilerplate*. (`model/User.java:13-18`.)

**¿Cómo ayuda a la inyección de dependencias?**
`@AllArgsConstructor` sobre una clase con campos `private final` genera el constructor que Spring usa para inyectar. (`service/UserService.java:25`.)

**¿Algún riesgo con Lombok y JPA?**
Sí: `@ToString` o `@EqualsAndHashCode` que incluyan **relaciones** pueden provocar recursión infinita o `LazyInitializationException` al intentar pintar una asociación no cargada. Por eso en entidades conviene excluir las relaciones de esos métodos.

---

<a name="parte-4"></a>
## Parte 4 — "Defiende tu proyecto" (preguntas abiertas)

No tienen una respuesta cerrada: el entrevistador quiere ver que **entiendes tu propio código**. Practicad explicarlas en 1-2 minutos:

1. **Hazme un recorrido por la arquitectura.** (Capas: `model` → `repository` → `service` → `controller` → `templates`; y la API en `controller/api`.)
2. **¿Qué pasa, paso a paso, desde que pulso "Login" hasta que veo la home?**
3. **Enséñame cómo proteges una ruta solo para administradores.** (`SecurityConfig` + `sec:authorize` en el HTML.)
4. **¿Por qué separaste el controlador web del REST?** ¿Cuándo usarías uno u otro?
5. **¿Cómo evitas mostrar la contraseña o guardarla en texto plano?**
6. **Si tuvieras que añadir "comentarios a un plato", ¿qué tocarías?** (entidad + relación + repo + servicio + controller + plantilla + test).
7. **¿Qué harías distinto si esto tuviera 100.000 usuarios?** (índices, paginación, *fetch* de relaciones, caché...).
8. **¿Qué parte del proyecto te costó más y cómo lo resolviste?** (la respuesta honesta vende más que la perfecta).
9. **¿Cómo lo desplegaste?** (perfiles, PostgreSQL, variables de entorno, Render).
10. **Si esto fallara en producción, ¿por dónde empezarías a mirar?** (logs, perfil activo, conexión a BD).

> Consejo: ten el proyecto **desplegado y con el enlace a mano**. Poder decir "te lo enseño funcionando" cambia por completo una entrevista.

---

<a name="chuleta"></a>
## Chuleta rápida de anotaciones

| Anotación | Para qué |
|---|---|
| `@SpringBootApplication` | Arranque: config + autoconfig + scan |
| `@Configuration` / `@Bean` | Definir beans a mano |
| `@Component` `@Service` `@Repository` | Registrar beans por capa |
| `@Controller` / `@RestController` | Web (HTML) / API (JSON) |
| `@GetMapping` `@PostMapping` `@PutMapping` `@PatchMapping` `@DeleteMapping` | Mapear URL + verbo |
| `@PathVariable` `@RequestParam` `@ModelAttribute` `@RequestBody` | Recibir datos de la petición |
| `@Entity` `@Table` `@Id` `@GeneratedValue` | Mapeo JPA |
| `@ManyToOne` `@OneToMany` `@Enumerated` `@Column` | Relaciones y columnas |
| `@Valid` + `BindingResult` | Validar formularios |
| `@ControllerAdvice` / `@RestControllerAdvice` | Código global (errores, datos de vista) |
| `@ExceptionHandler` | Capturar excepciones |
| `@AuthenticationPrincipal` | Usuario logueado |
| `@Bean SecurityFilterChain` / `PasswordEncoder` | Configurar seguridad |
| `@Operation` `@Tag` `@ApiResponses` | Documentar la API (OpenAPI) |
