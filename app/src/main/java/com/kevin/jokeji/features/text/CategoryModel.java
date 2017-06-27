package com.kevin.jokeji.features.text;

import com.kevin.jokeji.beans.Category;
import com.kevin.jokeji.features.base.HtmlCommonModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by kevin on 17/3/4.
 */

public class CategoryModel extends HtmlCommonModel<ArrayList<Category>> {

    @Override
    protected boolean isUseCache() {
        return true;
    }

    @Override
    public ArrayList<Category> getData(String url) {
        ArrayList<Category> categorys = new ArrayList<Category>();
        Category category = null;

        try {

            Document doc = Jsoup.connect(url).timeout(5000).get();

            Element element = doc.getElementById("classlist");

            Elements elements = element.getElementsByTag("a");

            for (Element a : elements) {

                category = new Category();
                category.setTitle(a.text());
                category.setUrl(a.attr("href"));
                categorys.add(category);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorys;
    }
}
