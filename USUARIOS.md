

# Autentication y autorization con usuarios con Spring Security

Ahora mismo la aplicación todo el mundo ve todo. 

No hay usuarios.

Queremos registro y login de usuarios, para que un usuario pueda iniciar sesion y hacer pedidos, ver sus pedidos, hacer reviews, etc.

Queremos que cada usuario tenga un rol, por ejemplo ROLE_USER, y que solo los usuarios con ese rol puedan hacer pedidos, reviews, etc.

Queremos que haya usuario admin con rol ROLE_ADMIN que pueda gestionar los restaurantes, platos, etc.

Para esto vamos a usar Spring Security, que es un framework de seguridad para aplicaciones web en Java.

## Paso 1: dependencias en pom.xml (OK)

* spring-boot-starter-security
* thymeleaf-extras-springsecurity6
* spring-security-test
* Opcional: spring-boot-starter-validation

## Paso 2: crear entidad User y enum Role (OK)

* enums/Role.java: ROLE_USER, ROLE_ADMIN
* model/User.java: id, username, password, role
   * implements UserDetails de Spring Security

## Paso 3: crear repositorio UserRepository (OK)

* repository/UserRepository.java:
  * findByUsername(String username)
  * boolean existsByUsername(String username)
  * boolean existsByEmail(String email)

## Paso 4: crear servicio UserService (OK)

* service/UserService.java
    * implements UserDetailsService de Spring Security
    * loadUserByUsername(String username)  sirve para que spring security pueda cargar el usuario a partir del username y comparar la contraseña al hacer login y demás

## Paso 5: crear SecurityConfig

* config/SecurityConfig.java 
    * securityFilterChain configura las rutas protegidas, login, logout, etc.
    * passwordEncoder() para cifrar las contraseñas
  
## Paso 6: controlador AuthController

* controller/AuthController.java
    * GET /login → muestra el formulario de login
    * GET /register → muestra el formulario de registro
    * POST /register → procesa el formulario de registro, crea el usuario con rol ROLE_USER

## Paso 7: vistas Thymeleaf HTML

* templates/auth/register.html
* templates/auth/login.html
* templates/fragments/navbar.html → mostrar enlaces de login/register o logout dependiendo de si el usuario está autenticado o no, usando thymeleaf-extras-springsecurity6

## Paso 8: opcional ViewSecurityAdvice.java

Esta clase proporciona métodos que se pueden usar en las vistas Thymeleaf para mostrar/ocultar partes de la vista dependiendo de si el usuario está autenticado o no, o si tiene un rol concreto.

Objetivo: `th:if="${isAuthenticated}` o `th:if=${isAdmin}`.

* config/ViewSecurityAdvice.java
    * isAuthenticated() para usar en Thymeleaf y mostrar/ocultar partes de la vista dependiendo de si el usuario está autenticado o no
    * isAdmin()
    * currentUserName()

## Paso 9: integración de User con Order y Review

* Order.java: añadir campo User user con @ManyToOne
* Review.java: añadir campo User user con @ManyToOne

En controller OrderController, al crear una orden, asignar el usuario autenticado a la orden.

En controller ReviewController, al crear una review, asignar el usuario autenticado a la review.