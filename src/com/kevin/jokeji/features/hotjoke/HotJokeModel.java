package com.kevin.jokeji.features.hotjoke;

import com.kevin.jokeji.beans.Joke;
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

public class HotJokeModel extends HtmlCommonModel<ArrayList<Joke>> {

    HotJokeModel() {
        super();
    }


    @Override
    public String formatUrlForPageId(String url, int page) {

        if (url.matches(".+" + "\\_[0-9]+\\.htm$")) {
            return url.replaceAll("\\_[0-9]+\\.htm$", "_" + page + ".htm");
        }
        return url.replace(".htm", "_" + page + ".htm");
    }

    @Override
    public ArrayList<Joke> getData(String url) {

        ArrayList<Joke> jokes = new ArrayList<>();


        try {
            Document doc = Jsoup.connect(url).timeout(5000).get();
            Element content = doc.getElementsByClass("list_title").get(0);
            Elements lis = content.getElementsByTag("li");

            Joke joke = null;

            for (Element li : lis) {
                joke = new Joke();
                joke.setTitle(li.child(0).child(0).text());
                joke.setUrl(li.child(0).child(0).attr("href"));
                joke.setCount(li.child(1).text());
                joke.setData(li.child(2).text());
                jokes.add(joke);
            }
        } catch (IOException e) {

        }

        return jokes;
    }
}
