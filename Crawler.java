package com.company;

import java.net.*;
import java.util.*;
import java.util.regex.*;

class Crawler {
    private HashMap<String, URLPair> links = new HashMap<>();
    private LinkedList<URLPair> pool = new LinkedList<>();
    private int depth = 0;

    public Crawler(String url, int depth) {
        this.depth = depth;
        pool.add(new URLPair(url, 0));
    }

    public void find() {
        while (pool.size() > 0)
            addlink(pool.pop());
        System.out.println("\nНайдено: " + links.size() + "\n");
        for (URLPair link : links.values())
            System.out.println(link);
    }

    private void addlink(URLPair link) {
        if (links.containsKey(link.getURL())) return;
        links.put(link.getURL(), link);
        if (link.getDepth() >= depth) return;
        try {
            URL url = new URL(link.getURL());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Scanner s = new Scanner(con.getInputStream());
            Pattern LINK_REGEX = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1");
            while (s.findWithinHorizon(LINK_REGEX, 0) != null) {
                String newURL = s.match().group(2);
                if (newURL.startsWith("/"))
                    newURL = link.getURL() + newURL;
                else if (!newURL.startsWith("http"))
                    continue;
                URLPair newLink = new URLPair(newURL, link.getDepth() + 1);
                pool.add(newLink);
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        String url = args[0];
        int depth = 0;
        try {
            depth = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        Crawler crawler = new Crawler(url, depth);
        crawler.find();
    }
}
