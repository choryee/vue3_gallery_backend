package org.africalib.gallery.backend.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.africalib.gallery.backend.entity.Item;
import org.africalib.gallery.backend.entity.Member;
import org.africalib.gallery.backend.repository.ItemRepository;
import org.africalib.gallery.backend.repository.MemberRepository;
import org.africalib.gallery.backend.service.JwtService;
import org.africalib.gallery.backend.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import jakarta.servlet.http.Cookie;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JwtService jwtService;

    @PostMapping("/api/account/join")
    public ResponseEntity join(@RequestBody Member member, HttpServletResponse res){
//        int insertMember = memberRepository.insertMember(param.get("email"), param.get("password") );
//        System.out.println("insertMember  >> "+  insertMember);
//        if(insertMember == 1){
//            res.addHeader("result", "success");
//        }

        Member member1 = memberRepository.save(member);
        System.out.println("member1  >> "+  member1);
        if(member1 !=null){
            res.addHeader("result", "No Korean"); // Header에 넣어, 클에 보낸다.
        }
        return new ResponseEntity("하하 성공함", HttpStatus.OK);
    }

    @GetMapping("/api/account/info")
    public ResponseEntity info(@RequestBody Map<String, String> param, HttpServletResponse res){
        Member member =  memberRepository.findByEmailAndPassword(param.get("email"), param.get("password"));
       System.out.println(" member >> "+  member);
        return new ResponseEntity(member, HttpStatus.OK);
    }

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
        if(member !=null){ // member 있을때,
            // JwtService jwtService =new JwtServiceImpl(); @Autowired 해서 불필요함.
            int id = member.getId();
          //  String token = jwtService.getToken("id", id);
            String token = String.valueOf(id)+"abcd";


            //토큰을 쿠키로 만들어, 화면에 res로 보내줌. 쿠키를 화면에서 생성 안 할려고.
            jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("token", token);
            cookie.setHttpOnly(true);  // 자바스크립트로는 접근 금지
            cookie.setPath("/");

            res.addCookie(cookie);
           // return new ResponseEntity<>(id, HttpStatus.OK); // 로그인호출한 곳으로, id로 만든 토큰 만든후 보내줌.
            return  new ResponseEntity(member, HttpStatus.OK);
        } // if

       //return 0;
        throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token){
        Claims claims = jwtService.getClaims(token);
        if(claims !=null){
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
