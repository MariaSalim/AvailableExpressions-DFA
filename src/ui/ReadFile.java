package ui;
//Java Classes
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import analysis.AvailableExpressionsAnalysis;
import parser.FindSets;
import parser.State; 

public class ReadFile 
{
	public static void main(String[] args) 
	{
		try {

		// Creating an object of the file for reading the statement
		File file = new File("./Files/Test.txt");  
	
		ArrayList<State> stmts = new ArrayList<State>();

		Scanner myReader = new Scanner(file);
		int i=0;
		while (myReader.hasNextLine()) 
		{
			String stmt = myReader.nextLine();

			if(FindSets.checkStatementToNotConsider(stmt))
			{
				continue; 
			}
					
			stmts.add(new State(new ArrayList<String>(), i+1, AvailableExpressionsAnalysis.findKillSet(stmt), AvailableExpressionsAnalysis.findGenSet(stmt), stmt));
			
			i++;
		}
		
		myReader.close();
		
		for (int j = 0; j < stmts.size(); j++)
		{
			System.out.println(stmts.get(j).getLineNo()+":");
			System.out.println("-> Statement: " + stmts.get(j).getStatement());
			System.out.println("\t=> Gen Set: " + stmts.get(j).getGenSet());
			System.out.println("\t=> Kill Set: " + stmts.get(j).getkillSet());
		}
		
		} catch (FileNotFoundException e) {
		System.out.println("Error");
		e.printStackTrace();
		}
		
	}

}
