package br.com.condesales.models;

import java.io.Serializable;

public class Statistics implements Serializable{

	public int getCheckinsCount() {
		return checkinsCount;
	}

	public int getUsersCount() {
		return usersCount;
	}

	public int getTipCount() {
		return tipCount;
	}

	private int checkinsCount;

	private int usersCount;

	private int tipCount;
}
