package com.kevin.jokeji.features.image;

import android.util.Log;

import com.kevin.jokeji.beans.Image;
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

public class ImageModel extends HtmlCommonModel<ArrayList<Image>> {

    @Override
    public String formatUrlForPageId(String url, int page) {

        Log.i("mq",url + "/index_" + page + ".html");

        return url + "/index_" + page + ".html";
    }

    @Override
    public ArrayList<Image> getData(String url) {
        ArrayList<Image> images = new ArrayList<Image>();

        try {
            Document doc = Jsoup.connect(url).timeout(5000).get();
            Elements contents = doc.getElementsByClass("main-list");
            String imgUrl = "http://" + new URL(url).getHost();

            Image image = null;
            for (Element element : contents) {

                image = new Image();

                Element dtM = element.getElementsByTag("dt").get(0);

                Element aM = dtM
                        .getElementsByTag("a")
                        .get(0);

                image.setIcon(imgUrl + aM
                        .getElementsByTag("img")
                        .get(0)
                        .attr("src"));


                image.setAuthor(dtM.getElementsByTag("p")
                        .get(0)
                        .getElementsByTag("a")
                        .get(0)
                        .text());

                image.setDate(dtM.getElementsByTag("span")
                        .get(0).text()
                );
                image.setTitle(dtM.child(2).text());
                image.setImage(imgUrl + element
                        .child(1)
                        .child(0)
                        .child(0)
                        .child(0)
                        .attr("src"));


                images.add(image);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return images;
    }

}
