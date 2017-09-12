import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class NewMedReg extends JFrame implements ActionListener 
{
	JFrame jf;
	JButton b1,b2;
	JLabel l1,l2,l3,l4,l5;
	JTextField t1;
	JPasswordField p1,p2;
	Font f;

	NewMedReg()
	{
        jf=new JFrame();
        f = new Font("Times New Roman",Font.BOLD,20);
		jf.setLayout(null);

        l1 = new JLabel("Enter details");
        l1.setFont(new Font("Times New Roman",Font.BOLD,30));
        l1.setBounds(300,100,300,40);
		jf.add(l1);

		l2 = new JLabel("Address: ");
		l2.setFont(f);
		l2.setBounds(200,250,200,25);
		jf.add(l2);

		t1 = new JTextField(100);
		t1.setBounds(350,250,200,25);
		t1.setToolTipText("Enter Medical Address");
		jf.add(t1);

		l3 = new JLabel("Password: "); 
		l3.setFont(f);
		l3.setBounds(200,300,200,25);
		jf.add(l3);

		p1 = new JPasswordField(20);
		p1.setBounds(350,300,200,25);
		p1.setToolTipText("Enter Password");
		jf.add(p1);

		l4 = new JLabel("Confirm Password: "); 
		l4.setFont(f);
		l4.setBounds(200,350,150,25);
		jf.add(l4);

		p2 = new JPasswordField(20);
		p2.setBounds(350,350,200,25);
		p2.setToolTipText("Confirm Password");
		jf.add(p2);

		b1 = new JButton("Create account");
		b1.setBounds(200,400,150,35);
		jf.add(b1);
		b1.addActionListener(this);

		b2 = new JButton("Clear");
		b2.setBounds(370,400,100,35);
		jf.add(b2);
		b2.addActionListener(this);

		jf.setTitle("Register");
		jf.setLocation(20,20);
		jf.setSize(800,600);
		jf.setResizable(false);
		jf.getContentPane().setBackground(Color.cyan);
		jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource()==b1)
		{
			String pw1, pw2;
			pw1 = new String(p1.getPassword());
			pw2 = new String (p2.getPassword());
			
			if ( pw1.equals(pw2) )
			{
			  	try
			  	{
			  		int medid=0;

					Class.forName("com.mysql.jdbc.Driver");
			        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","");
					System.out.println("Connected to database.");

					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = stmt.executeQuery("select max(medid) from Medical" );
					
					if(rs.next()) medid = rs.getInt(1);
					medid++;

	                    PreparedStatement ps=con.prepareStatement("insert into Medical (medid,medAddress,password)values(?,?,?)");
	                    ps.setInt(1,medid);
	                    ps.setString(2,t1.getText());
			            ps.setString(3,p2.getText());
			  			ps.executeUpdate();

		  			JOptionPane.showMessageDialog(jf,"Your login ID id: " + medid,"Added Medical",JOptionPane.INFORMATION_MESSAGE);

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

			else 
			{
				JOptionPane.showMessageDialog(null,"Passwords doesn't match","Error",JOptionPane.WARNING_MESSAGE);	
			}
		}

		else if (ae.getSource()==b2) 
		{
			t1.setText("");
			p1.setText("");
			p2.setText("");
		}
	}

	public static void main(String args[])
	{
		new NewMedReg();
	}
}