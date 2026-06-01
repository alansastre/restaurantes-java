
## Funcionalidad Favoritos

Un usuario quiere marcar como favorito un restaurante o un plato y poder visualizarlos en su perfil.

* model/Favorite.java
  * id
  * createdAt
  * User user
  * Restaurant restaurant
  * Dish dish

* repository/FavoriteRepository.java
  * findByUser_IdAndRestaurantId
  * findByUser_IdAndDishId

* service/FavoriteService.java
  * toggleRestaurant
  * toggleDish
  * findFavoriteRestaurants
  * findFavoriteDishes

* controller/FavoriteController.java
  * toggle

* fragments/favorite-button.html
* Editar:
  * restaurants/restaurant-list.html añadir botón favorito
  * restaurants/restaurant-detail.html añadir botón favorito
  * dishes/dish-list.html añadir botón favorito
  * dishes/dish-detail.html añadir botón favorito

* GlobalModelAttributes crear un Controller Advice que expone datos que se pueden usar en todas las pantallas

* user-detail.html mostrar también los favorite de ese usuario