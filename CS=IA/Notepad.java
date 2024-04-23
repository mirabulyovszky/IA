import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;

public class Notepad extends KeyAdapter implements ActionListener, KeyListener{
	static int active = 0;
	static int fSize = 17;


	JFrame baseFrame;
	JMenuBar menuBar;
	JMenu file, edit, format, view;
	JMenuItem newDoc, openDoc, exit, save, saveAs, remove, fontFamily, fontStyle, fontSize, status;
	JTextArea mainText;
	JTextField title;
	Font baseFont;
	JPanel bottom;
	JLabel info;
	JList familyList, styleList, sizeList;
	JFileChooser chooser;
	FileReader reader;

	String[] familyValue = {"Agency FB","Antiqua","Architect","Arial","Calibri","Comic Sans","Courier","Cursive","Impact","Serif"};
	String[] sizeValue = {"5","10","15","20","25","30","35","40","45","50","55","60","65","70"};
	int[] styleValue = {Font.PLAIN, Font.BOLD, Font.ITALIC};
	String[] styleValues ={"PLAIN", "BOLD", "ITALIC"};
	String fFamily, fSizeStr;
	int fStyle;
	int length;
	int lines;
	String tle;
	String fileName = "New Document";
	//JScrollPane scrollPane;
	//JScrollBar vScrollBar;
	//JScrollBar hScrollBar;

	Notepad(){
		chooser = new JFileChooser();
		reader = new FileReader();

		baseFrame = new JFrame("New Document");
		baseFont = new Font("Arial",Font.PLAIN,17);

		bottom = new JPanel();

		info = new JLabel();

		familyList = new JList(familyValue);
		styleList = new JList(styleValues);
		sizeList = new JList(sizeValue);

		familyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		styleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		bottom.add(info);

		//scrollPane = new JScrollPane(mainText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//vScrollBar = scrollPane.getVerticalScrollBar();
		//hScrollBar = scrollPane.getHorizontalScrollBar();

		title = new JTextField(100);

		//mainText
		mainText = new JTextArea();
		mainText.setMargin(new Insets(5,5,5,5));
		mainText.setFont(baseFont);
		baseFrame.add(mainText);

		//menuBar
		menuBar = new JMenuBar();
		baseFrame.setJMenuBar(menuBar);

		//menu tabs
		file = new JMenu("File");
		edit = new JMenu("Edit");
		format = new JMenu("Format");
		view = new JMenu("View");
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(format);
		menuBar.add(view);

		//menu items within "file"
		newDoc = new JMenuItem("New Document");
		openDoc = new JMenuItem("Open Document");
		save = new JMenuItem("Save Document");
		saveAs = new JMenuItem("Save As Document");
		exit = new JMenuItem("Exit");
		file.add(newDoc);
		file.add(openDoc);
		file.add(save);
		file.add(saveAs);
		file.add(exit);

		//menu items within "edit"
		remove = new JMenuItem("Remove Document");
		edit.add(remove);

		//menu items within "format"
		fontFamily = new JMenuItem("Set Font Family");
		fontStyle = new JMenuItem("Set Font Style");
		fontSize = new JMenuItem("Set Font Size");
		format.add(fontFamily);
		format.add(fontStyle);
		format.add(fontSize);

		//menu items within "view"
		status = new JMenuItem("Status");
		view.add(status);

		baseFrame.add(bottom, BorderLayout.SOUTH);
		//baseFrame.add(scrollPane, BorderLayout.CENTER);

		//actionListeners for "file"
		newDoc.addActionListener(this);
		openDoc.addActionListener(this);
		save.addActionListener(this);
		saveAs.addActionListener(this);
		exit.addActionListener(this);

		//actionListeners for "edit"
		remove.addActionListener(this);

		//actionListeners for "view"
		status.addActionListener(this);

		//actionListeners for "format"
		fontFamily.addActionListener(this);
		fontSize.addActionListener(this);
		fontStyle.addActionListener(this);

		//keyListener for typing
		mainText.addKeyListener(this);

		//baseFrame details
		baseFrame.setSize(1000,800);
		baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		baseFrame.setLocationRelativeTo(null);
		baseFrame.setVisible(true);
	}

	public void saveFile(){
		try{
			FileOutputStream saveFile = new FileOutputStream(fileName +".txt");
			String s = mainText.getText();
			for(int i = 0;i<s.length();i++){
				saveFile.write(s.charAt(i));
			}
			saveFile.close();
		}
		catch (Exception e){
			System.out.println("Something went wrong.");
		}
	}

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == newDoc){
			baseFrame.setTitle("New Document.txt");
			mainText.setText("");
			title.setText("");
		}
		else if(evt.getSource() == openDoc){
			if(title.getText().isEmpty()){
				fileName = JOptionPane.showInputDialog(null,
						"Enter Your File Title",
						"Your File Name",
						JOptionPane.QUESTION_MESSAGE);
				title.setText(fileName);
				tle = title.getText();
				saveFile();
			}
			JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = chooser.showOpenDialog(null);
			if(returnValue == JFileChooser.APPROVE_OPTION){
				//baseFrame.setTitle("New Document.txt");
				//mainText.setText("");
				//title.setText("");

			}
			else{
				JOptionPane.showMessageDialog(null, "Error");
			}
		}
		else if(evt.getSource() == save){
			title.setText(fileName);
			tle = title.getText();
			saveFile();
		}
		else if(evt.getSource() == saveAs){
			if(title.getText().isEmpty()){
				fileName = JOptionPane.showInputDialog(null,
						"Enter Your File Title",
						"Your File Name",
						JOptionPane.QUESTION_MESSAGE);
				title.setText(fileName);
				tle = title.getText();
				saveFile();
			}
		}
		else if(evt.getSource() == exit){
			baseFrame.dispose();
		}
		else if(evt.getSource() == remove){
			mainText.setText("");
			JOptionPane.showMessageDialog(null, "Removed");
		}
		else if(evt.getSource() == fontFamily){
				JOptionPane.showConfirmDialog(null, familyList, "Choose Font Family", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				fFamily = String.valueOf(familyList.getSelectedValue());
				baseFont = new Font(fFamily, fStyle, fSize);
				mainText.setFont(baseFont);
		}
		else if(evt.getSource() == fontStyle){
				JOptionPane.showConfirmDialog(null, styleList, "Choose Font Style", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				fStyle = styleValue[styleList.getSelectedIndex()];
				baseFont = new Font(fFamily, fStyle, fSize);
				mainText.setFont(baseFont);
		}
		else if(evt.getSource() == fontSize){
				JOptionPane.showConfirmDialog(null, sizeList, "Choose Font Size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				fSizeStr = String.valueOf(sizeList.getSelectedValue());
				fSize = Integer.parseInt(fSizeStr);
				baseFont = new Font(fFamily, fStyle, fSize);
				mainText.setFont(baseFont);
		}
		else if(evt.getSource() == status){

		}

	}
	
	public void keyTyped(KeyEvent ke){
		length = mainText.getText().length();
		lines = mainText.getLineCount();
		info.setText("Length: " + length + " Lines: " + lines);
	}

}
