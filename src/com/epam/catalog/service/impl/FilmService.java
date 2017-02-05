package com.epam.catalog.service.impl;

import java.util.ArrayList;

import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.FilmDAO;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.dao.factory.DAOFactory;
import com.epam.catalog.service.NewsService;
import com.epam.catalog.service.exception.ServiceException;

public class FilmService implements NewsService{
	
	public void addNews(String[] attributes){
		/* 0 - name
		   1 - authors
		   2 - genres
		   3 - news title
		   4 - news text
		   5 - news date */
		String name = attributes[0];
		ArrayList<String> genres = new ArrayList<String>();
		genres.addAll(parseAndConvert(attributes[2], ", "));
		ArrayList<String> actors = new ArrayList<String>();
		actors.addAll(parseAndConvert(attributes[1], ", "));
		
		Film film= new Film(name, genres, actors, attributes[3], attributes[4], attributes[5]);
		DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
		FilmDAO filmDAO = daoObjectFactory.getFilmDAO(); 
		filmDAO.addFilm(film);
	}	
	
	public ArrayList<Film> findNews(SearchCriteries criteries) throws ServiceException{
		DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
		FilmDAO filmDAO = daoObjectFactory.getFilmDAO(); 
		ArrayList<Film> foundFilms = new ArrayList<Film>();
		try {
			foundFilms = filmDAO.findNews(criteries);
		} catch (DAOException e) {
			throw new ServiceException();
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
