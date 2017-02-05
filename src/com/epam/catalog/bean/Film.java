package com.epam.catalog.bean;

import java.util.ArrayList;

public class Film {

	private String name;
	private ArrayList<String> genres;
	private ArrayList<String> actors;
	private News news;
	
	public Film(){}
	public Film(String name, ArrayList<String> genres, ArrayList<String> actors, String newsTitle, String newsText, String newsDate){
		this.name = name;
		this.genres = new ArrayList<String>();
		this.genres.addAll(genres);
		this.actors = new ArrayList<String>();
		this.actors.addAll(actors);
		this.news = new News(newsTitle, newsText, newsDate);
	}
		
	public String getName(){
		return this.name;
	}
	
	public ArrayList<String> getActors(){
		return this.actors;
	}
	
	public ArrayList<String> getGenres(){
		return this.genres;
	}
	public News getNews(){
		return this.news;
	}
	
	public void showNews(){
		System.out.println(this.name);
		for(String genre: genres){
			System.out.print(genre);
			if (!genre.equals(genres.get(genres.size()-1))){
				System.out.print(", ");
			}
			else{
				System.out.println(".");
			}
		}
		for(String actor: actors){
			System.out.print(actor);
			if (!actor.equals(actors.get(actors.size()-1))){
				System.out.print(", ");
			}
			else{
				System.out.println(".");
			}
		}
		System.out.println(news.getTitle());
		System.out.println(news.getText());
		System.out.println(news.getDate());
		
	}
}
