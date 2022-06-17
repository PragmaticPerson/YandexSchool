package ru.yandex.goods.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.goods.models.ShopUnit;
import ru.yandex.goods.models.ShopUnitImport;
import ru.yandex.goods.service.ShopUnitService;

import java.util.List;
import java.util.UUID;

@RestController
public class ShopOrientedRESTController {
    private ShopUnitService service;

    @Autowired
    public ShopOrientedRESTController(ShopUnitService service) {
        this.service = service;
    }

    @GetMapping("/nodes/{uuid}")
    public ShopUnit getShopUnitMapping(@PathVariable UUID uuid) {
        return service.getShopUnitWithChildren(uuid).convertToShopUnit();
    }

    @PostMapping("/imports")
    @ResponseStatus(code = HttpStatus.OK)
    public void importShopUnits(@RequestBody List<ShopUnitImport> shopUnitImports) {
        shopUnitImports.forEach(imp -> {
            imp.setUpdateDateToItems();
            imp.getItems().forEach(service::save);
        });
    }
}
