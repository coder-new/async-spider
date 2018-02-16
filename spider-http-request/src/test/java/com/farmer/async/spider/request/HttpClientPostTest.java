package com.farmer.async.spider.request;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/3
 */
public class HttpClientPostTest {

    @Test
    public void test() {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","UM_distinctid=15f6d69ebab1dd-07cfb5526240b8-c303767-1fa400-15f6d69ebac7b5; __utma=226521935.32960887.1508421617.1512660704.1512660704.1; __utmz=226521935.1512660704.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); _ga=GA1.2.32960887.1508421617; _gid=GA1.2.894988185.1517670667; .CNBlogsCookie=D672A3F7B633D4E5A0B08631FBE81D463C025F5BB28D9A5C19565059A49A44356DF8D20017B30DCF7707983AA6A42E3A2E8B6F296F117787CCF68CEC81A0534B29484B72E3DFE520164511713692E9CC46DA63C3; .Cnblogs.AspNetCore.Cookies=CfDJ8N7AeFYNSk1Put6Iydpme2ZFoR-RL6CqRXVS2_zWOtVPbuyYccLoA-Jsa6X0-LOACETaMzxJvf6JVn9i1mEUI0JqLH35G3mzHNAK6s8R7hE5WQiZ_3p9prifLa0k76L_6HxdsjU3-cZJs246EneU9I69EvPncX4Z-dTjM3llsFmzVYi8gmPIypVB1C1l4M0or3GbVTFeMRK3aaWvP5-RlFFsGAe9m-gcJ5NMm7lcYQZlImg_SEuZ7_h6KXQEcmQ9DHl-9F9PEIHYIhbtWzhHX62uhSR6Rr_0dG17PyVOCwjfnsWjlFcKu6xv2Tg-LkhrJQ; _gat=1");
        requestHeaders.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        requestHeaders.add("Content-Type","application/json; charset=UTF-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid","1f6bb84a-ce61-e111-aa3f-842b2b196315");
        jsonObject.put("groupId","00000000-0000-0000-0000-000000000000");
        jsonObject.put("page",1);
        jsonObject.put("isFollowes",true);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://home.cnblogs.com/u/nylcy/followers/?page=2",
                HttpMethod.GET, requestEntity, String.class);
        String body = responseEntity.getBody();

        Document doc = Jsoup.parse(body);
        Elements bloggerClasses = doc.getElementsByClass("avatar_list");

        List<Node> nodes = bloggerClasses.get(0).childNodes();
        for (Node node : nodes) {
            if (node.childNodes().size() == 0) {
                continue;
            }
            String[] arr = node.childNode(1).attr("href").split("/");
            System.out.println(arr[2]);
        }
    }
}
