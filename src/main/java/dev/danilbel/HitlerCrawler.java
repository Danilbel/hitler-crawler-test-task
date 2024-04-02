package dev.danilbel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

public class HitlerCrawler implements Callable<String> {
    private static final int MAX_HOPS = 6;
    private static final String HITLER_URL = "https://en.wikipedia.org/wiki/Adolf_Hitler";
    private static final String URL_SELECTOR = "a[href^=\"/wiki/\"]:not([href*=\"#\"]):not([href*=\":\"])";
    private static final AtomicBoolean found = new AtomicBoolean(false);

    private final String currentUrl;
    private final int hopCount;
    private final String path;

    public HitlerCrawler(String currentUrl, int hopCount, String path) {
        this.currentUrl = currentUrl;
        this.hopCount = hopCount;
        this.path = path;
    }

    @Override
    public String call() {

        if (currentUrl.equals(HITLER_URL)) {
            found.set(true);
            return path;
        }

        if (found.get() || hopCount >= MAX_HOPS) {
            return null;
        }

        try {
            Document doc = Jsoup.connect(currentUrl).get();
            Elements links = doc.select(URL_SELECTOR);

            List<HitlerCrawler> subTasks = links.stream()
                    .map(element -> element.attr("abs:href"))
                    .distinct()
                    .filter(url -> !url.equals(currentUrl) && !path.contains(url))
                    .map(url -> new HitlerCrawler(url, hopCount + 1, path + "\n -> " + url))
                    .toList();
            
            return ForkJoinPool.commonPool().invokeAll(subTasks).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        } catch (Exception ignored) {
        }

        return null;
    }
}
