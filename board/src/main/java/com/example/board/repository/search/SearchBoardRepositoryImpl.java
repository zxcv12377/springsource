package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.entity.Board;
import com.example.board.entity.QBaseEntity;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);

    }

    @Override
    public Page<Object[]> list(Pageable pageable) {
        log.info("SearchBoard ");

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(member).on(board.member.eq(member));

        // 댓글 개수
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board)).groupBy(reply.board);
        log.info("=====================");
        log.info(query);
        log.info("=====================");
        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        // Sort
        Sort sort = pageable.getSort();
        // foreach 쓴이유는 sort 기준이 여러개 있을 가능성이 있기 때문
        // ex) bno.descending, createdDate.ascending
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder<Board> orderBuilder = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderBuilder.get(prop)));
        });

        // ------------------ 전체 리스트 + Sort 적용 ----------------------

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        // 위 두줄이 실제 페이지 처리하는 구문
        List<Tuple> result = tuple.fetch();
        // 아랫 줄은 전체 갯수
        Long count = tuple.fetchCount();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return new PageImpl(list, pageable, count);
    }

    @Override
    public Object[] getBoardByBno(Long bno) {
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(member).on(board.member.eq(member));
        query.where(board.bno.eq(bno));

        // 댓글 개수
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board)).groupBy(reply.board);

        log.info("=====================");
        log.info(query);
        log.info("=====================");
        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);
        Tuple result = tuple.fetchFirst();

        return result.toArray();
    }

}
