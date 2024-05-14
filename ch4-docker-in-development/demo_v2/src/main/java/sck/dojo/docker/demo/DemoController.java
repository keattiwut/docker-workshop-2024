package sck.dojo.docker.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @GetMapping("/greeting")
    public List<String> greeting() {
        return List.of("Hello", "World", "Hello Again");
    }

    @GetMapping("/hello")
    public String hello() { return "World"; }

    @GetMapping("/love")
    public String love() { return "live"; }
}
