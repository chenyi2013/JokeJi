package com.kevin.jokeji.features.saying;

import com.kevin.jokeji.features.base.CommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kevin on 17/3/3.
 */

public class SayingDetailModel extends CommonModel<ArrayList<String>> {

    @Override
    public ArrayList<String> getData(String ulr) {

        ArrayList<String> sayings = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(ulr).timeout(15000).get();
            Elements contents = doc.getElementsByClass("content");
            Elements elements = contents
                    .get(0).getElementsByTag("img");


            for (Element element : elements) {
                sayings.add(element.attr("src"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sayings;
    }

}
