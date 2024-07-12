package cloud.habib.springkeyclock.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TestController {


    @GetMapping("/hello-admin")
    @PreAuthorize("hasRole('admin_client_role')")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @GetMapping("/hello-user")
    @PreAuthorize("hasRole('user_client_role') or hasRole('admin_client_role')")
    public ArrayList<String> helloUser() {
        var strings = new ArrayList<String>() ;
        strings.add("HELLO USER");
        return strings;
    }

}
