

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


* Order (pedido)
  * fecha
  * precioTotal
  * numeroComensales
  * tip propina
  * estado (enum: PENDING, IN_PROGRESS, COMPLETED, CANCELLED)
  * type: enum: DINE_IN, TAKEAWAY, DELIVERY
  * asociaciones:
    * user (ManyToOne)
    * restaurant (ManyToOne)
    * List<Dish> dishes (ManyToMany)
* Review
  * Long id
  * String comment
  * Integer rating
  * LocalDate date
  * asociaciones:
  * restaurant (ManyToOne)
  * user (ManyToOne)