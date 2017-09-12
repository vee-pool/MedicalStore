import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.ResultSet;

public class SearchMedicine extends JFrame implements ActionListener
{
	int medicalID;
	JFrame jf;
	JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,ln;
    JButton b0,b1,b2;
    JComboBox msname;
    String s;
	Font f;
    Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	DefaultTableModel model = new DefaultTableModel();
    JTable tabGrid = new JTable(model);
    JScrollPane scrlPane = new JScrollPane(tabGrid);

	SearchMedicine(int medID)
	{
		medicalID = medID;

		jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);
		jf.setLayout(null);

		l2 = new JLabel("Medicine name*");
		//l2.setFont(f);
   		l2.setBounds(50,50,200,25);
		jf.add(l2);

    	t2 = new JTextField(20);
		t2.setBounds(250,50,200,25);t2.setToolTipText("Enter medicine name to search");
		jf.add(t2);

		b0 = new JButton("Search");
        b0.setBounds(150,100,110,35);b0.setToolTipText("click to search medicine details");
		jf.add(b0);
		b0.addActionListener(this);

		b1 = new JButton("clear");
		b1.setBounds(300,100,110,35);b1.setToolTipText("click to clear all textfields");
	    jf.add(b1); b1.addActionListener(this);

	    scrlPane.setBounds(0,150,900,600);
        jf.add(scrlPane);
        tabGrid.setFont(new Font ("Times New Roman",0,15));

        model.addColumn("M_BNO");
        model.addColumn("M_NAME");
        model.addColumn("M_COMPANY");
        model.addColumn("M_QUANTITY");
        model.addColumn("M_EXPDATE");
        model.addColumn("M_PURDATE");
        model.addColumn("M_TYPE");
        model.addColumn("M_SALEPRICE");
        model.addColumn("M_PURPRICE");
        model.addColumn("M_RACKNO");
        model.addColumn("M_SID");
        model.addColumn("M_SNAME");

	     jf.setTitle("Search Medicine ");
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

			try
			{
				if((t2.getText()).equals(""))
				{
					JOptionPane.showMessageDialog(this,"Please enter medicine name !","Warning!!!",JOptionPane.WARNING_MESSAGE);
				}
    
				else
				{
					int foundrec = 0, r=0;

				    Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","");
					System.out.println("Connected to database.");

					ps=con.prepareStatement("select * from medicine where (mname='"+t2.getText()+"' ) and sid IN (select sid from medsupp where medID = '"+medicalID+"')" );
					rs=ps.executeQuery();

					while(rs.next())
					{
						model.insertRow(r++, new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),
														  rs.getString(4),rs.getString(5),rs.getString(6),
														  rs.getString(7),rs.getString(8),rs.getString(9),
														  rs.getString(10),rs.getString(11),rs.getString(12)});
					    foundrec = 1;
					}

	        		if (foundrec == 0)
                	{
                    	JOptionPane.showMessageDialog(null,"Record is not available","Dialog",JOptionPane.WARNING_MESSAGE);
                	}
	         	}

	         	con.close();
        	}

			catch(SQLException se)
			{
				System.out.println(se);
				JOptionPane.showMessageDialog(null,"SQL Error."+se);
			}

			catch(Exception e)
			{
				System.out.println(e);
				JOptionPane.showMessageDialog(null,"Error."+e);
			}
		}

		else if(ae.getSource()==b1)
	    {
	                t2.setText("");
	    }

	}

 /*public static void main(String args[])
 {
  new SearchMedicine();
 }*/
}


