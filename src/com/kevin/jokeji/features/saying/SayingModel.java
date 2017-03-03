package com.kevin.jokeji.features.saying;

import com.kevin.jokeji.beans.Saying;
import com.kevin.jokeji.features.base.HtmlCommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kevin on 17/3/2.
 */

public class SayingModel extends HtmlCommonModel<ArrayList<Saying>> {


    @Override
    public String formatUrlForPageId(String url, int page) {
        return url + page + ".htm";
    }

    @Override
    public ArrayList<Saying> getData(String url) {


        ArrayList<Saying> sayings = new ArrayList<>();

        Saying saying = null;


        try {
            Document doc = Jsoup.connect(url).timeout(15000).get();
            Elements contents = doc.getElementsByClass("channel");

            Elements elements = contents
                    .get(0)
                    .child(0)
                    .getElementsByTag("a");

            for (Element element : elements) {

                saying = new Saying();
                saying.setTitle(element.ownText());
                saying.setUrl("http://" + new URL(url).getHost() + element.attr("href"));
                sayings.add(saying);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sayings;
    }
}
