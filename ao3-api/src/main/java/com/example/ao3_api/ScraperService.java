package com.example.ao3_api;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {
    public Fic scrapeFic(String url) {
        Fic fic = new Fic();
        
        try {
            Document doc = Jsoup.connect(url).get();

            String title = doc.select("h2.tittle.heading").first().text();
            String author = doc.select("a[rel=author]").first().text();
            String summary = doc.select("div.summary backquote p").text();
            String fandom = doc.select("dd.fandoms a.tag").text();
            String chapters = doc.select("dd.chapters").text();
            
            String wordsString = doc.select("dd.words").text().replaceAll(",", "");
            int wordCount = Integer.parseInt(wordsString);

            fic.setTitle(title);
            fic.setAuthor(author);
            fic.setSummary(summary);
            fic.setWordCount(wordCount);
            fic.setChapterCount(chapters);
            fic.setFandom(fandom);
        }
            catch (IOException e) {
                System.err.println("Error fetching the URL: " + e.getMessage());
            }
            
        return fic;
    }
}
    
