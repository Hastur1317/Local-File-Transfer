package edu.cpp.cs.cs380;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SpinnerNumberModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI implements ActionListener {

	private File file = null;
	private File key = null;
	JFrame frmFileTransfer;
	private JTextField txtUserName;
	private JTextField txtPassword;
	private JButton btnFile;
	private JLabel lblfile;
	private JButton btnKey;
	private JLabel lblkey;
	private JRadioButton rbSender;
	private JRadioButton rbReceiver;
	private JButton btnConnect;
	private JSpinner spByteSize;
	private JCheckBox chkASCII;
	private JSpinner spPort;
	private JTextField txtIP;
	private JMenuItem itemProfile;
	private JTextArea txtOutput;
	private JTextField txtFile;
	private JLabel lblFileName;
	private UpdatingText run;

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFileTransfer = new JFrame();
		frmFileTransfer.setResizable(false);
		frmFileTransfer.setTitle("File Transfer");
		frmFileTransfer.setBounds(100, 100, 614, 528);
		frmFileTransfer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFileTransfer.getContentPane().setLayout(null);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(37, 41, 109, 20);
		frmFileTransfer.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(37, 97, 109, 20);
		frmFileTransfer.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		btnFile = new JButton("Choose File");
		btnFile.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnFile.setBounds(184, 40, 97, 23);
		frmFileTransfer.getContentPane().add(btnFile);
		
		btnKey = new JButton("Choose Key");
		btnKey.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnKey.setBounds(184, 96, 97, 23);
		frmFileTransfer.getContentPane().add(btnKey);
		
		lblfile = DefaultComponentFactory.getInstance().createLabel("No File Selected");
		lblfile.setBounds(194, 74, 393, 14);
		frmFileTransfer.getContentPane().add(lblfile);
		
		lblkey = DefaultComponentFactory.getInstance().createLabel("No File Selected");
		lblkey.setBounds(194, 130, 393, 14);
		frmFileTransfer.getContentPane().add(lblkey);
		
		chkASCII = new JCheckBox("ASCII Armoring");
		chkASCII.setBounds(37, 136, 127, 23);
		frmFileTransfer.getContentPane().add(chkASCII);
		
		spByteSize = new JSpinner();
		spByteSize.setModel(new SpinnerNumberModel(1, 1, 1024, 1));
		spByteSize.setBounds(37, 196, 89, 20);
		frmFileTransfer.getContentPane().add(spByteSize);
		
		JLabel lblKB = DefaultComponentFactory.getInstance().createLabel("KB ");
		lblKB.setBounds(136, 199, 32, 14);
		frmFileTransfer.getContentPane().add(lblKB);
		
		ButtonGroup bg = new ButtonGroup();
		
		rbSender = new JRadioButton("Sender");
		rbSender.setSelected(true);
		rbSender.setBounds(184, 169, 109, 23);
		frmFileTransfer.getContentPane().add(rbSender);
		
		rbReceiver = new JRadioButton("Receiver");
		rbReceiver.setBounds(184, 195, 109, 23);
		frmFileTransfer.getContentPane().add(rbReceiver);
		
		bg.add(rbReceiver);
		bg.add(rbSender);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(258, 296, 97, 23);
		frmFileTransfer.getContentPane().add(btnConnect);
		
		JLabel lblUsername = DefaultComponentFactory.getInstance().createLabel("UserName");
		lblUsername.setBounds(37, 21, 92, 14);
		frmFileTransfer.getContentPane().add(lblUsername);
		
		JLabel lbPassword = DefaultComponentFactory.getInstance().createLabel("Password");
		lbPassword.setBounds(37, 72, 92, 14);
		frmFileTransfer.getContentPane().add(lbPassword);
		
		JLabel lblSize = DefaultComponentFactory.getInstance().createLabel("Size of Chunks:");
		lblSize.setBounds(37, 173, 92, 14);
		frmFileTransfer.getContentPane().add(lblSize);
		
		txtIP = new JTextField();
		txtIP.setBounds(19, 247, 127, 20);
		frmFileTransfer.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblIpAddress = DefaultComponentFactory.getInstance().createLabel("IP Address");
		lblIpAddress.setBounds(47, 227, 92, 14);
		frmFileTransfer.getContentPane().add(lblIpAddress);
		
		spPort = new JSpinner();
		spPort.setModel(new SpinnerNumberModel(4444, 1024, 49152, 1));
		spPort.setBounds(160, 247, 63, 20);
		frmFileTransfer.getContentPane().add(spPort);
		
		JLabel lblPort = DefaultComponentFactory.getInstance().createLabel("Port");
		lblPort.setBounds(149, 227, 92, 14);
		frmFileTransfer.getContentPane().add(lblPort);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 330, 588, 137);
		frmFileTransfer.getContentPane().add(scrollPane);
		
		txtOutput = new JTextArea();
		txtOutput.setText("*System Progress*\r\n");
		txtOutput.setEditable(false);
		scrollPane.setViewportView(txtOutput);
		
		lblFileName = DefaultComponentFactory.getInstance().createLabel("File Name");
		lblFileName.setEnabled(false);
		lblFileName.setBounds(19, 278, 92, 14);
		frmFileTransfer.getContentPane().add(lblFileName);
		
		txtFile = new JTextField();
		txtFile.setEnabled(false);
		txtFile.setBounds(19, 297, 127, 20);
		frmFileTransfer.getContentPane().add(txtFile);
		txtFile.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frmFileTransfer.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		itemProfile = new JMenuItem("Add Profile");
		mnFile.add(itemProfile);
		
		rbSender.addActionListener(this);
		rbReceiver.addActionListener(this);
		
		itemProfile.addActionListener(this);
		btnConnect.addActionListener(this);
		btnFile.addActionListener(this);
		btnKey.addActionListener(this);
	}

	//action method
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		int fileOption;
		JFileChooser picker = new JFileChooser();
		
		if(ae.getSource() == itemProfile)
		{
			writeProfile();
		}
		else if(ae.getSource() == rbSender)
		{
			btnFile.setEnabled(true);
			txtIP.setEnabled(true);
			lblfile.setVisible(true);
			lblFileName.setEnabled(false);
			txtFile.setEnabled(false);
			spByteSize.setEnabled(true);
		}
		else if(ae.getSource() == rbReceiver)
		{
			btnFile.setEnabled(false);
			txtIP.setEnabled(false);
			lblfile.setVisible(false);
			lblFileName.setEnabled(true);
			txtFile.setEnabled(true);
			spByteSize.setEnabled(false);
		}
		else if(ae.getSource() == btnFile)
		{
			fileOption = picker.showOpenDialog(frmFileTransfer);
			if(fileOption == 0)
			{
				file = picker.getSelectedFile();
				lblfile.setText(file.getPath());
			}
			else
			{
				file = null;
				lblfile.setText("No File Selected");
			}
		}
		else if(ae.getSource() == btnKey)
		{
			fileOption = picker.showOpenDialog(frmFileTransfer);
			if(fileOption == 0)
			{
				key = picker.getSelectedFile();
				lblkey.setText(key.getPath());
			}
			else
			{
				key = null;
				lblkey.setText("No File Selected");
			}
		}
		else 
		{
			//makes a connection
			Manager man;
			if(rbSender.isSelected())
			{
				if(txtUserName.getText().matches(""))
				{
					JOptionPane.showMessageDialog(frmFileTransfer, "No Username!");
					return;
				}
				if(txtUserName.getText().matches(""))
				{
					JOptionPane.showMessageDialog(frmFileTransfer, "No Password!");
					return;
				}
				if(file == null)
				{
					JOptionPane.showMessageDialog(frmFileTransfer, "No File to transfer");
					return;
				}
				if(key == null)
				{
					JOptionPane.showMessageDialog(frmFileTransfer, "No Key File Selected");
					return;
				}
				if(txtUserName.getText().length() + txtPassword.getText().length() >500)
				{
					JOptionPane.showMessageDialog(frmFileTransfer, "Username and password too long");
					return;
				}
				man = new Manager(txtUserName.getText(), txtPassword.getText(), file, key, (int) spByteSize.getValue(), chkASCII.isSelected());
				run = new UpdatingText(man, true, txtIP.getText(), (int) spPort.getValue(), txtOutput);
				new Thread(){
				    public void run() {
				        //initiate  the bot:
				        try {
				            run.run();
				        } catch (Exception e) {
				            e.printStackTrace();
				        }

				    }}.start();
				//man.SendFile(txtIP.getText(), (int) spPort.getValue(), txtOutput);
			}
			else
			{
				if(txtFile.getText().matches(""))
				{
					txtFile.setText("copy");
				}
				if(key == null)
				{
					JOptionPane.showMessageDialog(frmFileTransfer, "No Key File Selected");
					return;
				}
				man = new Manager(txtUserName.getText(), txtPassword.getText(), key, chkASCII.isSelected(), txtFile.getText());
				run = new UpdatingText(man, false, "", (int) spPort.getValue(), txtOutput);
				
				new Thread(){
				    public void run() {
				        //initiate  the bot:
				        try {
				            run.run();
				        } catch (Exception e) {
				            e.printStackTrace();
				        }

				    }}.start();
				
				//run.run(
				//man.RecieveFile((int) spPort.getValue(), txtOutput);
			}
		}
	}

	//adds a username and password
	private void writeProfile()
	{
		String user = txtUserName.getText() + txtPassword.getText();
		if(user.length() >60)
		{
			JOptionPane.showMessageDialog(frmFileTransfer, "Username and password too long");
			return;
		}
		FileInterface f;
		try {
			f = new FileInterface(new File("profiles.txt"));
			f.addProfile(txtUserName.getText(), txtPassword.getText());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class UpdatingText extends Thread
{
	private Manager man;
	private boolean sender;
	private String ip;
	private int port;
	private JTextArea txtOutput;
	
	
	public UpdatingText(Manager man, boolean sender,String ip,int port, JTextArea txtOutput)
	{
		this.man = man;
		this.sender = sender;
		this.ip = ip;
		this.port = port;
		this.txtOutput = txtOutput;
	}
	public void run()
	{
		if(sender)
		{
			man.SendFile(ip, port, txtOutput);
		}
		else
		{
			man.RecieveFile(port, txtOutput);
		}
	}
	
}
