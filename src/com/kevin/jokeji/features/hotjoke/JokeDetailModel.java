package com.kevin.jokeji.features.hotjoke;

import com.kevin.jokeji.features.base.HtmlCommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by kevin on 17/3/4.
 */

public class JokeDetailModel extends HtmlCommonModel<String> {

    @Override
    protected boolean isUseCache() {
        return true;
    }

    @Override
    public String getData(String url) {
        try {

            String str = URLEncoder.encode("冷笑话", "utf-8");
            url = url.replace("冷笑话", str);
            Document doc = Jsoup.connect(url).timeout(5000).get();

            Element element = doc.getElementById("text110");

            Elements aElements = element.getElementsByTag("a");
            for (Element a : aElements) {
                a.remove();
            }

            return element.html();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
