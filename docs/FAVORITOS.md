
## Funcionalidad Favoritos

Un usuario quiere marcar como favorito un restaurante o un plato y poder visualizarlos en su perfil.

* model/Favorite.java (OK)
  * id
  * createdAt
  * User user
  * Restaurant restaurant
  * Dish dish

* repository/FavoriteRepository.java (50 %)
  * findByUser_IdAndRestaurantIsNotNull (OK)
  * findByUser_IdAndDishIsNotNull (OK)
  * findByUser_IdAndRestaurantId (OK)
  * findByUser_IdAndDishId

* service/FavoriteService.java
  * toggleRestaurant (OK)
  * toggleDish
  * findFavoriteRestaurants (OK)
  * findFavoriteDishes (OK)

* controller/FavoriteController.java
  * toggle (OK)

* fragments/favorite-button.html
* Editar:
  * restaurants/restaurant-list.html añadir botón favorito
  * restaurants/restaurant-detail.html añadir botón favorito (OK)
  * dishes/dish-list.html añadir botón favorito
  * dishes/dish-detail.html añadir botón favorito

* GlobalModelAttributes crear un Controller Advice que expone datos que se pueden usar en todas las pantallas

* user-detail.html mostrar también los favorite de ese usuario