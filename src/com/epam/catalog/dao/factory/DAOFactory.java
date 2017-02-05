package com.epam.catalog.dao.factory;

import com.epam.catalog.dao.BookDAO;
import com.epam.catalog.dao.DiskDAO;
import com.epam.catalog.dao.FilmDAO;
import com.epam.catalog.dao.impl.FileBookDAO;
import com.epam.catalog.dao.impl.FileDiskDAO;
import com.epam.catalog.dao.impl.FileFilmDAO;

public final class DAOFactory {

	private static final DAOFactory instance = new DAOFactory();
	
	private final BookDAO fileBookImpl = new FileBookDAO();
	private final DiskDAO fileDiskImpl = new FileDiskDAO();
	private final FilmDAO fileFilmImpl = new FileFilmDAO();
	
	private DAOFactory(){}
	
	public static DAOFactory getInstance(){
		return instance;
	}
	
	public BookDAO getBookDAO(){
		return fileBookImpl;
	}
	
	public DiskDAO getDiskDAO(){
		return fileDiskImpl;
	}
	
	public FilmDAO getFilmDAO(){
		return fileFilmImpl;
	}
}
