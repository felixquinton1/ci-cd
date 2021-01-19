package com.cicdlectures.menuserver.controller;

import static org.assertj.core.api.Assertions.*;

import java.net.URL;
import java.util.List;
import java.util.HashSet;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cicdlectures.menuserver.repository.DishRepository;
import com.cicdlectures.menuserver.repository.MenuRepository;
import com.cicdlectures.menuserver.model.Dish;
import com.cicdlectures.menuserver.model.Menu;
import com.cicdlectures.menuserver.dto.MenuDto;
import com.cicdlectures.menuserver.dto.DishDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MenuControllerIT {

  @LocalServerPort
  private int port;

  private URL url;

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private DishRepository dishRepository;

  @Autowired
  private TestRestTemplate template;

  private final List<Menu> existingMenus = Arrays.asList(
      new Menu(null, "Christmas menu", new HashSet<>(Arrays.asList(new Dish(null, "Turkey", null), new Dish(null, "Pecan Pie", null)))),
      new Menu(null, "New year's eve menu", new HashSet<>(Arrays.asList(new Dish(null, "Potatos", null), new Dish(null, "Tiramisu", null)))));

  @BeforeEach
  public void setUp() throws Exception {
    url = new URL("http://localhost:" + port + "/menus");
  }

  @BeforeEach
  public void initDataset() {
    for (Menu menu : existingMenus) {
      menuRepository.save(menu);
    }
  }

  @Test
  public void listsExistingMenus() throws Exception {
    MenuDto[] wantMenus = {
        new MenuDto(Long.valueOf(1), "Christmas menu",
            new HashSet<DishDto>(
                Arrays.asList(new DishDto(Long.valueOf(1), "Turkey"), new DishDto(Long.valueOf(2), "Pecan Pie")))),
        new MenuDto(Long.valueOf(2), "New year's eve menu", new HashSet<DishDto>(
            Arrays.asList(new DishDto(Long.valueOf(3), "Potatos"), new DishDto(Long.valueOf(4), "Tiramisu")))) };

    ResponseEntity<MenuDto[]> response = this.template.getForEntity(url.toString(), MenuDto[].class);

    MenuDto[] gotMenus = response.getBody();

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertArrayEquals(wantMenus, gotMenus);
  }

  @Test
  public void createsNewMenu() throws Exception {
    MenuDto wantMenu = new MenuDto(Long.valueOf(3), "Valentines'day menu", new HashSet<DishDto>(
        Arrays.asList(new DishDto(Long.valueOf(1), "Turkey"), new DishDto(Long.valueOf(2), "Pecan Pie"))));

    MenuDto newMenu = new MenuDto(null, "Valentines'day menu",
        new HashSet<DishDto>(Arrays.asList(new DishDto(null, "Turkey"), new DishDto(null, "Pecan Pie"))));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<MenuDto> request = new HttpEntity<>(newMenu, headers);

    ResponseEntity<MenuDto> response = this.template.postForEntity(url.toString(), request, MenuDto.class);

    // LOOOOOOL. This is fine.
    assertTrue(false);
  }
}
