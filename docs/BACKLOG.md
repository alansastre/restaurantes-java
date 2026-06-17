

Pendiente:


* spring-validation (OK)
  * dependencia spring-boot-starter-validation en pom.xl
  * Te ahorra tener que validar manualmente con if en controller
  * anotaciones en atributos:
    * @NotBlank
    * @Size
    * @PositiveOrZero
    * @Positive
  * Agregar @Valid en los @ModelAttribute @PostMapping de controladores y BindingResult
  * En el HTML mostrar error por cada campo que falle la validación `#fields.hasErrors('rating')`

* Input tipo file en formularios para subir imagen (OK)

* config/DataInitializer.java datos demo y dejar main limpio

* GitHub: ramas (branches) y Pull Request (visto ya un ejemplo)
* Base de datos PostgreSQL (OK)
* Revisión proyectos
* Test de servicio, Test controller
* Ensayos:
  * Grupo 2: lunes 15/06
  * Grupo 1 y 3: jueves 11/06

* Controlador API REST @RestController y probarlo con OpenAPI Swagger / Postman (OK)
  * controller/api/RestaurantRestController.java (OK)
  * controller/api/DishRestController.java
  * controller/api/ApiExceptionAdvice.java (gestion global errores personalizada)
    * timestamp
    * status
    * error
    * message
    * path

* README para proyectos (OK)
* Home (OK) index.html
* Redirección global tras login poder volver a donde estabamos en lugar de perder progreso (OK)

* Que un User solo pueda editar/borrar sus propias reviews, comprobar si le pertenecen primero o es admin. (OK)

* @PostMapping /profile poder actualizar tu propio perfil, no desactivarse. (OK)

* Botón de copiar a clipboard el id o token o lo que quiera (OK)






Subir imágenes:

* User, Restaurant, Dish: private String imageUrl;
* service/FileStorageService.java
* config/WebConfig.java
* Actualizar en SecurityConfig añadir ruta /uploads/**
* user-form.html, dish-form.html, restaurant-form.html añadir un input de tipo file
* En los Controller agregar MultipartFile imageFile
* Mostrar imagen en la aplicacion










Pendiente:

* Nuevos campos: firstName, lastName
* Foto
* @GetMapping /profile (PENDIENTE)






Hecho:
* UserService:
* update (PENDIENTE)
* UserController
  * admin
    * @GetMapping new /admin/users/new (PENDIENTE)
    * @GetMapping edit /admin/users/edit/1 (PENDIENTE)
    * @PostMapping /admin/users (PENDIENTE)
* Panel de usuarios:
  * UserRepository OK
  * UserService OK
    * loadUserByUsername OK
    * register OK
    * findStatsById OK
    * update (PENDIENTE)
    * UserController
      * list /admin/users OK
      * detail /admin/users/1 OK
      * new /admin/users/new (PENDIENTE)
      * edit /admin/users/edit/1 (PENDIENTE)
      * /profile (PENDIENTE)
    * user-list.html OK
    * user-detail.html OK
    * user-form.html  (PENDIENTE)
    * navbar.html poner enlace a /admin/users para ROLE_ADMIN OK
    * navbar.html poner enlace en su nombre a /profile para todos
    * Datos en Data Initializer
    * Avatar del usuario
    * /profile que pueda cambiar nombre apellido y biografia
    * User: firstName, lastName, active, bio, avatar

Pendiente:


* Loggers @Slf4j de Lombok: log.info, log.warn, log.error (OK)
* Fixes en proyectos y tableros


* GitHub Actions (Integracion Continua) (OK)
* Favoritos (Product, House, Movie) (80 %)
  * User
  * Favorite: @ManyToOne User, @ManyToOne Restaurant, @ManyToOne Dish, LocalDateTime
  * Restaurant
* order-detail: datos de pago y descuento (OK)
  * cardOwner
  * cardNumber
  * cardExpirationDate
  * cardCode




* Advice (Controller global)
* spring validation para formularios para validar datos de entrada


* Test de un controlador con MockMvc
* Presentación slides: google slides, canva, powerpoint
* equals y hashcode en entidades para comparar objeto
* OrderService para lógica de pedidos
* Despliegue en Render



Hecho:



g1: 
* redirect a formulario pelicula tras crear director
* redirect a movies si intenta acceso no autorizado a new o edit o cualquier otra