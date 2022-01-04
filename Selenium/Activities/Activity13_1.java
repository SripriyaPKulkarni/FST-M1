package activities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;


public class Activity13_1 {

	public static void main(String[] args) throws FileNotFoundException, IOException, CsvException {
		// TODO Auto-generated method stub

		//Load CSV file
        CSVReader reader = new CSVReader(new FileReader("C://Users//Sri Priya P Kulkarni//Softwares//Selenium//Sample.csv"));
 
        //Load content into list
        List<String[]> list = reader.readAll();
        System.out.println("Total number of rows are: " + list.size());
 
        //Create Iterator reference
        Iterator<String[]> itr = list.iterator();
 
        //Iterate all values
        while(itr.hasNext()) {
            String[] str = itr.next();
 
            System.out.print("Values are: ");
            for(int i=0;i<str.length;i++) {
                System.out.print(" " + str[i]);
            }
            System.out.println(" ");
        }
        
        reader.close();
	}

}
