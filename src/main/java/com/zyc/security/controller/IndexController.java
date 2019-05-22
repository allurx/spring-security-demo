package com.zyc.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zyc
 */
@Controller
public class IndexController {

    @RequestMapping({"/index", "/"})
    public String index() {
        return "index";
    }

}
