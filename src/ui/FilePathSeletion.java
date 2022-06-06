package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class FilePathSeletion {

	private JFrame frame;
	private JTextField tfPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilePathSeletion window = new FilePathSeletion();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FilePathSeletion() {
		initialize();
	}
 
	/**
	 * Initialize the contents of the frame.
	 */
	//D:\Projects\EclipseProjects\Data-Flow-Analysis-File\Files\Test3.txt
	//D:\Projects\EclipseProjects\Data-Flow-Analysis-File - Loop\Files\TestLoop3.txt
	//D:\Projects\EclipseProjects\Data-Flow-Analysis-File - Loop\Files\Test1.txt
	//D:\Projects\EclipseProjects\Data-Flow-Analysis-File - Loop\Files\TestLoop1.txt
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#274472")); //Dark Blue
		frame.setResizable(false);
		frame.setBounds(100, 100, 541, 227);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Label
		JLabel lblpath = new JLabel("Enter Path of the File to Analyze");
		lblpath.setFocusable(false);
		lblpath.setHorizontalAlignment(SwingConstants.CENTER);
		lblpath.setFont(new Font("Consolas", Font.BOLD, 21));
		lblpath.setForeground(Color.WHITE);
		lblpath.setBackground(Color.decode("#DADBDE"));
		lblpath.setBounds(10, 22, 507, 25);
		frame.getContentPane().add(lblpath);
		
		//TextField
		tfPath = new JTextField();
		tfPath.setBorder(new LineBorder(Color.decode("#5885AF"), 1, true)); //Blue Gray
		tfPath.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		tfPath.setBackground(Color.decode("#D5E4E7")); //Baby Blue
		tfPath.setForeground(Color.BLACK);
		tfPath.setFont(new Font("Consolas", Font.BOLD, 13));
		tfPath.setBounds(10, 64, 507, 40);
		frame.getContentPane().add(tfPath);
		tfPath.setColumns(10);
		
		JButton btnNext = new JButton("ANALYZE");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String path = tfPath.getText().trim();
				if(path.isBlank())
				{
					JOptionPane.showMessageDialog(null,"You cannot Leave the field Empty","EMPTY", JOptionPane.WARNING_MESSAGE);
				}
				else if(validatePath(path))
				{
					DataFlowAnalysis obj = new DataFlowAnalysis(path);
					frame.setVisible(false);
					obj.setFrameVisibility(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null,"No Such File Exists in the System!","ERROR 404 - Not Found", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNext.setBackground(Color.decode("#5885AF"));
		btnNext.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNext.setBorder(new LineBorder(Color.WHITE, 1, true));
		btnNext.setFont(new Font("Consolas", Font.BOLD, 18));
		btnNext.setForeground(Color.decode("#D5E4E7"));
		btnNext.setBounds(406, 127, 111, 40);
		frame.getContentPane().add(btnNext);
	}
	private boolean validatePath(String link)
	{
		File f = new File(link);
		if(f.exists() && !f.isDirectory())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
