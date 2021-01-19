package com.cicdlectures.menuserver.service;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cicdlectures.menuserver.repository.MenuRepository;
import com.cicdlectures.menuserver.dto.MenuDto;

@Service
public class ListMenuService {

  private final MenuRepository menuRepository;

  @Autowired
  public ListMenuService(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  public List<MenuDto> listMenus() {
    return MenuDto.fromModelIterable(menuRepository.findAll());
  }
}
