package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/runTest")
    public String runTest() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        HttpHeaders headers = new HttpHeaders();
                        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                        map.add("name", "xiaoming");
                        map.add("age", "123456");
                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                        HttpEntity<MultiValueMap<String, Object>> param = new HttpEntity<>(map, headers);
                        ResponseEntity<String> response = restTemplate.postForEntity("http://127.0.0.1:8080/postTest", param, String.class);
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        HashMap<String, String> objectObjectHashMap = new HashMap<>();
                        objectObjectHashMap.put("a", "1");
                        objectObjectHashMap.put("b", "1");
                        ResponseEntity<String> response = restTemplate.getForEntity("http://127.0.0.1:8080/getParams?a={a}&b={b}", String.class, objectObjectHashMap);
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return "run success";
    }


    @GetMapping("/getParams")
    public String getParams(String a, int b) {
        return "get success";
    }

    @GetMapping("/getParams1")
    public String getParams1() {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("a", "1");
        objectObjectHashMap.put("b", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("http://127.0.0.1:8080/getParams?a={a}&b={b}", String.class, objectObjectHashMap);
        System.out.println(response);
        return "get success";
    }


    @PostMapping("/postTest")
    public String postTest(HttpServletRequest request) {
        String age1 = request.getParameter("age");
        String name1 = request.getParameter("name");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String age2 = request.getParameter("age");
                String name2 = request.getParameter("name");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                age2 = request.getParameter("age");
                name2 = request.getParameter("name");
            }
        }).start();
        return "post success";
    }
}
