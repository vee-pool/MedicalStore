import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.ResultSet;

public class SupplierWiseMedList extends JFrame implements ActionListener
{
    int medicalID;
    JFrame jf;
    JButton submit,clear;
    JLabel l1, lsname, lsph, lsem, lsadd;
    JTextField t1;
    Font f, fs;
    Connection con;
    PreparedStatement ps;
    JComboBox date;
    Statement stmt;
    ResultSet rs;
    String purdate;
    DefaultTableModel model = new DefaultTableModel();
    JTable tabGrid = new JTable(model);
    JScrollPane scrlPane = new JScrollPane(tabGrid);

    DefaultTableModel supp = new DefaultTableModel();
    JTable tabSupp = new JTable(supp);
    JScrollPane suppPane = new JScrollPane(tabSupp); 

    public SupplierWiseMedList(int id)
    {
        medicalID = id;
       jf=new JFrame();
       f = new Font("Times New Roman",Font.BOLD,20);
       fs = new Font("Times New Roman",Font.BOLD,12);
       jf.setLayout(null);

        l1 = new JLabel("Enter Supplier name:");
        l1.setFont(f);
        l1.setBounds(20,50,200,25);
        jf.add(l1);

        t1=new JTextField(10);
        t1.setBounds(220,50,200,25);t1.setToolTipText("Enter supplier name");
        jf.add(t1);

        submit = new JButton("Submit",new ImageIcon("images//open.png"));
        submit.setBounds(20,100,110,35); submit.setToolTipText("click to open supplier wise medicine report");
        jf.add(submit);submit.addActionListener(this);

        clear = new JButton("Clear",new ImageIcon("images//clear.png"));
        clear.setBounds(150,100,110,35);clear.setToolTipText("click to clear textfield");
        jf.add(clear);clear.addActionListener(this);

        scrlPane.setBounds(0,150,900,600);
        jf.add(scrlPane);
        tabGrid.setFont(new Font ("Times New Roman",0,15));

        model.addColumn("M_BNO");
        model.addColumn("M_NAME");
        model.addColumn("M_COMPANY");
        model.addColumn("M_QUANTITY");
        model.addColumn("M_EXPDATE");
        model.addColumn("M_TYPE");
        model.addColumn("M_PURPRICE");
        model.addColumn("M_SALEPRICE");
        model.addColumn("M_RACKNO");
        model.addColumn("M_SID");

        suppPane.setBounds(500,0,400,60);
        jf.add(suppPane);
        tabSupp.setFont(new Font ("Times New Roman",0,15));

        supp.addColumn("Name");
        supp.addColumn("Address");
        supp.addColumn("Phone no");
        supp.addColumn("Email");

        date=new JComboBox();
        date.setBounds(280,100,130,25);
        date.setToolTipText("select purchase date");
        jf.add(date);
        date.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                purdate =(String)date.getSelectedItem();
            }
        });

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","");
            System.out.println("Connected to database.");
            ps=con.prepareStatement("select distinct mpurdate from medicine where medID = '" + medicalID + "'");
            rs=ps.executeQuery();
            while(rs.next())
            {
                String sname1=rs.getString(1);
                date.addItem(sname1);
            }

            con.close();
        }

        catch(SQLException se)
        {
            System.out.println(se);
        }

        catch(Exception e)
        {
            System.out.println(e);
        }


        jf.setTitle("Supplier Wise Medicine Report");
        jf.setSize(900,700);
        jf.setLocation(20,20);
        jf.setResizable(false);
        jf.getContentPane().setBackground(Color.cyan);
        jf.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==submit)
        {//list

            display_supplier_info();
            int r = 0;
            try
            {
                if(((t1.getText()).equals("")))
                {
                    JOptionPane.showMessageDialog(this,"Please supplier name !","Warning!!!",JOptionPane.WARNING_MESSAGE);
                }

                else
                {
                    int foundrec = 0;
                    Class.forName("com.mysql.jdbc.Driver");
                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","");
                    System.out.println("Connected to database.");
                    stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    rs = stmt.executeQuery("SELECT mbno,mname,mcompany,mquantity,mexpdate,mtype,mpurprice, msaleprice,mrackno,sid from medicine where sname='"
                                            +t1.getText()+"' and mpurdate='"+purdate+"' and medID = '"+medicalID+"'");
              
                    while(rs.next())
                    {
                        model.insertRow(r++,new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),
                                                            rs.getString(4),rs.getString(5),
                                                            rs.getString(6),rs.getString(7),rs.getString(8),
                                                            rs.getString(9),rs.getString(10)});
                        foundrec = 1;
                    }

                    if (foundrec == 0)
                    {
                        JOptionPane.showMessageDialog(null,"Not any medicine","Dialog",JOptionPane.WARNING_MESSAGE);
                    }
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

         else if(ae.getSource()==clear)
         {
            t1.setText("");
         }
    }

    private void display_supplier_info()
    {

            try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_store","root","");
                    System.out.println("Connected to database. display_supplier_info");
                    stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    rs = stmt.executeQuery("SELECT * from supplier where sname = '" + t1.getText()+"'");
              
                if (rs.next())
                {
                    supp.insertRow(0,new Object[]{rs.getString(2),rs.getString(3),
                                                    rs.getString(4),rs.getString(5)});
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

    /*public static void main(String args[])
    {
        new SupplierWiseMedList();
    }*/
}
