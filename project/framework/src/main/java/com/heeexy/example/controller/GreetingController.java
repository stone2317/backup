package com.heeexy.example.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.heeexy.example.util.constants.Constants;
import com.heeexy.example.util.model.ClientMessage;
import com.heeexy.example.util.model.ServerMessage;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author junmingyang
 * @date 2018/9/24 7:03 PM
 */
@Controller
public class GreetingController {

    @MessageMapping(Constants.HELLO_MAPPING)
    @SendTo(Constants.TOPIC)
    public ServerMessage greeting(ClientMessage message) throws Exception {
        // 模拟延时，以便测试客户端是否在异步工作
        Thread.sleep(1000);
        return new ServerMessage("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
