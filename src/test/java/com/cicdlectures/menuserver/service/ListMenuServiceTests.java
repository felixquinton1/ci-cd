package com.cicdlectures.menuserver.service;

import java.util.HashSet;
import java.util.Arrays;
import java.util.List;

import com.cicdlectures.menuserver.repository.MenuRepository;
import com.cicdlectures.menuserver.repository.DishRepository;
import com.cicdlectures.menuserver.dto.MenuDto;
import com.cicdlectures.menuserver.dto.DishDto;
import com.cicdlectures.menuserver.model.Menu;
import com.cicdlectures.menuserver.model.Dish;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListMenuServiceTests {

  private ListMenuService subject;

  private MenuRepository menuRepository;

  @BeforeEach
  public void init() {
    menuRepository = mock(MenuRepository.class);
    subject = new ListMenuService(menuRepository);
  }

  @Test
  public void listsKnownMenus() {
    Iterable<Menu> existingMenus = Arrays.asList(
      new Menu(Long.valueOf(1), "Christmas menu", new HashSet<>(Arrays.asList(new Dish(Long.valueOf(1), "Turkey", null), new Dish(Long.valueOf(2), "Pecan Pie", null)))),
      new Menu(Long.valueOf(1), "New year's eve menu", new HashSet<>(Arrays.asList(new Dish(Long.valueOf(3), "Potatos", null), new Dish(Long.valueOf(4), "Tiramisu", null)))));

    when(menuRepository.findAll()).thenReturn(existingMenus);

    List<MenuDto> got = subject.listMenus();

    assertEquals(MenuDto.fromModelIterable(existingMenus), got);
  }
}

