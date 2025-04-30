package com.example.mart.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;
import com.example.mart.entity.Delivery;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.DeliveryStatus;
import com.example.mart.entity.constant.OrderStatus;
import com.example.mart.repository.CategoryItemRepository;
import com.example.mart.repository.CategoryRepository;
import com.example.mart.repository.DeliveryRepository;
import com.example.mart.repository.ItemRepository;
import com.example.mart.repository.MemberRepository;
import com.example.mart.repository.OrderItemRepository;
import com.example.mart.repository.OrderRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryItemRepository categoryItemRepository;

    @Test
    public void qureydslTest() {
        // List<Member> list = orderRepository.members();
        // list.forEach(i -> System.out.println(i));

        List<Item> list = orderRepository.items();
        System.out.println(list);
    }

    @Test
    public void testMemberInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .city("서울" + i)
                    .street("724-11" + i)
                    .zipcode("1650" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void testItemInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Item item = Item.builder()
                    .name("item" + i)
                    .price(i * 20000)
                    .stockQuntity(i * 5)
                    .build();
            itemRepository.save(item);
        });
    }

    // 주문하다 : Order + OrderItem insert
    @Test
    public void TestOrder() {

        Order order = Order.builder()
                .member(Member.builder().id(1L).build())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        orderRepository.save(order);

        // 주문과 관련된 상품은 OrderItem 삽입
        OrderItem orderItem = OrderItem.builder()
                .item(itemRepository.findById(2L).get())
                .order(order)
                .orderPrice(39000)
                .count(1)
                .build();

        orderItemRepository.save(orderItem);

        OrderItem orderItem2 = OrderItem.builder()
                .item(itemRepository.findById(3L).get())
                .order(order)
                .orderPrice(45000)
                .count(1)
                .build();

        orderItemRepository.save(orderItem2);
    }

    @Transactional
    @Test
    public void testRead1() {
        Order order = orderRepository.findById(1L).get();
        System.out.println(order);
        System.out.println(order.getMember());
    }

    @Transactional
    @Test
    public void testRead2() {
        Member member = memberRepository.findById(1L).get();
        System.out.println(member.getOrders());
    }

    @Transactional
    @Test
    public void testRead3() {
        OrderItem orderItem = orderItemRepository.findById(1L).get();
        System.out.println(orderItem);
        System.out.println(orderItem.getItem().getName());
        System.out.println(orderItem.getOrder().getMember().getName());
    }

    @Transactional
    @Test
    public void testRead4() {
        Order order = orderRepository.findById(3L).get();
        order.getOrderitems().forEach(item -> System.out.println(item));
    }

    @Test
    public void testDelete1() {
        // memberRepository.deleteById(5L);

        // 주문 상품 취소
        // 주문 취소
        // 멤버 제거
        memberRepository.deleteById(1L);
    }

    @Test
    public void testDelete2() {
        orderItemRepository.deleteById(2L);

        orderRepository.deleteById(2L);

        // 주문 상품 취소
    }

    @Test
    public void testDelete3() {
        // 부모쪽에 cascade 작성
        // 주문 제거(주문 상품 같이 제거)
        orderRepository.deleteById(1L);

    }

    @Commit
    @Transactional
    @Test
    public void testDelete4() {
        Order order = orderRepository.findById(4L).get();
        System.out.println(order.getOrderitems());

        // 첫번째 자식 제거
        order.getOrderitems().remove(0);
        orderRepository.save(order);
    }

    @Test
    public void TestOrder2() {
        // order저장시 orderitem도 같이 저장
        Order order = Order.builder()
                .member(Member.builder().id(1L).build())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        // 주문과 관련된 상품은 OrderItem 삽입
        OrderItem orderItem = OrderItem.builder()
                .item(itemRepository.findById(2L).get())
                .order(order)
                .orderPrice(39000)
                .count(10)
                .build();

        order.getOrderitems().add(orderItem);
        orderRepository.save(order);

    }

    @Test
    public void testDeliveryInsert() {
        Delivery delivery = Delivery.builder()
                .zipcode("15011")
                .city("부산")
                .street("120-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        deliveryRepository.save(delivery);

        Order order = orderRepository.findById(4L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Transactional
    @Test
    public void testDeliveryRead() {
        System.out.println(deliveryRepository.findById(1L));
        Order order = orderRepository.findById(4L).get();
        System.out.println(order.getDelivery().getDeliveryStatus());
    }

    @Transactional
    @Test
    public void testDeliveryRead2() {
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println("주문 조회 : " + delivery.getOrder());
        System.out.println("주문자 조회 : " + delivery.getOrder().getMember());
        System.out.println("주문 아이템 조회 : " + delivery.getOrder().getOrderitems());
    }

    @Test
    public void testDeliveryInsert2() {
        Delivery delivery = Delivery.builder()
                .zipcode("16011")
                .city("서울")
                .street("130-54")
                .deliveryStatus(DeliveryStatus.COMP)
                .build();

        Order order = orderRepository.findById(5L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Test
    public void testDeliveryDelete() {
        orderRepository.deleteById(5L);
    }

    @Test
    public void testCategoryItemInsert1() {
        Category category1 = Category.builder().name("가전제품").build();
        Category category2 = Category.builder().name("식품").build();
        Category category3 = Category.builder().name("생필품").build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        Item item1 = Item.builder().name("TV").price(2500000).stockQuntity(15).build();

        itemRepository.save(item1);

        CategoryItem cItem = CategoryItem.builder().category(category1).item(item1).build();
        categoryItemRepository.save(cItem);

        item1 = Item.builder().name("콩나물").price(1200).stockQuntity(5).build();

        itemRepository.save(item1);

        cItem = CategoryItem.builder().category(category2).item(item1).build();
        categoryItemRepository.save(cItem);

        item1 = Item.builder().name("샴푸").price(12000).stockQuntity(7).build();

        itemRepository.save(item1);

        cItem = CategoryItem.builder().category(category3).item(item1).build();
        categoryItemRepository.save(cItem);
    }

    @Transactional
    @Test
    public void testreadCategroyItem() {
        CategoryItem categoryItem = categoryItemRepository.findById(10L).get();
        System.out.println(categoryItem.getCategory());
        System.out.println(categoryItem.getCategory().getName());
        System.out.println(categoryItem.getItem());
        System.out.println(categoryItem.getItem().getName());

        Category category = categoryRepository.findById(10L).get();
        category.getCategoryItems().forEach(item -> System.out.println(item.getItem()));
    }

    @Test
    public void joinTest() {
        List<Object[]> list = orderRepository.joinTest();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
            Order order = (Order) objects[0];
            Member member = (Member) objects[1];
            OrderItem orderItem = (OrderItem) objects[2];
            System.out.println(order);
            System.out.println(member);
            System.out.println(orderItem);
        }
    }

    @Test
    public void subQueryTest() {
        List<Object[]> list = orderRepository.subQueryTest();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
            Order order = (Order) objects[0];
            Member member = (Member) objects[1];
            OrderItem orderItem = (OrderItem) objects[2];
            Long orderCnt = (Long) objects[3];
            Long orderSum = (Long) objects[4];
            System.out.println(order);
            System.out.println(member);
            System.out.println(orderItem);
            System.out.println(orderCnt);
            System.out.println(orderSum);
        }
    }
}
