package parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindSets 
{
	
	//while condition
	public static boolean checkStatementIfLoopStarts(String line)
	{
		if(line.startsWith("While") || line.startsWith("while"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean checkStatementIfLoopEnds(String line)
	{
		if(line.startsWith("Goto") || line.startsWith("goto"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//Check if statements have to be analyzed 
	public static boolean checkStatementToNotConsider(String line)
	{
		if(line.contains("{") || line.contains("}")|| line.startsWith("//"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
	public static ArrayList<String> findVariablesInGenSet(String gs)
	{
		String delimeter = checkWhichOp(gs);
		String[] variables = splitusingOp(delimeter, gs);
		ArrayList<String> tmpArr = new ArrayList<String>();
		for(int i = 0;i<variables.length;i++)
		{
			tmpArr.add(variables[i].trim());
		}
		return tmpArr;
	}
	private static String checkWhichOp(String str)
	{
		if(str.contains("+"))
		{
			return "\\+";
		}
		else if(str.contains("/"))
		{
			return "/";
		}
		else if(str.contains("-"))
		{
			return "-";
		}
		else
		{
			return "\\*";
		}
	}
	private static String[] splitusingOp(String delimeter, String str)
	{
		return str.split(delimeter);
	}
	//Formatted Kill set
	public static String checkSetsIfNull(String set)
	{
		String tmp="{ }";
		if(set==null)
		{
			return tmp;
		}
		else
		{
			return "{ " + set +" }";
		}
	}
	
	
	//Formatted Gen set
//	public static String removeSemiColon(String str)
//	{
//		String[] removedSC = str.split(";");
//		//System.out.print(removedSC[0]);
//		return removedSC[0];
//	}
		
}
