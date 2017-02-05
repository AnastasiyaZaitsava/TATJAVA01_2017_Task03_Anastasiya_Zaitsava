package com.epam.catalog.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.BookDAO;
import com.epam.catalog.dao.exception.DAOException;

public class FileBookDAO implements BookDAO{
	String resourceFile = "News.txt";
	
	public void addBook(Book book){
		try{
			FileWriter sw = new FileWriter(resourceFile, true);
			sw.write("\r\n\r\n"+"Book" + "\r\n");
			sw.write(book.getName() + "\r\n");
			for(String author: book.getAuthors()){
				sw.write(author);
				if (!author.equals(book.getAuthors().get(book.getAuthors().size()-1))){
					sw.write(", ");
				}
			}
			sw.write("\r\n");
			for(String genre: book.getGenres()){
				sw.write(genre);
				if (!genre.equals(book.getGenres().get(book.getGenres().size()-1))){
					sw.write(", ");
				}
			}
			sw.write("\r\n");
			sw.write(book.getNews().getTitle() + "\r\n");
			sw.write(book.getNews().getText() + "\r\n");
			sw.write(book.getNews().getDate());
			sw.close();

		 }catch(Exception e){

		       System.out.print("Exception");

		   }   
	}
	public ArrayList<Book> findNews(SearchCriteries criteries) throws DAOException{
		ArrayList<Book> foundBooks = new ArrayList<Book>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(resourceFile));){
			String line;
			String bookName;
			String authors;
			String genres;
			String newsTitle;
			String newsText;
			String newsDate;
			boolean soughtName = false;
			int soughtAuthor = 0;
			int soughtGenre = 0;
			while ((line = reader.readLine()) != null) {
				if(line.contains("Book")){
					bookName = reader.readLine();
					if(criteries.getCriteries().containsKey("name")){
						if(bookName.equals(criteries.getCriteries().get("name")[0])){
							soughtName = true;
						}
					} else {
						soughtName = true;
					}
					authors = reader.readLine();
					if(criteries.getCriteries().containsKey("authors") && soughtName){
						for(int i = 0; i<criteries.getCriteries().get("authors").length; i++){
							if(authors.contains(criteries.getCriteries().get("authors")[i])){
								soughtAuthor++;
							}
						}
					} else {
						soughtAuthor++;
					}
					genres = reader.readLine();
					if(criteries.getCriteries().containsKey("genres") && soughtName && (soughtAuthor>0)){
						for(int i = 0; i<criteries.getCriteries().get("genres").length; i++){
							if(genres.contains(criteries.getCriteries().get("genres")[i])){
								soughtGenre++;
							}
						}
					} else {
						soughtGenre++;
					}
					
					
					if(soughtName && (soughtAuthor>0) && (soughtGenre>0)){
						newsTitle = reader.readLine();
						newsText = reader.readLine();
						newsDate = reader.readLine();
						foundBooks.add(new Book(bookName, parseAndConvert(authors, ", "), 
								parseAndConvert(genres, ", "), newsTitle, newsText, newsDate));
					}
					soughtName = false;
					soughtAuthor = 0;
					soughtGenre = 0;
				}					
			}
		} catch (FileNotFoundException e) {
			File newsFile = new File(resourceFile);
		} catch (IOException e){
			throw new DAOException();
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
