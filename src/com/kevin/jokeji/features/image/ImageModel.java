package com.kevin.jokeji.features.image;

import com.kevin.jokeji.beans.Image;
import com.kevin.jokeji.features.base.HtmlCommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kevin on 17/3/2.
 */

public class ImageModel extends HtmlCommonModel<ArrayList<Image>> {

    @Override
    public String formatUrlForPageId(String url, int page) {

        return url + "/index_" + page + ".htm";
    }

    @Override
    public ArrayList<Image> getData(String url) {
        ArrayList<Image> images = new ArrayList<Image>();

        try {
            Document doc = Jsoup.connect(url).timeout(5000).get();
            Elements contents = doc.getElementsByClass("mahua");

            Image image = null;
            for (Element element : contents) {

                image = new Image();
                images.add(image);


                Element iconEl = element.child(0).child(0).child(0);
                Element imgEl = element.child(1).child(0);
                image.setTitle(element.child(0).child(2).child(0).text());
                image.setAuthor(element.child(0).child(1).child(0).text());

                if (iconEl.hasAttr("mahuaImg")) {
                    image.setIcon(iconEl.attr("mahuaImg"));
                } else {
                    image.setIcon(iconEl.attr("src"));
                }

                if (!"img".equals(imgEl.tagName())) {
                    image.setContent(element.child(1).html());
                    image.setImage(false);
                    return images;
                }

                image.setImage(true);
                if (imgEl.hasAttr("mahuaImg")) {

                    image.setImage(imgEl.attr("mahuaImg"));
                    image.setGif(false);

                    if (imgEl.hasAttr("mahuaGifImg")) {
                        image.setGif(true);
                        image.setGifImg(imgEl.attr("mahuaGifImg"));
                    }
                } else {
                    image.setImage(imgEl.attr("src"));
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

}
