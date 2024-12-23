package org.africalib.gallery.backend.controller;

import org.africalib.gallery.backend.entity.Cart;
import org.africalib.gallery.backend.entity.Item;
import org.africalib.gallery.backend.entity.Member;
import org.africalib.gallery.backend.repository.CartRepository;
import org.africalib.gallery.backend.repository.ItemRepository;
import org.africalib.gallery.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    JwtService jwtService;

    //jwt구현이후, 회원,item등 서버로 요청 보낼때는 서버에서 만든 토큰을 서버에서 다시 받아 확인후, 그 토큰에서
    //회원id를 꺼내와서 사용함.

    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(@CookieValue(value = "token", required = false) String token){
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId); // memberId가 가진 카트 리스트

        List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList(); // 카트 itemId만 가져오기.
        List<Item> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity(items, HttpStatus.OK);
    }

    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity removeCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        cartRepository.delete(cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
