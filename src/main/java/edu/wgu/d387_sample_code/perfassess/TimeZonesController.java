package edu.wgu.d387_sample_code.perfassess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class TimeZonesController {
    private final TimeZonesService timeZonesService;

    @Autowired
    public TimeZonesController(TimeZonesService timeZonesService) {
        this.timeZonesService = timeZonesService;
    }

    @GetMapping("/timezone")
    public String getTimeZone(@RequestParam String timeZoneId) {
        return timeZonesService.getPresentationTimes(timeZoneId);
    }
}
