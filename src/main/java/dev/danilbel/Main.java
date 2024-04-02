package dev.danilbel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class Main {

    private static final String WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar /build/libs/hitler-crawler-1.0.jar <starting_wikipedia_page_url>");
            return;
        }

        if (!args[0].startsWith(WIKIPEDIA_URL)) {
            System.err.println("Please provide a valid Wikipedia URL.\nThe URL should start with " + WIKIPEDIA_URL);
            return;
        }

        System.out.println("Searching ...");

        String startingUrl = args[0];
        HitlerCrawler crawler = new HitlerCrawler(startingUrl, 0, startingUrl);
        Future<String> result = ForkJoinPool.commonPool().submit(crawler);

        try {
            String path = result.get();
            if (path != null) {
                System.out.println("Hitler found: \n" + path);
            } else {
                System.out.println("Hitler not found.");
            }
        } catch (Exception ignored) {
        }
    }
}
