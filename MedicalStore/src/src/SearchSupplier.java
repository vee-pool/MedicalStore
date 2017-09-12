import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.ResultSet;

public class SearchSupplier extends JFrame implements ActionListener
{
	JFrame jf;
	JTextField t1,t2,t3,t4,t5;
	JLabel l1,l2,l3,l4,l5,l6;
	JButton b0,b1,b2;
	Font f;
    Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	DefaultTableModel model = new DefaultTableModel();
    JTable tabGrid = new JTable(model);
    JScrollPane scrlPane = new JScrollPane(tabGrid);
    int medicalID;

	SearchSupplier(int medId)
	{
		medicalID = medId;
		jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);
		jf.setLayout(null);

		l2 = new JLabel("Search by name *");
		//l2.setFont(f);
  		l2.setBounds(150,50,170,25);
		jf.add(l2);

		t2=new JTextField(20);
		t2.setBounds(320,50,200,25);t2.setToolTipText("Enter supplier name to search supplier");
		jf.add(t2);

		/*l3 = new JLabel("Supplier address");
		//l3.setFont(f);
   		l3.setBounds(150,200,170,25);
		jf.add(l3);

		t3=new JTextField(20);
		t3.setBounds(320,200,250,25);jf.add(t3);

		l4 = new JLabel("Supplier phone no");
		//l4.setFont(f);
   		l4.setBounds(150,240,170,25);
		jf.add(l4);

		t4=new JTextField(20);
		t4.setBounds(320,240,100,25);
		jf.add(t4);

		l5 = new JLabel("Supplier email id");
		//l5.setFont(f);
   		l5.setBounds(150,280,170,25);
		jf.add(l5);

		t5=new JTextField(20);
		t5.setBounds(320,280,200,25);
		jf.add(t5);*/


        b0 = new JButton("Search",new ImageIcon("images//search.png"));
        b0.setBounds(150,110,110,35);b0.setToolTipText("click to open supplier details");
		jf.add(b0);b0.addActionListener(this);

		b1 = new JButton("Clear",new ImageIcon("images//clear.png"));
		b1.setBounds(300,110,110,35);b1.setToolTipText("click to clear all textfields");
	    jf.add(b1); b1.addActionListener(this);

        /*b2= new JButton("All",new ImageIcon("images//all.png"));
		b2.setBounds(450,330,110,35);b2.setToolTipText("click to view all supplier details");
		jf.add(b2); b2.addActionListener(this);*/

      	scrlPane.setBounds(0,180,900,600);
      	jf.add(scrlPane);
      	tabGrid.setFont(new Font ("Times New Roman",0,15));

      	model.addColumn("S_ID");
      	model.addColumn("S_NAME");
      	model.addColumn("S_ADDRESS");
      	model.addColumn("S_PHONENO");
      	model.addColumn("S_EMAILID");

	    jf.setTitle("Search Supplier");
	    jf.setSize(900,700);
		jf.setLocation(20,20);
		jf.setResizable(false);
		jf.getContentPane().setBackground(Color.cyan);
	    jf.setVisible(true);

	}

 	public void actionPerformed(ActionEvent ae)
    {
	    if(ae.getSource()==b0)
		{//fetch

			String name=t2.getText();
			Pattern p2=Pattern.compile("[a-zA-Z]+[ ]*[a-zA-Z]*");
			Matcher m2=p2.matcher(name);
			boolean matchFound2=m2.matches();

			if((t2.getText()).equals(""))
			{
	    		 JOptionPane.showMessageDialog(this,"Please enter supplier name !","Warning!!!",
	    		 								JOptionPane.WARNING_MESSAGE);
	    		 return;
			}                   						        

			try
			{
				int foundrec = 0, r=0;
			    Class.forName("com.mysql.jdbc.Driver");
			    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","");
				System.out.println("Connected to database.");

			    {
			    	ps=con.prepareStatement("select * from supplier where sname='"
			    							+t2.getText()+"'and sid IN (select sid from medsupp where medId='"
			    							+medicalID+ "' )");	
				}
				    
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					model.insertRow(r++, new Object[]{rs.getString(1),rs.getString(2),
										rs.getString(3),rs.getString(4),rs.getString(5) });
					foundrec = 1;
				} 
				
				if (foundrec == 0)
				{
					JOptionPane.showMessageDialog(null,"Record is not available","Dialog",JOptionPane.WARNING_MESSAGE);
				}
									 
				con.close();
			}
	  
			catch(SQLException se)
			{
				System.out.println(se);
			   	JOptionPane.showMessageDialog(null,"SQL Error:"+se);
			}
			
			catch(Exception e)
			{
				System.out.println(e);
				JOptionPane.showMessageDialog(null,"Error:"+e);
			}
		}

		if(ae.getSource()==b1)
		{//clear
	    	t2.setText("");
		}

	}

	/*public static void main(String args[])
	{
		new SearchSupplier();
	}*/
}

