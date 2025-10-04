package com.example.ao3_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FicController {
    
    private final ScraperService scraperService;

    @Autowired
    public FicController(ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/getFicInfo")
    public Fic getFicInfo(String url) {
        return scraperService.scrapeFic(url);
    }
}
