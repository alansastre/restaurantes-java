




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

* Home
* Loggers @Slf4j de Lombok: log.info, log.warn, log.error (OK)
* Fixes en proyectos y tableros
* config/DataInitializer.java datos demo y dejar main limpio
* Input tipo file en formularios para subir imagen (90%)
* GitHub Actions (Integracion Continua) (OK)
* Favoritos (Product, House, Movie) (80 %)
  * User
  * Favorite: @ManyToOne User, @ManyToOne Restaurant, @ManyToOne Dish, LocalDateTime
  * Restaurant
* order-detail: datos de pago y descuento (PENDIENTE)
  * cardOwner
  * cardNumber
  * cardExpirationDate
  * cardCode
* Controlador API REST @RestController y probarlo con OpenAPI Swagger / Postman
* Presentaciones: estructura. miercoles 10/06
* GitHub: ramas (branches) y Pull Request
* Base de datos MySQL


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