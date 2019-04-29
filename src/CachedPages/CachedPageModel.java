package CachedPages;

import java.util.HashMap;
import java.util.Map;

public class CachedPageModel {
    public String url;
    public String headers;
    public String html;
    public CachedPageModel(String url, String headers, String html){
        this.url = url;
        this.headers = headers;
        this.html = html;
    }



}
