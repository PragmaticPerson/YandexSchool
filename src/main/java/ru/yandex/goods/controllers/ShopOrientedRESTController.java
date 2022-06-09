package ru.yandex.goods.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.goods.models.ShopUnit;
import ru.yandex.goods.models.ShopUnitType;

import java.time.LocalDateTime;

@RestController
public class ShopOrientedRESTController {

    @GetMapping("/get")
    public ShopUnit getShopUnitMapping() {
        LocalDateTime date = LocalDateTime.now();
        return new ShopUnit("uuid", "name",date, null, ShopUnitType.CATEGORY, 0, null);
    }
}
