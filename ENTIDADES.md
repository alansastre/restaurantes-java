

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



* Review
  * Long id
  * String comment
  * Integer rating
  * LocalDate date
  * asociaciones:
  * restaurant (ManyToOne)
  * user (ManyToOne)