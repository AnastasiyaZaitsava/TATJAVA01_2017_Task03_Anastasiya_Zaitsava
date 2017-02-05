package com.epam.catalog.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.FilmDAO;
import com.epam.catalog.dao.exception.DAOException;

public class FileFilmDAO implements FilmDAO {
	String resourceFile = "News.txt";

	public void addFilm(Film film){

		try{
			FileWriter sw = new FileWriter(resourceFile,true);
			sw.write("\r\n\r\n"+"Film" + "\r\n");
			sw.write(film.getName() + "\r\n");
			for(String genre: film.getGenres()){
				sw.write(genre);
				if (!genre.equals(film.getGenres().get(film.getGenres().size()-1))){
					sw.write(", ");
				}
			}
			sw.write("\r\n");
			for(String author: film.getActors()){
				sw.write(author);
				if (!author.equals(film.getActors().get(film.getActors().size()-1))){
					sw.write(", ");
				}
			}
			sw.write("\r\n");
			
			sw.write(film.getNews().getTitle() + "\r\n");
			sw.write(film.getNews().getText() + "\r\n");
			sw.write(film.getNews().getDate());
			sw.close();

		 }catch(Exception e){

		       System.out.print("Exception");

		   }    

	}
	public ArrayList<Film> findNews(SearchCriteries criteries) throws DAOException{
		ArrayList<Film> foundFilms = new ArrayList<Film>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(resourceFile));){
			String line;
			String filmName;
			String genres;
			String actors;
			String newsTitle;
			String newsText;
			String newsDate;
			boolean soughtName = false;
			boolean soughtGenre = false;
			boolean soughtActor = false;
			while ((line = reader.readLine()) != null) {
				if(line.contains("Film")){
					filmName = reader.readLine();
					if(criteries.getCriteries().containsKey("name")){
						if(filmName.equals(criteries.getCriteries().get("name")[0])){
							soughtName = true;
						}
					} else {
						soughtName = true;
					}
					genres = reader.readLine();
					if(criteries.getCriteries().containsKey("genres") && soughtName){
						for(int i = 0; i<criteries.getCriteries().get("genres").length; i++){
							if(genres.contains(criteries.getCriteries().get("genres")[i])){
								soughtGenre = true;
							}
						}
					} else {
						soughtGenre = true;
					}
					actors = reader.readLine();
					if(criteries.getCriteries().containsKey("actors") && soughtName && soughtGenre){
						for(int i = 0; i<criteries.getCriteries().get("actors").length; i++){
							if(actors.contains(criteries.getCriteries().get("actors")[i])){
								soughtActor = true;
							}
						}
					} else {
						soughtActor = true;
					}
					
					
					
					if(soughtName && soughtActor && soughtGenre){
						newsTitle = reader.readLine();
						newsText = reader.readLine();
						newsDate = reader.readLine();
						foundFilms.add(new Film(filmName, parseAndConvert(genres, ", "), 
								parseAndConvert(actors, ", "),  newsTitle, newsText, newsDate));
					}
					soughtName = false;
					soughtActor = false;
					soughtGenre = false;
				}					
			}
		} catch (FileNotFoundException e) {
			File newsFile = new File(resourceFile);
		} catch (IOException e){
			throw new DAOException();
		}

		
		return foundFilms;
	}
	public static ArrayList<String> parseAndConvert(String exp, String regex){
		String[] parsedExp = exp.split(regex);
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<parsedExp.length; i++){
			list.add(parsedExp[i]);
		}
		return list;
		
	}
}
