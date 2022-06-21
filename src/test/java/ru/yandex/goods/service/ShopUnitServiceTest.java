package ru.yandex.goods.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.goods.dao.ShopUnitRepository;
import ru.yandex.goods.exceptions.ValidationFailedException;
import ru.yandex.goods.models.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShopUnitServiceTest {

    @InjectMocks
    private ShopUnitService shopUnitService;

    @Mock
    private ShopUnitRepository shopUnitRepository;

    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final UUID ROOT_UUID = UUID.fromString("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
    private static final UUID NODE_UUID = UUID.fromString("d515e43f-f3f6-4471-bb77-6b455017a2d2");
    private static final UUID firstUUID = UUID.fromString("863e1a7a-1304-42ae-943b-179184c077e3");
    private static final UUID secondUUID = UUID.fromString("b1d8fd7d-2ae3-47d5-b2f9-0f094af800d4");

    private static ShopUnit ROOT;
    private static ShopUnitDB ROOT_DB;

    @BeforeEach
    void setUp() {

        ROOT_DB = new ShopUnitDB(ROOT_UUID, "Root", null, ShopUnitType.CATEGORY,
                List.of(
                        new ShopUnitPrice(
                                1, ROOT_UUID, LocalDateTime.parse("2022-02-01T12:00:00.000"), 2500
                        ),
                        new ShopUnitPrice(
                                2, ROOT_UUID, LocalDateTime.parse("2022-02-04T12:00:00.000"), 5000
                        )
                ), null
        );

        List<ShopUnitDB> childrenOfRoot = List.of(
                new ShopUnitDB(NODE_UUID, "Catalog", ROOT_UUID, ShopUnitType.CATEGORY, List.of(
                        new ShopUnitPrice(5, firstUUID,
                                LocalDateTime.parse("2022-02-01T12:00:00.000"), 2500),
                        new ShopUnitPrice(7, secondUUID,
                                LocalDateTime.parse("2022-02-02T12:00:00.000"), 5000)),
                        null)
        );

        List<ShopUnitDB> childrenOfNode = List.of(
                new ShopUnitDB(firstUUID, "JPhone 13", NODE_UUID,
                        ShopUnitType.OFFER, List.of(new ShopUnitPrice(4, firstUUID,
                        LocalDateTime.parse("2022-02-01T12:00:00.000"), 2500)), null),
                new ShopUnitDB(secondUUID, "Xomiа Readme 10", NODE_UUID,
                        ShopUnitType.OFFER, List.of(new ShopUnitPrice(6, secondUUID,
                        LocalDateTime.parse("2022-02-02T12:00:00.000"), 5000)), null)
        );

        lenient().when(shopUnitRepository.findById(ROOT_UUID)).thenReturn(Optional.of(ROOT_DB));
        lenient().when(shopUnitRepository.findById(NODE_UUID)).thenReturn(Optional.of(childrenOfRoot.get(0)));
        lenient().when(shopUnitRepository.findById(firstUUID)).thenReturn(Optional.of(childrenOfNode.get(0)));
        lenient().when(shopUnitRepository.findById(secondUUID)).thenReturn(Optional.of(childrenOfNode.get(1)));

        lenient().when(shopUnitRepository.findAllChildrenByParentId(ROOT_UUID)).thenReturn(childrenOfRoot);
        lenient().when(shopUnitRepository.findAllChildrenByParentId(NODE_UUID)).thenReturn(childrenOfNode);
    }

    @Test
    void getShopUnitWithChildrenTest() {
        ShopUnit result = shopUnitService.getShopUnitWithChildren(ROOT_UUID);
        ShopUnit expected = new ShopUnit(ROOT_UUID, "Root", LocalDateTime.parse("2022-02-04T12:00:00.000"),
                null, ShopUnitType.CATEGORY, 5000, Set.of(
                new ShopUnit(NODE_UUID, "Catalog", LocalDateTime.parse("2022-02-02T12:00:00.000"),
                        ROOT_UUID, ShopUnitType.CATEGORY, 5000, Set.of(
                        new ShopUnit(firstUUID, "JPhone 13", LocalDateTime.parse("2022-02-01T12:00:00.000"),
                                NODE_UUID, ShopUnitType.OFFER, 2500, null),
                        new ShopUnit(secondUUID, "Xomiа Readme 10", LocalDateTime.parse("2022-02-02T12:00:00.000"),
                                NODE_UUID, ShopUnitType.OFFER, 5000, null)
                ))
        ));

        assertEquals(expected, result);
    }

    @Test
    void getStatisticWithCorrectDatesTest() {
        when(shopUnitRepository.findByIdAndDate(eq(ROOT_UUID), any(), any())).thenReturn(ROOT_DB);

        List<ShopUnitStatistic> result = shopUnitService.getShopUnitStatistic(ROOT_UUID,
                new StatisticDates(LocalDateTime.parse("2022-02-01T00:00:00.000"),
                        LocalDateTime.parse("2022-02-02T12:00:00.000")));

        List<ShopUnitStatistic> expected = List.of(
                new ShopUnitStatistic(ROOT_UUID, "Root", LocalDateTime.parse("2022-02-01T12:00:00.000"),
                        null, ShopUnitType.CATEGORY, 2500),
                new ShopUnitStatistic(ROOT_UUID, "Root", LocalDateTime.parse("2022-02-04T12:00:00.000"),
                        null, ShopUnitType.CATEGORY, 5000)
        );

        assertEquals(expected, result);
    }

    @Test
    void getStatisticWhenWrongDatesTest() {
        assertThrows(ValidationFailedException.class, () ->
                shopUnitService.getShopUnitStatistic(ROOT_UUID,
                        new StatisticDates(LocalDateTime.parse("2022-02-02T12:00:00.000"),
                                LocalDateTime.parse("2022-02-01T12:00:00.000"))));
    }

    @Test
    void getStatisticWhenNullDatesTest() {
        assertThrows(ValidationFailedException.class, () ->
                shopUnitService.getShopUnitStatistic(ROOT_UUID,
                        new StatisticDates(null,
                                LocalDateTime.parse("2022-02-01T12:00:00.000"))));

        List<ShopUnitStatistic> result = shopUnitService.getShopUnitStatistic(ROOT_UUID, new StatisticDates(null, null));
        List<ShopUnitStatistic> expected = List.of(
                new ShopUnitStatistic(ROOT_UUID, "Root", LocalDateTime.parse("2022-02-01T12:00:00.000"),
                        null, ShopUnitType.CATEGORY, 2500),
                new ShopUnitStatistic(ROOT_UUID, "Root", LocalDateTime.parse("2022-02-04T12:00:00.000"),
                        null, ShopUnitType.CATEGORY, 5000)
        );

        assertEquals(result, expected);
    }
}
