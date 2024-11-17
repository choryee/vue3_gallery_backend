package org.africalib.gallery.backend.controller;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.africalib.gallery.backend.dto.OrderDto;
import org.africalib.gallery.backend.entity.Cart;
import org.africalib.gallery.backend.entity.Item;
import org.africalib.gallery.backend.entity.Order;
import org.africalib.gallery.backend.repository.CartRepository;
import org.africalib.gallery.backend.repository.ItemRepository;
import org.africalib.gallery.backend.repository.OrderRepository;
import org.africalib.gallery.backend.service.JwtService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;


    @GetMapping("/api/orders")
    public ResponseEntity getOrders(
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        List<Order> lists = orderRepository.findByMemberIdOrderByIdDesc(memberId); //Spring Data JPA는 메서드 이름을 분석하여 쿼리를 생성합니다. 다음과 같은 규칙을 따릅니다:

        return new ResponseEntity(lists, HttpStatus.OK);

    }


    @Transactional // 034950
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(
            @RequestBody OrderDto dto,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Order newOrder =new Order();
        int memberId = jwtService.getId(token);
        newOrder.setMemberId(memberId);
        newOrder.setName(dto.getName());
        newOrder.setAddress(dto.getAddress());
        newOrder.setCardNumber(dto.getCardNumber());
        newOrder.setPayment(dto.getPayment());
        newOrder.setItems(dto.getItems());
        newOrder.setReg_date(dto.getReg_date());

        orderRepository.save(newOrder );

        // 주문버튼후에는, 카트 비워주기 <-- @Transactional 없으면, 서버 에러남.
        cartRepository.deleteByMemberId(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
