package com.jojoldu.book.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됩니다.
 * 앞의 경로는 src/main/resources/templates로 , 뒤의 파일 확장자는 .mustache가 붙는 것입니다.
 * 즉 여기선 "index"를 반환하므로 src/main/resources/templates/index.mustache로 전환되어 View Resolver가 처리하게 됩니다.
 *
 */
@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
