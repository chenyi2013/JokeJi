package com.kevin.jokeji.beans;

import java.io.Serializable;

public class Joke implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private String data;
	private String count;
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Joke [title=" + title + ", data=" + data + ", count=" + count
				+ ", url=" + url + "]";
	}

}
