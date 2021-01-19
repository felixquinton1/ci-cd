package com.cicdlectures.menuserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.cicdlectures.menuserver.model.Menu;

public interface MenuRepository extends CrudRepository<Menu, Long> {
}
