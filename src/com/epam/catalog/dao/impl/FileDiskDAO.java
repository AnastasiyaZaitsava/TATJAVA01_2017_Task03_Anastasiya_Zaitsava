package com.epam.catalog.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.epam.catalog.bean.Disk;
import com.epam.catalog.bean.SearchCriteries;
import com.epam.catalog.dao.DiskDAO;
import com.epam.catalog.dao.exception.DAOException;

public class FileDiskDAO implements DiskDAO{
	String resourceFile = "News.txt";
	
	public void addDisk(Disk disk){

		try{
			FileWriter sw = new FileWriter(resourceFile,true);
			sw.write("\r\n\r\n"+"Disk" + "\r\n");
			sw.write(disk.getName() + "\r\n");
			sw.write(disk.getContent() + "\r\n");
			sw.write(disk.getProducer() + "\r\n");
			sw.write(disk.getNews().getTitle() + "\r\n");
			sw.write(disk.getNews().getText() + "\r\n");
			sw.write(disk.getNews().getDate());
			sw.close();

		 }catch(Exception e){

		       System.out.print("Exception");

		   }    
	}
	public ArrayList<Disk> findNews(SearchCriteries criteries) throws DAOException{
		ArrayList<Disk> foundDisks = new ArrayList<Disk>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(resourceFile));){
			String line;
			String diskName;
			String content;
			String producer;
			String newsTitle;
			String newsText;
			String newsDate;
			boolean soughtName = false;
			boolean soughtContent = false;
			boolean soughtProducer = false;
			while ((line = reader.readLine()) != null) {
				if(line.contains("Disk")){
					diskName = reader.readLine();
					if(criteries.getCriteries().containsKey("name")){
						if(diskName.equals(criteries.getCriteries().get("name")[0])){
							soughtName = true;
						}
					} else {
						soughtName = true;
					}
					content = reader.readLine();
					if(criteries.getCriteries().containsKey("content") && soughtName){
						if(content.contains(criteries.getCriteries().get("content")[0])){
							soughtContent = true;
						}
					} else {
						soughtContent = true;
					}
					producer = reader.readLine();
					if(criteries.getCriteries().containsKey("producer") && soughtName && soughtContent){
						if(producer.contains(criteries.getCriteries().get("producer")[0])){
							soughtProducer = true;
						}
					} else {
						soughtProducer = true;
					}
					
					
					if(soughtName && soughtContent && soughtProducer){
						newsTitle = reader.readLine();
						newsText = reader.readLine();
						newsDate = reader.readLine();
						foundDisks.add(new Disk(diskName, content, 
								producer, newsTitle, newsText, newsDate));
					}
					soughtName = false;
					soughtContent = false;
					soughtProducer = false;
				}					
			}
		} catch (FileNotFoundException e) {
			File newsFile = new File(resourceFile);
		} catch (IOException e){
			throw new DAOException();
		}

		
		return foundDisks;
	}

}
