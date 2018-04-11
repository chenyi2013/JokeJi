package com.kevin.jokeji.features.text;

import com.kevin.jokeji.beans.JokeItem;
import com.kevin.jokeji.features.base.HtmlCommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by kevin on 17/3/4.
 */

public class AwardsModel extends HtmlCommonModel<ArrayList<JokeItem>> {

    @Override
    protected boolean isUseCache() {
        return false;
    }

    @Override
    public ArrayList<JokeItem> getData(String url) {
        ArrayList<JokeItem> jokes = new ArrayList<JokeItem>();
        JokeItem joke = null;

        int sectionPosition = 0;
        int listPosition = 0;

        try {

            Document doc = Jsoup.connect(url).timeout(30000).get();

            Element element = doc.getElementsByClass("jokeall_main").get(0);

            Elements h2s = element.getElementsByTag("h2");

            String key = null;
            String uri = null;

            for (Element e : h2s) {

                key = e.child(0).text();
                uri = e.child(0).attr("href");

                joke = new JokeItem(JokeItem.SECTION);
                joke.sectionPosition = sectionPosition++;
                joke.listPosition = listPosition++;
                joke.setTitle(key);
                jokes.add(joke);

                Elements uls = e.parent().parent().getElementsByTag("ul")
                        .get(0).getElementsByTag("li");
                for (Element li : uls) {

                    joke = new JokeItem(JokeItem.ITEM);

                    joke.setTitle(li.child(0).text());
                    joke.setUrl(li.child(0).attr("href"));
                    joke.setCount(li.child(1).text());

                    jokes.add(joke);

                }

                joke = new JokeItem(JokeItem.MORE);

                joke.setTitle("查看更多");
                joke.setUrl(uri);
                jokes.add(joke);

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return jokes;
    }
}
