package com.cicdlectures.menuserver.service;

import java.util.List;
import java.util.HashSet;
import java.util.Arrays;

import com.cicdlectures.menuserver.repository.MenuRepository;
import com.cicdlectures.menuserver.repository.DishRepository;
import com.cicdlectures.menuserver.dto.MenuDto;
import com.cicdlectures.menuserver.dto.DishDto;
import com.cicdlectures.menuserver.model.Menu;
import com.cicdlectures.menuserver.model.Dish;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateMenuServiceTests {

  private CreateMenuService subject;

  private MenuRepository menuRepository;

  private DishRepository dishRepository;

  @BeforeEach
  public void init() {
    menuRepository = mock(MenuRepository.class);
    dishRepository = mock(DishRepository.class);
    subject = new CreateMenuService(menuRepository, dishRepository);
  }

  @Test
  public void savesANewMenu() {
    MenuDto newMenu = new MenuDto(null, "Christmas menu",
        new HashSet<DishDto>(Arrays.asList(new DishDto(null, "Turkey"), new DishDto(null, "Pecan Pie"))));

    Menu returnedMenu = new Menu(Long.valueOf(1), "Christmas menu", new HashSet<Dish>(
        Arrays.asList(new Dish(Long.valueOf(2), "Turkey", null), new Dish(Long.valueOf(3), "Pecan Pie", null))));

    when(menuRepository.save(any(Menu.class))).thenReturn(returnedMenu);

    MenuDto got = subject.createMenu(newMenu);

    ArgumentCaptor<Menu> savedMenu = ArgumentCaptor.forClass(Menu.class);

    verify(menuRepository, times(1)).save(savedMenu.capture());

    // TODO: I'll write asserts later lol. Tests are useless anyway, nobody runs them.
    // It's mostly the same thing than the next test.
    // PROVE ME WRONG.
    assertTrue(false);
  }

  @Test
  public void reusesExistingMenu() {
    Dish existingDish = new Dish(Long.valueOf(33), "Tiramisu", null);

    MenuDto newMenu = new MenuDto(null, "Christmas menu",
        new HashSet<DishDto>(Arrays.asList(new DishDto(null, "Turkey"), new DishDto(null, "Tiramisu"))));

    Menu returnedMenu = new Menu(Long.valueOf(1), "Christmas menu", new HashSet<Dish>(
        Arrays.asList(new Dish(Long.valueOf(2), "Turkey", null), new Dish(Long.valueOf(33), "Tiramisu", null))));

    when(menuRepository.save(any(Menu.class))).thenReturn(returnedMenu);
    when(dishRepository.findByName("Tiramisu")).thenReturn(existingDish);

    MenuDto got = subject.createMenu(newMenu);

    ArgumentCaptor<Menu> savedMenu = ArgumentCaptor.forClass(Menu.class);

    verify(menuRepository, times(2)).save(savedMenu.capture());

    assertEquals(savedMenu.getValue().getName(), newMenu.getName());
    assertTrue(savedMenu.getValue().getDishes().contains(existingDish));

    assertEquals(MenuDto.fromModel(returnedMenu), got);
  }
}
