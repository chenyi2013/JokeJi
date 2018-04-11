package com.kevin.jokeji.features.saying;

import com.kevin.jokeji.features.base.HtmlCommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by kevin on 17/3/3.
 */

public class SayingDetailModel extends HtmlCommonModel<String> {

    @Override
    public String getData(String ulr) {

        String sayings = null;

        try {
            Document doc = Jsoup.connect(ulr).timeout(30000).get();
            Elements contents = doc.getElementsByClass("neirong");
            contents
                    .get(0)
                    .getElementsByClass("ad-box336")
                    .remove();
            contents
                    .get(0)
                    .getElementsByClass("ad-blank")
                    .remove();

            contents.get(0)
                    .getElementsByTag("img")
                    .remove();


            sayings = contents.html();


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return sayings;
    }

}
