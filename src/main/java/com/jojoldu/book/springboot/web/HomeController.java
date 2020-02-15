package com.jojoldu.book.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*@RestController : 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 줍니다.
* @GetMapping : HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 줍니다.
* 이전까지의 요청 : @RequestMapping(method= RequestMethod.GET) */
@RestController
public class HomeController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
