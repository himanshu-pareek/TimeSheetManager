package dev.codegood.timesheetmanager.demo;

import dev.codegood.timesheetmanager.util.AuthUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/demo/v1")
public class DemoController {

    @GetMapping("/public")
    public Object allowAll() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", AuthUtil.getUserId());
        return map;
    }

}
