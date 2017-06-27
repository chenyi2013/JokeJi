package com.kevin.jokeji.beans;

import java.io.Serializable;

public class JokeItem extends Joke implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ITEM = 0;
	public static final int SECTION = 1;
	public static final int MORE = 2;

	public final int type;

	public int sectionPosition;
	public int listPosition;

	public JokeItem(int type) {
		this.type = type;

	}

}
