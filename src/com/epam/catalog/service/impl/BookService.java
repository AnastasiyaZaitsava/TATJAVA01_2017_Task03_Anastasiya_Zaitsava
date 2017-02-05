package com.epam.catalog.service.impl;

import java.util.ArrayList;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.BookDAO;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.dao.factory.DAOFactory;
import com.epam.catalog.service.exception.ServiceException;

public class BookService {
	public void addNews(String[] attributes){
		/* 0 - name
		   1 - authors
		   2 - genres
		   3 - news title
		   4 - news text
		   5 - news date */
		String name = attributes[0];
		ArrayList<String> authors = new ArrayList<String>();
		authors.addAll(parseAndConvert(attributes[1], ", "));
		ArrayList<String> genres = new ArrayList<String>();
		genres.addAll(parseAndConvert(attributes[2], ", "));
		Book book = new Book(name, authors, genres, attributes[3], attributes[4], attributes[5]);
		DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
		BookDAO bookDAO = daoObjectFactory.getBookDAO(); 
		bookDAO.addBook(book);
		
	}
	public ArrayList<Book> findNews(SearchCriteries criteries) throws ServiceException{
		ArrayList<Book> foundBooks = new ArrayList<Book>();
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance(); 
			BookDAO bookDAO = daoObjectFactory.getBookDAO(); 
			foundBooks = bookDAO.findNews(criteries);
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return foundBooks;
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
