package com.example.ao3_api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ScraperService {

    public Fic scrapeFic(String url) {
        Fic fic = new Fic();
        try {
            Document doc = Jsoup.connect(url).get();

            fic.setTitle(safeGetText(doc, "h2.title.heading"));
            fic.setAuthor(safeGetText(doc, "a[rel=author]"));
            fic.setSummary(safeGetText(doc, "div.summary blockquote p"));
            fic.setFandom(safeGetText(doc, "dd.fandom a.tag"));
            fic.setChapterCount(safeGetText(doc, "dd.chapters"));

            String wordsString = safeGetText(doc, "dd.words");
            if (!wordsString.isEmpty()) {
                try {
                    int wordCount = Integer.parseInt(wordsString.replaceAll(",", ""));
                    fic.setWordCount(wordCount);
                } catch (NumberFormatException e) {
                    System.err.println("Could not parse word count: " + wordsString);
                    fic.setWordCount(0);
                }
            } else {
                fic.setWordCount(0);
            }

        } catch (IOException e) {
            System.err.println("Error scraping the URL: " + e.getMessage());
            return null;
        }
        return fic;
    }

    private String safeGetText(Document doc, String selector) {
        Element element = doc.select(selector).first();
        if (element != null) {
            return element.text();
        }
        return "";
    }
}