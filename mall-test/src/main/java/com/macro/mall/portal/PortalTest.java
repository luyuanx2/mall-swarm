package com.macro.mall.portal;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.dtstack.plat.lang.base.JSONs;
import com.macro.mall.common.api.CommonResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luyuanx2
 */
@Component
@Slf4j
public class PortalTest implements ApplicationListener<ApplicationStartedEvent> {


//    private String portalUrl = "http://172.16.8.134:8201/mall-portal";
    private String demoUrl = "http://172.16.8.134:8201/mall-demo";

//    private String demoUrl = "http://mall-gateway:8201/mall-demo";
    private String portalUrl = "http://mall-gateway:8201/mall-portal";

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
    }



    /**
     * 访问demo应用
     */
    @Scheduled(fixedRate = 5000)
    public void scheduleDemo(){
        login(demoUrl + "/feign/portal/login");
        HttpUtil.get(demoUrl + "/feign/portal/home");
        HttpUtil.get(demoUrl + "/feign/search/justSearch");
    }

    /**
     * 首页
     */
    @Scheduled(fixedRate = 5000)
    public void scheduleHome(){
//        HttpUtil.get(portalUrl + "/home/content");

    }

    /**
     * 5秒登录一次
     */
    @Scheduled(fixedRate = 5000)
    public void scheduleLogin(){
//        login("http://172.16.8.134:8201/mall-portal/sso/login");
    }

    /**
     * 异常一分钟一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void scheduleException(){
        HttpUtil.get(portalUrl + "/home/execp");
    }

    /**
     * 耗时一分钟一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void scheduleTimeout(){
        HttpUtil.get(portalUrl + "/home/timeout");
    }



    public String login(String url) {
        Map<String, String> map = new HashMap<>();
        map.put("username", "test");
        map.put("password", "test");
        HttpRequest request = HttpUtil.createPost(url);
        request.contentType(ContentType.MULTIPART.getValue());
        request.formStr(map);
        HttpResponse response = request.execute();
        CommonResult<Map<String, String>> commonResult = JSONs.fromJSON(response.body(), CommonResult.class);
        String token = commonResult.getData().get("token");
        getUserInfo(token);
        return token;
    }

    private void getUserInfo(String token) {
        HttpRequest request = HttpUtil.createGet(portalUrl + "/sso/info");
        request.bearerAuth(token);
        HttpResponse response = request.execute();
        log.info("当前登录用户：{}", response.body());
    }

}
