

* Restaurant
  * Long id 
  * String name
  * Double averagePrice
  * Boolean active
  * FoodType category (enum)
  * LocalDate startDate
  * Integer numberEmployees

* Employee
  * Long id
  * String firstName
  * String lastName
  * String dni
  * Integer age
  * Restaurant restaurant (ManyToOne) (NUEVO)

* FoodType (enum)
  * SPANISH
  * JAPANESE

Próximamente:
* Dish ( plato, bebida, vino, postre, etc)

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