

* Restaurant [OK]
  * Long id 
  * String name
  * Double averagePrice
  * Boolean active
  * FoodType category (enum)
  * LocalDate startDate
  * Integer numberEmployees

* Employee [OK]
  * Long id
  * String firstName
  * String lastName
  * String dni
  * Integer age
  * Restaurant restaurant (ManyToOne) (NUEVO) [OK]

* FoodType (enum) [OK]
  * SPANISH
  * JAPANESE


* Dish (Plato)[OK]
  * Long id
  * String name
  * String description (longitud 500 en base de datos)
  * Double price
  * DishType type (enum: STARTER, MAIN_COURSE, DESSERT)
  * Asociación:
    * Restaurant restaurant (ManyToOne)

* Paso 1: crear class Dish y enum DishType
* Paso 2: crear asociación ManyToOne con Restaurant
* Paso 3: crear repositorio DishRepository con métodos CRUD
* Paso 4: crear algunos platos asociados a restaurantes existentes y guardarlos en la base de datos


* Order (pedido) [OK] + OrderRepository 
  * Long id
  * LocalDateTime date
  * Double totalPrice
  * Double tip
  * Integer tableNumber
  * Integer numPeople
  * OrderStatus status (enum: PENDING, IN_PROGRESS, COMPLETED)
  * asociaciones:
    * restaurant (ManyToOne)
  * Constructor vacío
  * Constructor con todos los parámetros excepto id
  * Getter
  * Setter
  * ToSTring

* OrderLine [OK] + OrderLineRepository
  * Long id 
  * Integer quantity
  * Asociaciones:
    * Dish dish (ManyToOne)
    * Order order (ManyToOne)


Pasos para empezar a crear Controllers:

* [ok] Crear paquete nuevo llamado controller en com.restaurantes

* [ok] HelloController (opcional)
  * [ok] Crear una clase java nueva llamada HelloController
  * [ok] Anotar la clase con @Controller
  * [ok] Crear un método público llamado hello que reciba un Model y devuelva String
  * [ok] Anotar el método con @GetMapping("/hello")
  * [ok] Dentro del método, guardar un mensaje en el Model con model.addAttribute("message", "¡Hola, mundo!")
  * [ok] Devolver el nombre del template HTML que vamos a crear: "hello"

* [ok] (opcional) Añadir xmlns:th="http://www.thymeleaf.org" en el HTML para usar Thymeleaf

* [] Crear una clase java nueva llamada RestaurantController
  * [ok] Anotar la clase con @Controller
  * [ok] Crear un constructor que reciba RestaurantRepository y lo guarde en un atributo privado final
  * [ok] Crear un método público llamado findAll que reciba un Model y devuelva String
  * [ok] Anotar el método con @GetMapping("/restaurants")
  * [ok] Dentro del método, usar restaurantRepository.findAll() para obtener la lista de restaurantes y guardarla en el Model con model.addAttribute("restaurants", restaurantRepository.findAll())
  * [ok] Devolver el nombre del template HTML que vamos a crear: "restaurant-list"

* [ok] Crear un template HTML nuevo llamado restaurant-list.html en src/main/resources/templates
  * [ok] Escribir el código HTML básico con una tabla para mostrar los restaurantes
  * [ok] Usar Thymeleaf para iterar sobre la lista de restaurantes y mostrar sus datos en la tabla







* Review
  * Long id
  * String comment
  * Integer rating
  * LocalDate date
  * asociaciones:
  * restaurant (ManyToOne)
  * user (ManyToOne)