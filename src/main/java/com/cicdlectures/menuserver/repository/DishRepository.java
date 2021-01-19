package com.cicdlectures.menuserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.cicdlectures.menuserver.model.Dish;

public interface DishRepository extends CrudRepository<Dish, Long> {
  Dish findByName(String name);
}
