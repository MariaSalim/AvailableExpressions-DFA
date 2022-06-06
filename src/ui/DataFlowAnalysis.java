package ui;


import java.awt.EventQueue;
import javax.swing.JFrame;


import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import analysis.AvailableExpressionsAnalysis;
import parser.FindSets;
import parser.State;

import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Cursor;

public class DataFlowAnalysis {

	private JFrame frmDataFlowAnalysis;
	private JTable table;
	private DefaultTableModel dtm;
	private JTextPane tpProgram;
	private static String filePath;
	private JPanel panelProgram;
	private JLabel lblProgram;
	private JLabel lblAnalysis;
//	private ArrayList<String> loopArray = new ArrayList<String>();
	private int loopStart=-1;
	private int loopEnd= -1;
	private int iteration = 0;
	
	//Grid
	private GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataFlowAnalysis window = new DataFlowAnalysis(" ");
					window.frmDataFlowAnalysis.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DataFlowAnalysis(String fp) {
		filePath = fp;	
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDataFlowAnalysis = new JFrame();
		frmDataFlowAnalysis.setTitle("Data Flow Analysis");
		frmDataFlowAnalysis.setBounds(100, 100, 726, 576);
		frmDataFlowAnalysis.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Components
		panelProgram = new JPanel(gbl);
		
		//Text Area
		tpProgram = new JTextPane();
		//Labels
		lblProgram = new JLabel("PROGRAM");
		lblAnalysis = new JLabel("Available Expressions Analysis");		
		//Grid Properties //Adding to Panel Grid
		gbc = new GridBagConstraints();
		panelProgram.setBackground(Color.decode("#274472")); //Dark Blue
		//Grids
		//1
		setGridPropertiesSpecific(0, 0);
		panelProgram.add(lblProgram, gbc);
		//2
		setGridPropertiesSpecific(0, 1);
		panelProgram.add(tpProgram,gbc);
		//3
		setGridPropertiesSpecific(0, 2);		
	    panelProgram.add(lblAnalysis,gbc);		
		//Panel to Pane
		frmDataFlowAnalysis.getContentPane().add(panelProgram, BorderLayout.NORTH);
		
		
		
		//Data
		ArrayList<State> als = new ArrayList<State>();
		als = readFile();
		
		//Table
		dtm = new DefaultTableModel(){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		if(loopStart == -1 || loopEnd == -1)
		{
			dtm.setColumnIdentifiers(new Object[]{ "Node", "Statement", "Gen Set","Kill Set","Avail In", "Avail Out"});
		}
		else
		{
			dtm.setColumnIdentifiers(new Object[]{ "Node", "Statement","Iteration", "Gen Set","Kill Set","Avail In", "Avail Out"});
		}
		table = new JTable(dtm);
		frmDataFlowAnalysis.getContentPane().add(table);
		//Pane
		JScrollPane scrollPane = new JScrollPane(table);
		frmDataFlowAnalysis.getContentPane().add(scrollPane);
		customizeComponents();
		
		for (int j = 0; j < als.size(); j++)
		{
			//ArrayList<String> vars = als.get(j).getFlowSet();
			//System.out.println(vars.size());
//			for(int h=0; h<vars.size();h++)
//			{
//				System.out.println("vars "+vars.get(h));
//			}
			String avail_outSet = " ", avail_inSet = " ";
			
			ArrayList<String> tempavailoutList;
			
			if(j>0)
			{
//				System.out.println("With Avail-in");
				tempavailoutList = new ArrayList<String>(als.get(j).getAvailIn());
//				for(int i=0; i<tempavailoutList.size();i++)
//				{
//					System.out.println(tempavailoutList.get(i));
//				}
			}
			else
			{
				tempavailoutList = new ArrayList<String>();
			}
			
			if(!(tempavailoutList.contains(als.get(j).getGenSet())))
			{
				tempavailoutList.add(als.get(j).getGenSet());
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
			ArrayList<String> avail_out = AvailableExpressionsAnalysis.findAvailOut(tempavailoutList, als.get(j).getkillSet());
//			for(int i=0; i<tempavailoutList.size();i++)
//				{
//					System.out.println(i+" "+tempavailoutList.get(i));
//				}
			als.get(j).setAvailOut(avail_out);
			if(j+1 < als.size())
			{
				als.get(j+1).setAvailIn(avail_out);
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
//			}
			
			//Avail IN
			

				avail_inSet ="{";
				if(j ==0)
				{
					avail_inSet = "{ }";
					//als.get(j).setAvailIn(new ArrayList<String>());
				}
				else
				{
					ArrayList<String> tempavailinList = new ArrayList<String>( als.get(j-1).getAvailOut());
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
				//System.out.println("J"+j);
			ArrayList<String> al = new ArrayList<String>();
			if(j == loopEnd-1)
			{			
				iteration++;
				//System.out.println("J"+j);System.out.println("Size: "+als.size());
				al = convertToStringArray(als.get(j).getLineNo(),als.get(j).getStatement(),iteration,als.get(j).getGenSet(),als.get(j).getkillSet(),avail_inSet,avail_outSet);
				
				String data[] = al.toArray(new String[al.size()]);

				dtm.addRow(data);
				for(int i=loopStart;i<=loopEnd-1;i++)
				{
					al=AvailableExpressionsAnalysis.findLoopsSets(als, i, loopStart, loopEnd);
					
					String data1[] = al.toArray(new String[al.size()]);

					dtm.addRow(data1);
				}

			}
			else
			{
				al = convertToStringArray(als.get(j).getLineNo(),als.get(j).getStatement(),iteration,als.get(j).getGenSet(),als.get(j).getkillSet(),avail_inSet,avail_outSet);	
				String data[] = al.toArray(new String[al.size()]);

				dtm.addRow(data);
			}
			
			
			
//			System.out.println(als.get(j).getLineNo()+":");
//			System.out.println("-> Statement: " + als.get(j).getStatement());
//			System.out.println("\t=> Gen Set: " + als.get(j).getGenSet());
//			System.out.println("\t=> Kill Set: " + als.get(j).getkillSet());
		}		
	}
	private void customizeComponents()
	{
		table.setFillsViewportHeight(true);
		table.setGridColor(Color.decode("#5885AF"));
		table.setRowHeight(25);
		table.setBackground(Color.decode("#274472")); //Dark Blue
		table.setForeground(Color.decode("#D5E4E7"));
		table.setFont(new Font("Consolas", Font.PLAIN, 14));
		centerDataTable();
		tableHeaderCustomizations();
		
		tpProgram.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		tpProgram.setEditable(false);
//		tpProgram.setBorder(new LineBorder(new Color(147, 167, 170), 3, true));
		tpProgram.setForeground(Color.decode("#D5E4E7"));
		tpProgram.setFont(new Font("Consolas", Font.PLAIN, 15));
		tpProgram.setBackground(Color.decode("#274472")); //Dark Blue
		tpProgram.setAlignmentX(Component.CENTER_ALIGNMENT);
		tpProgram.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		lblProgram.setForeground(Color.WHITE);
		lblProgram.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgram.setFont(new Font("Consolas", Font.BOLD, 25));
		
		lblAnalysis.setForeground(Color.WHITE);
		lblAnalysis.setFont(new Font("Consolas", Font.BOLD, 25));
		lblAnalysis.setHorizontalAlignment(SwingConstants.CENTER);

	}
	private void setGridPropertiesAll()
	{
		gbc.fill = GridBagConstraints.CENTER;
		gbc.ipady = 20;
		gbc.weightx = 0.5;
		
	}
	private void setGridPropertiesSpecific(int x, int y)
	{
		setGridPropertiesAll();
		gbc.gridx = x;
		gbc.gridy = y;
	}	
	private void tableHeaderCustomizations()
	{
		table.getTableHeader().setBackground(Color.decode("#5885AF"));
		table.getTableHeader().setForeground(Color.BLACK);
		table.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 15));
		table.getTableHeader().setBorder(new LineBorder(Color.decode("#5885AF"), 1, true));
	}
	private void centerDataTable()
	{
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
	    renderer.setHorizontalAlignment( SwingConstants.CENTER );
	}
	private void displayInTextArea(String prgm)
	{
		if(tpProgram.getText().isEmpty())
		{
			tpProgram.setText("\n\n");
		}
		tpProgram.setText(tpProgram.getText().toString()+ prgm + "\n");
	}
	private ArrayList<String> convertToStringArray(int no, String stmt, int iter,String gs, String ks, String AvailIn, String AvailOut)
	{
		ArrayList<String> tempData = new ArrayList<String>();
		
		tempData.add(String.valueOf(no));
		tempData.add(stmt);
		if(loopStart != -1 || loopEnd!= -1)
		{
			tempData.add(String.valueOf(iter));
		}
		tempData.add(FindSets.checkSetsIfNull(gs));
		tempData.add(FindSets.checkSetsIfNull(ks));
		tempData.add(AvailIn);
		tempData.add(AvailOut);
	
		return tempData;
	}
	private ArrayList<State> readFile()
	{
		try {
			//boolean loopFlag = false;
			// Creating an object of the file for reading the statement
			File file = new File(filePath);  
		
			ArrayList<State> stmts = new ArrayList<State>();

			Scanner myReader = new Scanner(file);
			int i=0;
			while (myReader.hasNextLine()) 
			{
				String stmt = myReader.nextLine();
				
				//Set Text in Text Area
				displayInTextArea((i+1 + ": "+ stmt).trim());
				
				if(FindSets.checkStatementToNotConsider(stmt))
				{
					continue;
				}
				if(FindSets.checkStatementIfLoopStarts(stmt))
				{

					loopStart = i+1;
					System.out.println(loopStart);
				}
				if(FindSets.checkStatementIfLoopEnds(stmt))
				{
					loopEnd = i+1;
					System.out.println(loopEnd);
				}
				
				String 	gen = AvailableExpressionsAnalysis.findGenSet(stmt);

				if(gen!=null)
				{
					ArrayList<String> tmp1 = FindSets.findVariablesInGenSet(gen);
					stmts.add(new State(tmp1, i+1, AvailableExpressionsAnalysis.findKillSet(stmt), gen, stmt.trim()));
				}
				else
				{
					stmts.add(new State(new ArrayList<String>(), i+1, AvailableExpressionsAnalysis.findKillSet(stmt), gen, stmt.trim()));
				}
				i++;
			}
			
			myReader.close();
			
			return stmts;
						
			} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
				
			}
		return null;
	}

	public void setFrameVisibility(boolean b)
	{
		frmDataFlowAnalysis.setVisible(b);
	}	 
}
