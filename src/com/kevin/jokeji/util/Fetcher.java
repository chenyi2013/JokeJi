package com.kevin.jokeji.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kevin.jokeji.beans.Category;
import com.kevin.jokeji.beans.Joke;
import com.kevin.jokeji.beans.JokeItem;

public class Fetcher {

	public static ArrayList<Joke> getJokes(String url) {

		ArrayList<Joke> jokes = new ArrayList<Joke>();
		Joke joke = null;

		try {
			Document doc = Jsoup.connect(url).timeout(5000).get();

			Element content = doc.getElementsByClass("list_title").get(0);

			Elements lis = content.getElementsByTag("li");

			for (Element li : lis) {

				joke = new Joke();

				joke.setTitle(li.child(0).child(0).text());
				joke.setUrl(li.child(0).child(0).attr("href"));

				joke.setCount(li.child(1).text());
				joke.setData(li.child(2).text());

				jokes.add(joke);

			}

			return jokes;

		} catch (IOException e) {
		}
		return null;
	}

	public static String getJoke(String url) {

		try {

			String str = URLEncoder.encode("冷笑话", "utf-8");
			url = url.replace("冷笑话", str);
			Document doc = Jsoup.connect(url).timeout(5000).get();

			Element element = doc.getElementById("text110");

			return element.html();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static ArrayList<Category> getCategorys(String url) {

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

			return categorys;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static ArrayList<Joke> getRanks(String url) {

		ArrayList<Joke> jokes = new ArrayList<Joke>();
		Joke joke = null;

		try {

			Document doc = Jsoup.connect(url).timeout(5000).get();

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

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static ArrayList<JokeItem> getAwards(String url) {

		ArrayList<JokeItem> jokes = new ArrayList<JokeItem>();
		JokeItem joke = null;

		int sectionPosition = 0;
		int listPosition = 0;

		try {

			Document doc = Jsoup.connect(url).timeout(5000).get();

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

			return jokes;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
