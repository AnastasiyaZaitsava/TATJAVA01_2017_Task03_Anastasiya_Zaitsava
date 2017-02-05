package com.epam.catalog.dao;

import org.testng.annotations.Test;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.Film;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.exception.DAOException;
import com.epam.catalog.dao.impl.FileBookDAO;
import com.epam.catalog.dao.impl.FileDiskDAO;
import com.epam.catalog.dao.impl.FileFilmDAO;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.testng.Assert;

public class tstDAO {
	
	@DataProvider (name = "FindBook")
	  public Object[][] dpBooks() {
		SearchCriteries[] booksDataSet = getDataSet("BookDataSet.txt", 5);
		return new Object[][] {
		      new Object[] { 2, booksDataSet[0]},
		      new Object[] { 1, booksDataSet[1] },
		      new Object[] { 1, booksDataSet[2] },
		      new Object[] { 2, booksDataSet[3] },
		      new Object[] { 0, booksDataSet[4] },
		    };
	}
	@DataProvider (name = "FindDisk")
	  public Object[][] dpDisks() {
		SearchCriteries[] disksDataSet = getDataSet("DiskDataSet.txt", 4);
		return new Object[][] {
		      new Object[] { 1, disksDataSet[0]},
		      new Object[] { 0, disksDataSet[1] },
		      new Object[] { 1, disksDataSet[2] },
		      new Object[] { 1, disksDataSet[3] },
		    };
	}
	@DataProvider (name = "FindFilm")
	  public Object[][] dpFilms() {
		SearchCriteries[] filmsDataSet = getDataSet("FilmDataSet.txt", 7);
		return new Object[][] {
		      new Object[] { 1, filmsDataSet[0]},
		      new Object[] { 2, filmsDataSet[1]},
		      new Object[] { 2, filmsDataSet[2]},
		      new Object[] { 0, filmsDataSet[3]},
		      new Object[] { 1, filmsDataSet[4]},
		      new Object[] { 2, filmsDataSet[5]},
		      new Object[] { 0, filmsDataSet[6]},
		    };
	}

  @Test (dataProvider = "FindBook")
  public void findBookTest(int expectedResult, SearchCriteries criteries) throws DAOException {
	  FileBookDAO bookDAO = new FileBookDAO();
	  ArrayList<Book> foundBooks = bookDAO.findNews(criteries);
	  Assert.assertEquals(foundBooks.size(), expectedResult);
  }
 
  @Test (dataProvider = "FindDisk")
  public void findDiskTest(int expectedResult, SearchCriteries criteries) throws DAOException {
	  FileDiskDAO diskDAO = new FileDiskDAO();
	  ArrayList<Disk> foundDisks = diskDAO.findNews(criteries);
	  Assert.assertEquals(foundDisks.size(), expectedResult);
  
  }
  
  @Test (dataProvider = "FindFilm")
  public void findFilmTest(int expectedResult, SearchCriteries criteries) throws DAOException {
	  FileFilmDAO filmDAO = new FileFilmDAO();
	  ArrayList<Film> foundFilms = filmDAO.findNews(criteries);
	  Assert.assertEquals(foundFilms.size(), expectedResult);
  
  }
 

  public static SearchCriteries formCriteries(ArrayList<String> parameters){
		String[] splitted;
		String[] categories = new String[parameters.size()];
		String[] items = new String[parameters.size()];
		for(int i = 0; i < parameters.size(); i++){
			splitted = parameters.get(i).split(" ", 2);
			categories[i] = splitted[0];
			items[i] = splitted[1];
		}
		SearchCriteries criteries = new SearchCriteries(categories, items);
		return criteries;
	}
  
  
  public SearchCriteries[] getDataSet(String fileName, int numOfSets){
	SearchCriteries[] dataSet = new SearchCriteries[numOfSets];
	  try (BufferedReader reader = new BufferedReader(new FileReader(fileName));){
			int i=0;
			String line;
			ArrayList<String> criteries = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				if(line.equals("set")){
					while(!(line = reader.readLine()).equals("endOfSet")){
						criteries.add(line);
						System.out.println(line);
					}
					dataSet[i] = formCriteries(criteries);
					i++;
					criteries.clear();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 
			e.printStackTrace();
		}

		return dataSet;
	  }
  }

