package com.example.relation.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.sports.Locker;
import com.example.relation.entity.sports.SportsMember;
import com.example.relation.repository.sports.LockerRepository;
import com.example.relation.repository.sports.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Test
    public void testInsert() {

        IntStream.range(1, 6).forEach(i -> {
            Locker locker = Locker.builder()
                    .name("locker" + i)
                    .build();
            lockerRepository.save(locker);

        });

        LongStream.range(1, 6).forEach(i -> {
            SportsMember member = SportsMember.builder()
                    .locker(Locker.builder().id(i).build())
                    .name("member" + i)
                    .build();
            sportsMemberRepository.save(member);

        });
    }

    @Test
    public void testRead1() {
        System.out.println(lockerRepository.findById(1L).get());
        System.out.println(sportsMemberRepository.findById(1L).get());
    }

    @Transactional
    @Test
    public void testRead2() {
        SportsMember sportsMember = sportsMemberRepository.findById(1L).get();

        System.out.println(sportsMember);
        System.out.println(sportsMember.getLocker());

    }

    @Test
    public void testUpdate() {
        SportsMember sportsMember = sportsMemberRepository.findById(3L).get();
        sportsMember.setName("홍길동");
        sportsMemberRepository.save(sportsMember);
    }

    @Test
    public void testDelete() {
        sportsMemberRepository.deleteById(5L);
    }

    @Test
    public void testDelete2() {
        // 무결성 제약조건
        // lockerRepository.deleteById(4L);
        SportsMember sportsMember = sportsMemberRepository.findById(4L).get();
        Locker locker = lockerRepository.findById(5L).get();
        sportsMember.setLocker(locker);
        sportsMemberRepository.save(sportsMember);
        lockerRepository.deleteById(4L);
    }

    @Test
    public void testRead3() {
        Locker locker = lockerRepository.findById(1L).get();
        System.out.println(locker);
        System.out.println(locker.getSportsMember());
    }
}
