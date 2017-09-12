import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.ResultSet;


public class DeleteSupplier extends JFrame implements ActionListener
{
	JFrame jf;
	JLabel l1,l2,l3,l4,l5,l6;
	JTextField t1,t2,t3,t4,t5;
	JButton b1,b2,b3;
	GridBagLayout gbl;
	GridBagConstraints gbc;
	Font f;
    Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
    DefaultTableModel model = new DefaultTableModel();
    JTable tabGrid = new JTable(model);
    JScrollPane scrlPane = new JScrollPane(tabGrid);

	DeleteSupplier()
	{
		jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);
		jf.setLayout(null);

	    l6=new JLabel(" Delete Supplier ");
	    l6.setFont(new Font("Times New Roman",Font.BOLD,25));
	    l6.setBounds(300,50,300,40);l6.setForeground(Color.blue);
	    jf.add(l6);

		l1= new JLabel("Supplier id*");
		//l1.setFont(f);
 		l1.setBounds(150,120,130,25);
		jf.add(l1);

		t1=new JTextField(20);
		t1.setBounds(320,120,100,25);t1.setToolTipText("Enter supplier id to delete supplier");
		jf.add(t1);

		l2 = new JLabel("Supplier name*");
		//l2.setFont(f);
  		l2.setBounds(150,160,170,25);
		jf.add(l2);

		t2=new JTextField(20);
		t2.setBounds(320,160,200,25);t2.setToolTipText("Enter supplier name to delete supplier");
		jf.add(t2);

		b1 = new JButton("Delete",new ImageIcon("images//delete.png"));
		b1.setBounds(300,330,120,35);b1.setToolTipText("click to delete supplier details");
		jf.add(b1);b1.addActionListener(this);

		b2 = new JButton("Clear",new ImageIcon("images//clear.png"));
		b2.setBounds(450,330,100,35);b2.setToolTipText("click to clear all textfields");
		jf.add(b2); b2.addActionListener(this);

     	b3 = new JButton("All",new ImageIcon("images//all.png"));
		b3.setBounds(600,330,100,35);b3.setToolTipText("click to view all supplier details");
		jf.add(b3); b3.addActionListener(this);

		scrlPane.setBounds(0,380,900,600);
		jf.add(scrlPane);
		tabGrid.setFont(new Font ("Times New Roman",0,15));

		model.addColumn("S_ID");
		model.addColumn("S_NAME");
		model.addColumn("S_ADDRESS");
		model.addColumn("S_PHONENO");
		model.addColumn("S_EMAILID");

		jf.setTitle("Delete Supplier");
		jf.setSize(900,700);
		jf.setLocation(20,20);
		jf.setResizable(false);
		jf.getContentPane().setBackground(Color.cyan);
		jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
	
		if(ae.getSource()==b1)
		{//delete

			String id=t1.getText();
			Pattern p1=Pattern.compile("[0-9]+");
			Matcher m1=p1.matcher(id);
			boolean matchFound1=m1.matches();

	
			String name=t2.getText();
			Pattern p2=Pattern.compile("[a-zA-Z]+[ ]*[a-zA-Z]*");
			Matcher m2=p2.matcher(name);
			boolean matchFound2=m2.matches();

			if(((t1.getText()).equals(""))||((t2.getText()).equals("")))
			{
				JOptionPane.showMessageDialog(this,"Please enter supplier id and name !","Warning!!!",JOptionPane.ERROR_MESSAGE);
			}

			else if(!matchFound1)
			{
		    	JOptionPane.showMessageDialog(this,"Invalid id!","Warning!!!",JOptionPane.WARNING_MESSAGE);
			}

			else if(!matchFound2)
       		{
 	     		JOptionPane.showMessageDialog(this,"Invalid Name!","Warning!!!",JOptionPane.WARNING_MESSAGE);
			}

			else
			{
				try
				{
				    Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","root");
					System.out.println("Connected to database.");
				    ps=con.prepareStatement("delete from supplier where sid='"+t1.getText()+"' or sname='"+t2.getText()+"'");
				    ps.executeUpdate();
			        JOptionPane.showMessageDialog(null,"Record is deleted.");
			        t1.setText("");
			        t2.setText("");
			        t3.setText(""); 
			        t4.setText("");
			        t5.setText("");
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
		}
     
		else if(ae.getSource()==b2)
		{
	          t1.setText("");
	          t2.setText("");
	          t3.setText("");
	          t4.setText("");
	          t5.setText("");
		}

		else if(ae.getSource()==b3)
		{//list
			int r = 0;
			try
			{
		     	Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","root");
				System.out.println("Connected to database.");
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		        rs = stmt.executeQuery("SELECT * from supplier" );

          		while(rs.next())
            	{
            		model.insertRow(r++, new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5) });
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
 	}

	public static void main(String args[])
	{
	      new DeleteSupplier();
	}
}

