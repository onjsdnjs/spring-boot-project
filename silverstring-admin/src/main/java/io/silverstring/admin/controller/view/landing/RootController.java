package io.silverstring.admin.controller.view.landing;

import io.silverstring.core.service.UserService;
import io.silverstring.domain.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Controller
@RequestMapping("/")
public class RootController {

    final UserService userService;

    @Autowired
    public RootController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public ModelAndView root() {
        ModelAndView mvn = new ModelAndView("landing/index");
        return mvn;
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(name = "msg", defaultValue = "none") String msg) {
        ModelAndView mvn = new ModelAndView("landing/login");
        mvn.addObject("msg", msg);
        return mvn;
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        ModelAndView mvn = new ModelAndView("landing/logout");
        return mvn;
    }

    @RequestMapping("/regist")
    public ModelAndView regist(@RequestParam(name = "msg", defaultValue = "none") String msg) {
        ModelAndView mvn = new ModelAndView("landing/regist");
        mvn.addObject("msg", msg);
        return mvn;
    }
    
    @RequestMapping(value = "/doRegist", method= RequestMethod.POST)
    public String doSignup(
            @RequestParam("email") String email
            , @RequestParam("pwd") String pwd
            , @RequestParam("fingerprint") String fingerprint) {
        try {
            log.info("doRegist : {}", email);
            return userService.doRegist(email, pwd, fingerprint);
        } catch (Exception ex) {
            return "redirect:/regist?msg=invalid";
        }
    }

    @RequestMapping("/emailConfirm")
    public ModelAndView emailConfirm(@RequestParam("hash") String hash, @RequestParam("code") String code) {
        ModelAndView mvn = new ModelAndView("common/emailConfirm");
        UserDTO.ResEmailConfirm resEmailConfirm = userService.emailConfirm(hash, code);
        mvn.addObject("title", resEmailConfirm.getTitle());
        mvn.addObject("msg", resEmailConfirm.getMsg());
        mvn.addObject("url", resEmailConfirm.getUrl());
        mvn.addObject("urlTitle", resEmailConfirm.getUrlTitle());
        return mvn;
    }
}
