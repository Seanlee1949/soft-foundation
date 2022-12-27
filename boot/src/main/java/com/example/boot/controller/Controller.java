package com.example.boot.controller;

import com.example.boot.data.HttpRequest;
import com.example.boot.util.JsonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.boot.controller.DeviceController.replaceUrl;
import static com.example.boot.controller.DeviceController.token;

/**
 * @author lishuai
 * @since 2022/11/26
 */
@RestController
@RequestMapping()
public class Controller {
    @GetMapping("/previewOnline")
    public Object previewOnline(){
//        String url = replaceUrl + "/api/lastData?" + "deviceKey=" + deviceKey + "&pileId=" + pileId;
//        String s = HttpRequest.sendGet(url, "", token);
//        return JsonUtils.parseObject(s, Object.class);
        return  null;
    }
    @GetMapping("/index")
    public Object index(){
        return  null;
    }

}
