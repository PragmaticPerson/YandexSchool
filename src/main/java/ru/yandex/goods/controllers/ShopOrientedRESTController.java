package ru.yandex.goods.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.goods.models.ShopUnit;
import ru.yandex.goods.models.ShopUnitImport;
import ru.yandex.goods.models.StatisticDates;
import ru.yandex.goods.service.ShopUnitService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class ShopOrientedRESTController {
    private ShopUnitService service;
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Autowired
    public ShopOrientedRESTController(ShopUnitService service) {
        this.service = service;
    }

    @GetMapping("/nodes/{uuid}")
    public ShopUnit getShopUnitWithChildren(@PathVariable UUID uuid) {
        return service.getShopUnitWithChildren(uuid);
    }

    @PostMapping("/imports")
    @ResponseStatus(code = HttpStatus.OK)
    public void importShopUnits(@RequestBody List<ShopUnitImport> shopUnitImports) {
        shopUnitImports.forEach(imp -> {
            imp.setUpdateDateToItems();
            imp.getItems().forEach(service::save);
        });
    }

    @GetMapping("/nodes/{uuid}/statistic")
    public List<ShopUnit> getShopUnitStatistic(
            @PathVariable UUID uuid,
            @RequestParam @Nullable @DateTimeFormat(pattern = FORMAT) LocalDateTime dateStart,
            @RequestParam @Nullable @DateTimeFormat(pattern = FORMAT) LocalDateTime dateEnd) {
        return service.getShopUnitStatistic(uuid, new StatisticDates(dateStart, dateEnd));
    }
}
