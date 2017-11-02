package com.siemens.bt.ists.filecompare;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

@SuppressWarnings("serial")
public class SecureConfigCompare extends JFrame {

	static File file1  ;
	static File file2 ;
	private File downloadPath;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextArea textArea;
	private  Patch patch2 ;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecureConfigCompare frame = new SecureConfigCompare();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SecureConfigCompare() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ChooseButtonActionPerformed(evt);
				textField.setText(file1.getAbsolutePath());
				
			}
		});
		btnBrowse.setBounds(320, 22, 89, 23);
		contentPane.add(btnBrowse);
		
		JLabel lblNewLabel = new JLabel("Project Config File");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lblNewLabel.setBounds(30, 66, 145, 25);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Master Data Config File");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(20, 20, 155, 25);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnBrowse_1 = new JButton("Browse...");
		btnBrowse_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseButtonActionPerformed2 (e);
			}
		});
		btnBrowse_1.setBounds(320, 68, 89, 23);
		contentPane.add(btnBrowse_1);
		
		JButton btnCompare = new JButton("Compare");
		btnCompare.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCompare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					compareFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCompare.setBounds(161, 116, 89, 23);
		contentPane.add(btnCompare);
		
		textField = new JTextField();
		textField.setBounds(175, 23, 135, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(175, 69, 135, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	
		JLabel lblNewLabel_2 = new JLabel("ResultWindow");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel_2.setBounds(40, 150, 115, 25);
		getContentPane().add(lblNewLabel_2);
		
		JButton btnDownloadFile = new JButton("Download File");
		btnDownloadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				selectDirectory(evt);

				try {
										 
					    //String content = textArea.getText();
				        String path=downloadPath.getAbsolutePath()+"\\compareResult.txt";
				        System.out.println(path);
				        File file = new File(path);

				        // If file doesn't exists, then create it
				        if (!file.exists()) {
				            file.createNewFile();
				        }
				        FileWriter fw = new FileWriter(file.getAbsoluteFile());
				        BufferedWriter bw = new BufferedWriter(fw);

				        bw.write(textArea.getText());
				        bw.newLine();
				        	                
				        // Close connection
				        bw.close();
				       				        
				}catch (IOException e) {
					// TODO: handle exception
				}
				
			}
		});
		btnDownloadFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDownloadFile.setBounds(245, 424, 124, 25);
		getContentPane().add(btnDownloadFile);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 174, 566, 245);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
	}
	
	private static List<String> fileToLines(String filename) {
	    List<String> lines = new LinkedList<String>();
	    String line = "";
	    try {
	        BufferedReader in = new BufferedReader(new FileReader(filename));
	        while ((line = in.readLine()) != null) {
	            lines.add(line);
	        }
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return lines;
	}
	
	private void compareFile() throws IOException {
		

        List<String> MasterData = fileToLines(file1.getAbsolutePath());
        List<String> ProjectData  = fileToLines(file2.getAbsolutePath());

        //// Compute diff. Get the Patch object. Patch is the container for computed deltas.
        patch2 = DiffUtils.diff(MasterData, ProjectData );
        
        int a = 0;
        int b = 0;
        
        for (Delta Compare: patch2.getDeltas()) {
        	
        	/*System.out.println(delta.getOriginal());
        	System.out.println(delta.getRevised());*/
//        	String temp=delta.getOriginal().getLines().toString();
//        	temp=temp.substring(temp.indexOf("[")+1, temp.indexOf("]"));
//        	
//        	String temp3=temp.substring(temp.indexOf("/*")-1,temp.indexOf("*/")+2);
//        	//temp=temp.replaceAll(temp3, "\n");
//        	String temp2=delta.getRevised().getLines().toString();
//        	
//        	temp2=temp2.replace("[","");
//        	temp2=temp2.replace("]","");
//        	System.out.println("MasterData:"+temp);
//        	System.out.println("MasterData:"+temp3);
//        	System.out.println("ProjectData:"+temp2);
//        	/*System.out.println("MasterData"+delta.getOriginal().getLines().toString());
        	//System.out.println("ProjectData"+delta.getRevised().getLines());*/
        	
            //System.out.println(delta);
           //textArea.append(delta + "\n");
        	
        	String Master=Compare.getOriginal().getLines().toString();
        	Master=Master.substring(Master.indexOf("[")+1, Master.indexOf("]"));
        	Master=Master.replace(", ", "\n");
        	String Project =Compare.getRevised().getLines().toString();
        	Project=Project.replace("[]","No Keyword Found");
        	        	
        	System.out.println("MasterData:"+ Master + "\n");
        	System.out.println("ProjectData:"+ Compare.getRevised().getLines() + "\n");
        	
        	textArea.append("MasterData:"+(++a)+"==>"+ Master+ "\n");
        	textArea.append("ProjectData:"+(++b)+"==>"+ Project+ "\n" + "\n");
         
            
        }
                      		
	}
	
	
	private void ChooseButtonActionPerformed(ActionEvent e) {
	    JFileChooser fileChooser = new JFileChooser();
	    int returnValue = fileChooser.showOpenDialog(null);
	    if (returnValue == JFileChooser.APPROVE_OPTION)
	    {
	        file1 = fileChooser.getSelectedFile();
	        
	        System.out.println(file1.getName());
	    }
	}
	
	
	private void ChooseButtonActionPerformed2(ActionEvent e) {
	    JFileChooser fileChooser = new JFileChooser();
	    int returnValue = fileChooser.showOpenDialog(null);
	    if (returnValue == JFileChooser.APPROVE_OPTION)
	    {
	        file2 = fileChooser.getSelectedFile();
	        textField_1.setText(file2.getAbsolutePath());
	       System.out.println(file2.getName());
	    }
	}
	
	private void selectDirectory(ActionEvent e) {
	    JFileChooser fileChooser = new JFileChooser();
	   
	    fileChooser.setDialogTitle("select Folder");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showSaveDialog(null);
		downloadPath = fileChooser.getSelectedFile();
		
	}
}
