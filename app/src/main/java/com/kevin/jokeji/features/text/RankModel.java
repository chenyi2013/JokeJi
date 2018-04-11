package com.kevin.jokeji.features.text;

import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.features.base.HtmlCommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kevin on 17/3/4.
 */

public class RankModel extends HtmlCommonModel<ArrayList<Joke>> {

    @Override
    protected boolean isUseCache() {
        return false;
    }

    @Override
    public ArrayList<Joke> getData(String url) {
        ArrayList<Joke> jokes = new ArrayList<Joke>();
        Joke joke = null;

        try {

            Document doc = Jsoup.connect(url).timeout(30000).get();

            Elements elements = doc.getElementsByClass("main_14");

            for (Element a : elements) {

                joke = new Joke();
                joke.setTitle(a.text());
                joke.setUrl(a.attr("href"));
                joke.setCount(a.parent().parent().child(2).text());
                joke.setData(a.parent().parent().child(3).text());
                jokes.add(joke);

            }

            return jokes;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}
