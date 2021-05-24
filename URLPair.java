package com.company;

class URLPair {
    private String url;
    private int depth;
    public URLPair(String url, int depth) {
        this.url = url;
        this.depth = depth;
    }
    public String getURL() {
        return url;
    }
    public int getDepth() {
        return depth;
    }
    public String toString() {
        return "Depth: " + depth + "\tURL: " + url;
    }
}
