package analysis;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.FindSets;
import parser.State;

public class AvailableExpressionsAnalysis 
{
	public static ArrayList<String> findLoopsSets(ArrayList<State> als, int index, int start, int end)
	{
		boolean flag = true;
//		System.out.println("Here!");
//		for (int j = start; j < end; j++)
//		{
			//ArrayList<String> vars = als.get(j).getFlowSet();
			//System.out.println(vars.size());
//			for(int h=0; h<vars.size();h++)
//			{
//				System.out.println("vars "+vars.get(h));
//			}
			String avail_outSet = " ", avail_inSet = " ";
			
			ArrayList<String> tempavailoutList = new ArrayList<String>();
			
			//Last Index of loop 
			ArrayList<String> tempavailoutListlast = new ArrayList<String>(als.get(end-1).getAvailOut());
			ArrayList<String> tempavailoutListStart = new ArrayList<String>(als.get(start-1).getAvailOut());
			
			//Intersection
//			ArrayList<String> tempmerge = new ArrayList<String>();
			
			if(index==start)
			{
				//tempavailoutList = new ArrayList<String>(als.get(index-1).getAvailIn());
	//			System.out.println("With Avail-in");
				
				if(tempavailoutListlast.size()>0 && tempavailoutListStart.size()>0)
				{
					for(int i=0; i<tempavailoutListlast.size();i++)
					{
						for(int j=0; j<tempavailoutListStart.size();j++)
						{
							if(tempavailoutListlast.get(i) != null && tempavailoutListStart.get(j) != null)
							{
								if(tempavailoutListlast.get(i).contains(tempavailoutListStart.get(j)))
								{
									tempavailoutList.add(tempavailoutListlast.get(i));
								}
							}
						}
					}
				}
				als.get(index-1).setAvailIn(tempavailoutList);
				flag=false;
//			for(int i=0; i<tempavailoutList.size();i++)
//			{
//				System.out.println(tempavailoutList.get(i));
//			}
						

				avail_inSet = "{";
				
				for(int t=0;t<tempavailoutList.size();t++)
				{
					
					if(tempavailoutList.get(t) == null)
					{
						
						continue;
					}
					if(t<tempavailoutList.size())
					{
						if(tempavailoutList.contains(tempavailoutList.get(t)));
					}
					avail_inSet = avail_inSet + tempavailoutList.get(t);
					if(t == tempavailoutList.size()-1)
					{
						continue;
					}
					avail_inSet = avail_inSet + ", ";
					
				}
				avail_inSet = avail_inSet + " }";
				
				if(!(tempavailoutList.contains(als.get(index-1).getGenSet())))
				{
					tempavailoutList.add(als.get(index).getGenSet());
	//				System.out.println("Adding");
	//				for(int i=0; i<tempavailoutList.size();i++)
	//				{
	//					System.out.println(tempavailoutList.get(i));
	//				}
				}
				ArrayList<String> avail_out = AvailableExpressionsAnalysis.findAvailOut(tempavailoutList, als.get(index).getkillSet());
				
				
				//als.get(index).setAvailOut(avail_out);
				avail_outSet = "{";
				
				ArrayList<String> temparr = new ArrayList<String>(als.get(index-1).getAvailIn());
				for(int i = 0; i<temparr.size();i++)
				{
					avail_out.add(temparr.get(i));
				}
				als.get(index).setAvailOut(avail_out);
//				avail_out.add(als.get(index-1).getAvailIn());
				for(int t=0;t<avail_out.size();t++)
				{
					
					if(avail_out.get(t) == null)
					{
						
						continue;
					}
					if(t<avail_out.size())
					{
						if(avail_out.contains(avail_out.get(t)));
					}
					avail_outSet = avail_outSet + avail_out.get(t);
					if(t == avail_out.size()-1)
					{
						continue;
					}
					avail_outSet = avail_outSet + ", ";
					
				}
				avail_outSet = avail_outSet + " }";
			}


		
			//Avail out for Iterations
			if(flag)
			{
				//Avail IN

				avail_inSet ="{";
				if(index ==0)
				{
					avail_inSet = "{ }";
				}
				else
				{
					ArrayList<String> tempavailinList = new ArrayList<String>( als.get(index-1).getAvailOut());
					for(int t=0;t<tempavailinList.size();t++)
					{
						if(tempavailinList.get(t) == null)
						{
							continue;
						}
						avail_inSet = avail_inSet + tempavailinList.get(t);
						if(t == tempavailinList.size()-1)
						{
							continue;
						}
						avail_inSet = avail_inSet + ", ";	
					}	
					avail_inSet = avail_inSet + " }";
				}
				
				avail_outSet = " ";			
				//ArrayList<String> tempavailoutList;
				
		
	//				System.out.println("With Avail-in");
					tempavailoutList = new ArrayList<String>(als.get(index).getAvailIn());
	//				for(int i=0; i<tempavailoutList.size();i++)
	//				{
	//					System.out.println(tempavailoutList.get(i));
	//				}
	
				
				
					//tempavailoutList = new ArrayList<String>();
				
				
				if(!(tempavailoutList.contains(als.get(index).getGenSet())))
				{
					tempavailoutList.add(als.get(index).getGenSet());
	//				System.out.println("Adding");
	//				for(int i=0; i<tempavailoutList.size();i++)
	//				{
	//					System.out.println(tempavailoutList.get(i));
	//				}
				}
	//			System.out.println("Final");
	//			for(int i=0; i<tempavailoutList.size();i++)
	//			{
	//				System.out.println(i+" "+tempavailoutList.get(i));
	//			}
				ArrayList<String> avail_out = AvailableExpressionsAnalysis.findAvailOut(tempavailoutList, als.get(index).getkillSet());
	//			for(int i=0; i<tempavailoutList.size();i++)
	//				{
	//					System.out.println(i+" "+tempavailoutList.get(i));
	//				}
				als.get(index).setAvailOut(avail_out);
				if(index+1 < als.size())
				{
					als.get(index+1).setAvailIn(avail_out);
				}
	//			if(avail_out.isEmpty())
	//			{
	//				avail_outSet = "{ }";
	//			}
	//			else
	//			{
					avail_outSet = "{";
					
					for(int t=0;t<avail_out.size();t++)
					{
						
						if(avail_out.get(t) == null)
						{
							
							continue;
						}
						if(t<avail_out.size())
						{
							if(avail_out.contains(avail_out.get(t)));
						}
						avail_outSet = avail_outSet + avail_out.get(t);
						if(t == avail_out.size()-1)
						{
							continue;
						}
						avail_outSet = avail_outSet + ", ";
						
					}
					avail_outSet = avail_outSet + " }";
			}
				
		return convertArrayToString(als.get(index).getLineNo(),als.get(index).getStatement(),1,als.get(index).getGenSet(),als.get(index).getkillSet(),avail_inSet,avail_outSet);
//		}

	}
	
	private static ArrayList<String> convertArrayToString(int no, String stmt, int iter,String gs, String ks, String AvailIn, String AvailOut)
	{
		ArrayList<String> tempData = new ArrayList<String>();
			
		tempData.add(String.valueOf(no));
		tempData.add(stmt);
		tempData.add(String.valueOf(iter));
		tempData.add(FindSets.checkSetsIfNull(gs));
		tempData.add(FindSets.checkSetsIfNull(ks));
		tempData.add(AvailIn);
		tempData.add(AvailOut);
	
		return tempData;
	}
	//Avail_out
	public static ArrayList<String> findAvailOut(ArrayList<String> exp,String var) 
	{

		if(exp.size() <= 0)
		{
			return exp;
		}
		for(int i=exp.size() - 1; i>=0; i--) //[a+b, a-b, c+d], a
		{
//			System.out.println("In Loop");
			//System.out.println(exp.get(i)+" :Element");
			if(exp.get(i) != null && var != null)
			{
//				System.out.println(exp.get(i)+" "+i+" ");
//				System.out.println(exp.get(i).trim().contains(var.trim()));
//				System.out.println(var);
				if(exp.get(i).trim().contains(var.trim()))
				{
//					System.out.println(exp.get(i)+" Removing");
					exp.remove(i);
				}
			}
		}
//		System.out.println("Returning");
//		for(int i=0; i<exp.size();i++)
//		{
//			System.out.println(exp.get(i));
//		}
		return exp;
		
	}
	
	//Generate gen set
	public static String findGenSet(String line)
	{
		if(line.contains(":="))
		{
			String[] exp = line.split(":=");
			for(int i=0; i<exp.length;i++)
			{			
				if(exp[i].contains("+") || exp[i].contains("-") || exp[i].contains("*") || exp[i].contains("/"))
				{
					return exp[i];		
				}
			}
				
		}
			return null;
	}
	
	//Generate Kill set
	public static String findKillSet(String line)
	{
		Pattern p = Pattern.compile("^(((int|float|double|long)\\s(([\\_\\w\\d])+))|(([\\_\\w\\d])+))");
		
		Matcher m = p.matcher(line.trim());
		while(m.find())
		{
			if((String.valueOf(m.group(4)).length()==1))
			{
				return m.group(4);
			}
			if((String.valueOf(m.group(6)).length()==1))
			{
				return m.group(6);
			}
		}
		
		return null;		
	}
}
