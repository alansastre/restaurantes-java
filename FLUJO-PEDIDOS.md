

## PROCESO DE INICIAR Y FINALIZAR UN PEDIDO EN RESTAURANTE


Como cliente quiero poder iniciar un pedido sobre un restaurante, debo poder añadir platos a mi pedido, quitar platos de mi pedido, y finalizar pedido.


1. restaurant-detail.html (OK)
    * Botón "Iniciar pedido"  /orders/new?restaurantId=1

2. OrderController (OK)
    * @GetMapping("orders/new")   para entrar al formulario  
    * return order-form.html

3. order-form.html (OK)
    * restaurant precargado
    * numero mesa
    * numero de comensales
    * comentarios/sugerencias/alergias
    * Botón Enviar (POST /orders)

4. OrderController
    * @PostMapping("orders")
    * save
    * "redirect:/orders/" + order.getId();

5. order-detail.html
    * Sección con platos Añadibles al pedido
    * En cada plato hay botón de Añadir --> POST /orders/1/lines?dishId=1

6. OrderController
    * @PostMapping("/orders/1/lines")
    * crea un Order Line apuntando al Order y al Dish y lo guarda 
    *  "redirect:/orders/" + order.getId();

7. order-detail.html (NUEVO)
    * Poder modificar cantidad Plato  --> POST /orders/{id}/lines/{id} quantity
    * Poder eliminar un OrderLine --> GET /orders/{id}/lines/{id}/delete (OK)

8. order-detail.html Botón Finalizar apunta GET /orders/{id}/finish

9. OrderController
    * @GetMapping("/orders/{id}/finish")
      * NUEVO: requestparam Double tip
    * setStatus FINISHED
    * recalculate price
    * save 
    *  "redirect:/orders/" + order.getId();


## Añadir pago simulado

No vamos a utilizar una integración con una pasarela de pago real, ya que por ejemplo integrar Stripe implica darse de alta, obtener keys, tener productos en stripe con precios, hacer compra, webhook. 


* Nuevos atributos en Order (pedido) (OK)
  * String cardOwner;
  * String cardNumber;
  * String cardExpirationDate;
  * String cardSecretCode; (este crítico mejor no guardarlo)

* OrderController
   * @PostMapping orders/{id}/finish

* HTML seccion de pago order-detail.html
  *  donde la propina añadir los nuevos campos

* Datos del pago mostrar los datos ofuscados el número tarjeta