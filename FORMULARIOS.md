

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
