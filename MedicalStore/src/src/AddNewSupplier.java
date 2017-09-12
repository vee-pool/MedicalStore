import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.ResultSet;

public class AddNewSupplier extends JFrame implements ActionListener
{
	JFrame jf;
	JTextField t1,t2,t3,t4,t5,tr,tq;
	JLabel l1,l2,l3,l4,l5,l6;
	JButton b0,b1,b2;
	Font f;
    Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
    int medicalID;

	AddNewSupplier(int id)
	{
		medicalID = id;

		jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);
		jf.setLayout(null);

	    l6=new JLabel("New Supplier details");
	    l6.setFont(new Font("Times New Roman",Font.BOLD,25));
	    l6.setBounds(250,50,300,40);l6.setForeground(Color.blue);
	    jf.add(l6);

		l1 = new JLabel("Supplier ID*");
        l1.setBounds(150,120,170,25);
		jf.add(l1);

		l2 = new JLabel("Supplier name*");
        l2.setBounds(150,160,170,25);
		jf.add(l2);

		t2=new JTextField(20);
		t2.setBounds(320,160,200,25);t2.setToolTipText("Enter supplier name");
		jf.add(t2);

		l3 = new JLabel("Supplier address*");
		l3.setBounds(150,200,170,25);
		jf.add(l3);

		t3=new JTextField(20);
		t3.setBounds(320,200,250,25);t3.setToolTipText("Enter supplier address");
		jf.add(t3);

		l4 = new JLabel("Supplier phone no*");
        l4.setBounds(150,240,170,25);
		jf.add(l4);

		t4=new JTextField(20);
		t4.setBounds(320,240,100,25);t4.setToolTipText("Enter supplier phone no");
		jf.add(t4);

		l5 = new JLabel("Supplier email id*");
        l5.setBounds(150,280,170,25);
		jf.add(l5);

		t5=new JTextField(20);
		t5.setBounds(320,280,200,25);t5.setToolTipText("Enter supplier email id");
		jf.add(t5);

		b0 = new JButton("Save",new ImageIcon("images//save.png"));
        b0.setBounds(150,330,110,35);b0.setToolTipText("click to save supplier details");
		jf.add(b0);b0.addActionListener(this);

		b1 = new JButton("Clear",new ImageIcon("images//clear.png"));
		b1.setBounds(300,330,110,35);b1.setToolTipText("click to clear all textfilds");
        jf.add(b1); b1.addActionListener(this);  
	            
		

	    jf.setTitle("Add New Supplier");
	    jf.setSize(900,700);
		jf.setLocation(20,20);
        jf.setResizable(false);
	    jf.getContentPane().setBackground(Color.cyan);
	    jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==b0)
		{


			String name=t2.getText();
			Pattern p2=Pattern.compile("[a-zA-Z]+[ ]*[a-zA-Z]*");
		    Matcher m2=p2.matcher(name);
			boolean matchFound2=m2.matches();

			String addr=t3.getText();
			Pattern p3=Pattern.compile("[a-zA-Z]+[ ]*[a-zA-Z]*");
			Matcher m3=p3.matcher(addr);
			boolean matchFound3=m3.matches();


			String mob=t4.getText();
			Pattern p4=Pattern.compile("\\d{10}"); 
		    Matcher m4=p4.matcher(mob);
		    boolean matchFound4=m4.matches();

			String email=t5.getText();
		  	Pattern p5=Pattern.compile("[a-zA-Z]+[0-9]*[._]*[a-zA-Z]*[0-9]*[._]*@[a-zA-Z0-9]+.[a-zA-Z0-9]+");
		    Matcher m5=p5.matcher(email);
		    boolean matchFound5=m5.matches();

	    	if(((t2.getText()).equals(""))||((t3.getText()).equals(""))||((t4.getText()).equals(""))||((t5.getText()).equals("")))
		    {
			    JOptionPane.showMessageDialog(this,"* Detail are Missing !","Warning!!!",JOptionPane.WARNING_MESSAGE);
		    }


		    else if(!matchFound2)
		    {
		        JOptionPane.showMessageDialog(this,"Invalid Name!","Warning!!!",JOptionPane.WARNING_MESSAGE);
	     	}


	        else if(!matchFound3)
		    {
		        JOptionPane.showMessageDialog(this,"Invalid Address!","Warning!!!",JOptionPane.WARNING_MESSAGE);
	     	}

            else if(!matchFound4)
		    {
		        JOptionPane.showMessageDialog(this,"Invalid phone no!","Warning!!!",JOptionPane.WARNING_MESSAGE);
	     	} 
	     	    
	     	    	                
	        else if(!matchFound5)
		    {
		       	  JOptionPane.showMessageDialog(this,"Invalid email id!","Warning!!!",JOptionPane.WARNING_MESSAGE);
		    }
	                
	                
			else
			{
			  try
			  	{
			  		int sid=0;

					Class.forName("com.mysql.jdbc.Driver");
			        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","");
					System.out.println("Connected to database.");

						stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						rs = stmt.executeQuery("select max(sid) from supplier" );
						if(rs.next()) sid = rs.getInt(1);
						sid++;

	                    ps=con.prepareStatement("insert into supplier (sid,sname,saddress,sphoneno,semailid)values(?,?,?,?,?)");
	                    ps.setInt(1,sid);
	                    ps.setString(2,t2.getText());
			            ps.setString(3,t3.getText());
			            ps.setString(4,t4.getText());
				        ps.setString(5,t5.getText());
			  			ps.executeUpdate();

	                    ps=con.prepareStatement("insert into medsupp (medID, sid)values(?,?)");
	                    ps.setInt(1,medicalID);
	                    ps.setInt(2,sid);
			  			ps.executeUpdate();

		  			JOptionPane.showMessageDialog(null,"Supplier added successfully.","Added Supplier",JOptionPane.INFORMATION_MESSAGE);
		  			jf.setVisible(false);

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

		else if(ae.getSource()==b1)
	    {//clear
	          //t1.setText("");
	          t2.setText("");
	          t3.setText("");
	          t4.setText("");
	          t5.setText("");
	    }

	}

}