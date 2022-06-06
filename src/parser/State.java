package parser;
import java.util.ArrayList;
import java.util.Collections;

public class State 
{
	//Elements
	private ArrayList<String> variableInGenset;
	private int lineNo;
	//private ArrayList<String> flowSet = new ArrayList<String>();
//	public boolean isDef;
//	private Boolean isLoopStmt;
	private String killSet;
	private String genSet;
//	private int loopStart;
//	private int loopFinish;
	private String statement;
	private ArrayList<String> availIn;
	private ArrayList<String> availOut;
	int left;
	int right;
	
	//Constructor
	public State(ArrayList<String> al, int ln, String kill, String gen, String stmt) 
	{
		variableInGenset = new ArrayList<String>(al);
		lineNo = ln;
		killSet = kill;
//		isLoopStmt = loop;
		genSet = gen;
		statement = stmt;
		availIn= new ArrayList<String>();
		availOut= new ArrayList<String>();
	}
	
	//Getters and Setters Functions
	
	//Getters
	public int getLineNo()
	{
		return this.lineNo;
	}
	public String getkillSet()
	{
		return this.killSet;
	}
	public String getGenSet()
	{
		return this.genSet;
	}
	public String getStatement()
	{
		return this.statement;
	}
	public ArrayList<String> getAvailIn()
	{
		return this.availIn;
	}
	public ArrayList<String> getAvailOut()
	{
		return this.availOut;
	}
//	public boolean getLoopFlag()
//	{
//		return this.isLoopStmt;
//	}
	
	//setters 
	public void setLineNo(int no)
	{
		this.lineNo = no;
	}
	public void setkillSet(String ks)
	{
		this.killSet = ks;
	}
	public void setGenSet(String gs)
	{
		this.genSet = gs;
	}
	public void setStatement(String stmt)
	{
		this.statement = stmt;
	}
	public void setAvailIn(ArrayList<String> ai)
	{
		this.availIn = new ArrayList<String>(ai);
	}
	public void setAvailOut(ArrayList<String> ao)
	{
		this.availOut = new ArrayList<String>(ao);
	}

	
}
