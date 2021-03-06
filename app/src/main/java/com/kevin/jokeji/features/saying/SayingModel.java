package com.kevin.jokeji.features.saying;

import com.kevin.jokeji.beans.Saying;
import com.kevin.jokeji.features.base.HtmlCommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kevin on 17/3/2.
 */

public class SayingModel extends HtmlCommonModel<ArrayList<Saying>> {


    @Override
    public String formatUrlForPageId(String url, int page) {
        return url + "_" + page + ".html";
    }

    @Override
    public ArrayList<Saying> getData(String url) {


        ArrayList<Saying> sayings = new ArrayList<>();

        Saying saying = null;


        try {
            Document doc = Jsoup.connect(url).timeout(30000).get();
            Elements contents = doc.getElementsByClass("art-t");


            for (Element element : contents) {

                saying = new Saying();
                saying.setTitle(element.child(0).text());
                saying.setUrl("http://" + new URL(url).getHost()
                        + element
                        .child(0)
                        .child(0)
                        .attr("href"));
                saying.setContent(element
                        .child(1)
                        .text());
                sayings.add(saying);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return sayings;
    }
}
