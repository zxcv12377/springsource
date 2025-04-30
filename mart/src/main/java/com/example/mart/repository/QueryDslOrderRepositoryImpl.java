package com.example.mart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.QItem;
import com.example.mart.entity.QMember;
import com.example.mart.entity.QOrder;
import com.example.mart.entity.QOrderItem;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class QueryDslOrderRepositoryImpl extends QuerydslRepositorySupport implements QueryDslOrderRepository {

    public QueryDslOrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Member> members() {

        QMember member = QMember.member;
        JPQLQuery<Member> query = from(member);
        query.where(member.name.eq("user1")).orderBy(member.name.desc());
        query.select(member);

        // fetch() : 리스트 조회, 데이터 없는 경우 빈리스트 반환
        // fetchFirst() : limit(1), fetchOne()
        // fetchOne() : 결과가 없으면 null, 둘 이상이면 NonUniqueException
        // fetchResults() : 페이징 정보 포함, total count 쿼리 추가 실행
        // fetchCount() : count 쿼리로 변경해서 count 수 조회
        List<Member> list = query.fetch();

        return list;
    }

    @Override
    public List<Item> items() {
        QItem item = QItem.item;
        JPQLQuery<Item> query = from(item);
        query.where(item.name.contains("TV").and(item.price.gt(200000)));
        query.select(item);

        return query.fetch();
    }

    @Override
    public List<Object[]> joinTest() {
        // order o join member m on o.member_id = m.member_id
        QOrder order = QOrder.order;
        QMember member = QMember.member
        QOrderItem orderItem = QOrderItem.orderItem;

        JPQLQuery<Order> query = from(order);
        query.join(member).on(order.member.eq(member));
        query.join(orderItem).on(order.eq(orderItem.order));

        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem);
        List<Tuple> result = tuple.fetch();

        List<Object[]> list = result.stream().map(t -> t.toString()).collect(Collectors.toList());
        
        return list;
    }

    @Override
    public List<Object[]> subQueryTest() {

        QOrder order = QOrder.order;
        QMember member = QMember.member
        QOrderItem orderItem = QOrderItem.orderItem;

        JPQLQuery<Order> query = from(order);
        query.join(member).on(order.member.eq(member))
        .join(orderItem).on(order.eq(orderItem.order));
        
        // 주문 건수
        JPQLQuery<Long> orderCnt = JPAExpressions.select(orderItem.order.count())
        .from(orderItem)
        .where(orderItem.order.eq(order)).groupBy(orderItem.order);

        // 주문 총 금액
        JPQLQuery<Integer> orderSum = JPAExpressions.select(orderItem.orderPrice.sum())
        .from(orderItem)
        .where(orderItem.order.eq(order)).groupBy(orderItem.order);

        JPQLQuery<Tuple> tuple =  query.select(order, member,orderItem, orderCnt, orderSum);
        List<Tuple> result = tuple.fetch();

        List<Object[]> list = result.stream().map(t -> t.toString()).collect(Collectors.toList());
        
        return list;
    }

}
