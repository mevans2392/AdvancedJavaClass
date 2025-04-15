package edu.wgu.d387_sample_code.perfassess;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class WelcomeController {
    private WelcomeService welcomeService;

    public WelcomeController(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    @GetMapping("/welcome")
    public Map<String, String> welcome() {
        Map<String, String> messages = new HashMap<>();

        messages.put("english", welcomeService.getLocalizedMessage("en", "US"));
        messages.put("french", welcomeService.getLocalizedMessage("fr", "CN"));

        return messages;
    }


}
