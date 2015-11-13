/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.malike.auth.client.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author malike_st
 */
@Controller
public class DemoController {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld() {
        return "Hello World.";
    }

}
