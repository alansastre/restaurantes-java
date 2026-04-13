package com.restaurantes.repository;

import com.restaurantes.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
//    @Transactional
//    @Modifying
//    @Query("delete from Restaurant r where r.name = ?1")
//    void deleteByNameTodoGuay(String name);

}