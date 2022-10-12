package org.lslonina.books.safaricrawler;

import org.lslonina.books.safaricrawler.crawler.Crawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class CrawlerRunner {
    private static final Logger log = LoggerFactory.getLogger(SafariCrawlerApplication.class);
    
    private final Crawler crawler;

    public CrawlerRunner(Crawler crawler) {
        this.crawler = crawler;
    }

    @Scheduled(cron = "0 0 */2 * * ?")
    public void fetchData() {
        try {
            crawler.refreshCovers();
            crawler.loadData();
        } catch (RuntimeException ex) {
            log.info("Can't load data", ex);
        }
    }
}