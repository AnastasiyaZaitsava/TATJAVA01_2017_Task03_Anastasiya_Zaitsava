package com.epam.catalog.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.epam.catalog.bean.Book;
import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.Film;
import com.epam.catalog.controller.Controller;
import com.epam.catalog.controller.exception.ControllerException;

public class View {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String command;
		String section;
		Controller controller = new Controller();
		while (true){
		System.out.println("Enter command (add - add news, find - news search, exit - close the application)");
		command = sc.nextLine();
		command.toLowerCase();
		if (command.equals("add")){
			System.out.println("Enter, to what section you want to add news (books, disks or films)");
			section = sc.nextLine();
			String[] news = new String[6];
			if (section.equals("books")){
				System.out.println("Enter title, authors (separated by commas and space), genres (separated by commas and space), "
						+ "news title, news text and news date. Start new field with a new line");
				for (int i=0; i<news.length; i++){
					news[i] = sc.nextLine();
				}
				controller.addBook(news);
				
			}
			else if (section.equals("disks")){
				System.out.println("Enter name, type of content, producer, "
						+ "news title, news text and news date. Start new field with a new line");
				for (int i=0; i<news.length; i++){
					news[i] = sc.nextLine();
				}
				controller.addDisk(news);
							
			}
			else if (section.equals("films")){
				System.out.println("Enter title,  genres (separated by commas and space), actors (separated by commas and space), "
						+ "news title, news text and news date. Start new field with a new line");
				for (int i=0; i<news.length; i++){
					news[i] = sc.nextLine();
				}
				controller.addFilm(news);
				
			}
			else{
				System.out.println("Such section doesn't exists");
			}
		}
		
		else if (command.equals("find")){
			System.out.println("Enter, in what section you want to find news (books, disks or films)");
			section = sc.nextLine();
			System.out.println("Enter parameters of search as follows: category value, value ");
			System.out.println("Every category start with new line.  "
					+ "In case of coincidence of categories in the settings, program will search by last set");
			if (section.equals("books")){
				System.out.println("Categories: name, authors, genres");
				ArrayList<String> parameters = readParameters(sc);
				ArrayList<Book> foundBooks;
				try {
					foundBooks = controller.findBook(parameters);
					if(foundBooks.isEmpty()){
						System.out.println("No books found");
					} else {
						for (Book book: foundBooks){
							book.showNews();
							System.out.println("");
						}
					}
				} catch (ControllerException e) {
					System.out.println("Search is temporarily unavaliable. Please, try again later.");
				}
				
			}
			else if (section.equals("disks")){
				System.out.println("Categories: name, content, producer");
				ArrayList<String> parameters = readParameters(sc);
				ArrayList<Disk> foundDisks;
				try {
					foundDisks = controller.findDisk(parameters);
					if(foundDisks.isEmpty()){
						System.out.println("No disks found");
					}
					else{
						for (Disk disk: foundDisks){
							disk.showNews();
							System.out.println("");
						}
					}
				} catch (ControllerException e) {
					System.out.println("Search is temporarily unavaliable. Please, try again later.");
				}
				
				
			}
			else if (section.equals("films")){
				System.out.println("Categories: name, genres, actors");
				ArrayList<String> parameters = readParameters(sc);
				ArrayList<Film> foundFilms;
				try {
					foundFilms = controller.findFilm(parameters);
					if(foundFilms.isEmpty()){
						System.out.println("No films found");
					} else {
						for (Film film: foundFilms){
							film.showNews();
							System.out.println("");
						}
					}
				} catch (ControllerException e) {
					System.out.println("Search is temporarily unavaliable. Please, try again later.");
				}
		
			}
			else{
				System.out.println("Such section doesn't exists");
			}
		}
		else if (command.equals("exit")){
			break;
		}
		else{
			System.out.println("Wrong command. Try again");
		}
	}
	}
	public static ArrayList<String> readParameters(Scanner sc){
		ArrayList<String> parameters = new ArrayList<String>();
		String paramBuf;
		while(sc.hasNextLine()){
			paramBuf=sc.nextLine();
			if(paramBuf.equals("end")){
				break;
			}
			else {
				parameters.add(paramBuf);
			}
		}
		return parameters;
	}
}
