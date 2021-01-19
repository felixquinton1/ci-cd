package com.cicdlectures.menuserver.dto;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import com.cicdlectures.menuserver.model.Menu;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class MenuDto {

  private Long id;

  private String name;

  private Set<DishDto> dishes;

  public MenuDto(Long id, String name, Set<DishDto> dishes) {
    this.id = id;
    this.name = name;
    this.dishes = dishes;
  }

  public static List<MenuDto> fromModelIterable(Iterable<Menu> models) {
    List<MenuDto> menus = new ArrayList<>();

    for (Menu model : models) {
      menus.add(MenuDto.fromModel(model));
    }

    return menus;
  }

  public static MenuDto fromModel(Menu menu) {
    return new MenuDto()
      .setId(menu.getId())
      .setName(menu.getName())
      .setDishes(DishDto.fromModelSet(menu.getDishes()));
  }
}
