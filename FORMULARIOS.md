
## FORMULARIOS 

Usamos formularios para poder enviar datos a los controladores. Normalmente se usan para crear/editar una entidad en base de datos.


Restaurantes:

1. RestaurantController
   * ENTRAR A CREAR: `@GetMapping("restaurants/new")`
   * ENTRAR A EDITAR: `@GetMapping("restaurants/edit/{id}")`
2. restaurant-form.html
   * `<form th:action="@{/restaurants} th:object="${restaurant}">`
     * `<input th:field="*{name}">`
     * `<button type="submit">Guardar</button>`
3. RestaurantController
   * `@PostMapping("restaurants")`
   * `@ModelAttribute Restaurant restaurant`


Dish: 

1. DishController
   * ENTRAR A CREAR:  `@GetMapping("dishes/new")`
   * ENTRAR A EDITAR: `@GetMapping("dishes/edit/{id}")`
2. dish-form.html
    * `<form th:action="@{/dishes} th:object="${dish}">`
        * `<input type="text" th:field="*{title}">`
        * `<button type="submit">Guardar</button>`
3. DishController
    * `@PostMapping("dishes")`
    * `@ModelAttribute Dish dish`

Review:

1. ReviewController
   * ENTRAR A CREAR:  `@GetMapping("reviews/new")` restaurantId, dishId
   * ENTRAR A EDITAR: `@GetMapping("reviews/edit/{id}")`
2. review-form.html
3. ReviewController
   * `@PostMapping("reviews")`
   * `@ModelAttribute Review review`

   
Order y OrderLine

1. OrderController
   * Gestionar pedido Order:
     * @GetMapping("orders")
     * @GetMapping("orders/{id}")
     * @GetMapping("orders/new")
     * @PostMapping("orders")
     * @GetMapping("orders/{id}/finish")
   * Gestionar líneas pedido OrderLine;
     * @PostMapping("orders/{id}/lines")
     * @GetMapping("orders/{orderId}/lines/{lineId}/delete")
     * @PostMapping("orders/{orderId}/lines/{lineId}")

2. order-form.html
3. order-detail.html



## GRUPO 2:

Purchase y PurchaseLine

navbar.html botón Iniciar compra

product-detail.html   Añadir al carrito

1. PurchaseController
   * Gestionar pedido Purchase:
      * @GetMapping("purchases")
      * @GetMapping("purchases/{id}")
      * @GetMapping("purchases/new")
      * @PostMapping("purchases")
      * @GetMapping("purchases/{id}/finish")
   * Gestionar líneas compra PurchaseLine;
      * @PostMapping("purchases/{id}/lines")
      * @GetMapping("purchases/{orderId}/lines/{lineId}/delete")
      * @PostMapping("purchases/{orderId}/lines/{lineId}")

2. purchase-form.html
3. purchase-detail.html


## GRUPO 1:

1. movie-detail.html
   * sessionRepo.findByMovieId

2. session-detail.html    /sessions/1
   * room
   * movie
   * opcion 1: 
     * ticketRepository.findBySessionIdAndPurchaseTimeIsNull mostrar como columnas con botón de Comprar (similar a order-detail muestra dishes)
   * opción 2:
      * ticketRepository.findBySessionId 
      * if ticket.purchaseTime == null  entonces mostrr botón verde de Comprar
      * if ticket.purchaseTime != null  entonces mostrar badge Vendido gris/rojo


3. ticket-detail.html  /tickets/1
   * si estado distinto de FINISHED entonces dejar añadir entidad Food osea columnas de comida con botón Añadir
   * entity Food asociada @ManyToOne a Ticket ticket