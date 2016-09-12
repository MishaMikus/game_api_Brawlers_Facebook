package model;

import java.util.Map;

public class PostRequest {
    public String url;
    public String body;
    public Map<String, String> header;

    public PostRequest(String url, String body, Map<String, String> header) {
        this.url = url;
        this.body = body;
        this.header = header;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "url='" + url + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public PostRequest(String url, String body) {
        this.url = url;
        this.body = body;
    }
}
