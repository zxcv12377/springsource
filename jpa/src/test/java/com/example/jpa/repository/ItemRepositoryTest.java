package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.QItem;
import com.example.jpa.entity.Item.ItemSellStatus;
import com.querydsl.core.BooleanBuilder;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void itemInsert() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Item item = Item.builder()
                    .itemNm("item" + i)
                    .price(i * 2000L)
                    .stockNumber(i + 10L)
                    .itemDetail("Item Detail" + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();

            itemRepository.save(item);
        });
    }

    @Test
    public void queryTest() {
        List<Object[]> objs = itemRepository.aggreate();
        for (Object[] obj : objs) {
            System.out.println(Arrays.toString(obj));
            System.out.println("아이템 수 : " + obj[4]);
            System.out.println("아이템 가격 합 : " + obj[0]);
            System.out.println("아이템 가격 평균 : " + obj[1]);
            System.out.println("아이템 가격 최대 : " + obj[2]);
            System.out.println("아이템 가격 최소 : " + obj[3]);
        }

    }

    @Test
    public void queryDslTest() {
        QItem item = QItem.item;
        // System.out.println(itemRepository.findAll(item.itemNm.eq("item2")));

        // System.out.println(itemRepository.findAll(item.itemNm.startsWith("item2")));
        // System.out.println(itemRepository.findAll(item.itemNm.endsWith("item2")));
        // System.out.println(itemRepository.findAll(item.itemNm.contains("item2")));
        // System.out.println(itemRepository.findAll(item.itemNm.eq("item2").and(item.price.gt(1000L))));
        // System.out.println(
        // itemRepository.findAll(item.itemNm.eq("item2").and(item.price.goe(1000L))));

        // System.out.println(itemRepository
        // .findAll(item.itemNm.contains("item2").or(item.itemSellStatus.eq(ItemSellStatus.SOLD_OUT))));

        // System.out.println(itemRepository.findAll(item.stockNumber.goe(30L)));

        // System.out.println(itemRepository.findAll(item.price.lt(35000L)));

        // 조건 : BooleanBuilder
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(item.itemNm.eq("item2"));
        builder.and(item.price.gt(1000L));
        System.out.println(itemRepository.findAll(builder));

    }

}
