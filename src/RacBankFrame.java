import java.awt.*;

import javax.swing.*;

import java.sql.*;
import java.awt.event.*;

import javax.swing.border.Border;
import javax.swing.event.*;

import java.util.Vector;


public class RacBankFrame extends JFrame implements ActionListener,FocusListener,ChangeListener,ListSelectionListener,ItemListener  
{
	JTabbedPane jtp;
	
	JTextField tId1,tNam1,tBal1;
	JButton bSub1,bClr1,bCls1;
	
	JTextField tId2,tAct2,tNam2,tAmt2;
	JButton bSub2,bClr2,bCls2;
	
	JList lst3;
	JTextField tNam3,tBal3,tMsg3;
	JButton bDel3;
	JRadioButton rCr2,rDr2;
	
	
	JCheckBox jcb3[]=new JCheckBox[100];
	JTextField tDet3[]=new JTextField[100];
	
	int totTrans;
	
	int actid;
	int trnid;
	int id;
	int balance;
	
	int arr[]=new int[100];
	Vector vct=new Vector();
	
	PreparedStatement psInsCust,psUpdCust,psInsTrans,psGetCust,psGetTrans,psUpdTrans,psGetAct;
			
	RacBankFrame(Connection cn)
	{
		super("BANK");
		setSize(600,400);
		setLocation(100,100);
		
		RacBankPanel bp=new RacBankPanel(15, 15, 15, 15);
		
		add(bp);
		
		//SQL QUERY AND PREPARE STATEMENTS
		try
		{
			Statement st=cn.createStatement();
			ResultSet rs=st.executeQuery("select max(actid) as maxid from cust");
			rs.next();
			
			actid=rs.getInt("maxid");
			if(actid==0)
				actid=100;
			
			actid++;
			
			rs=st.executeQuery("select max(transid) as maxid from trans");
			rs.next();
			trnid=rs.getInt("maxid");
			if(trnid==0)
				trnid=1000;
			trnid++;
			
			psInsCust=cn.prepareStatement("insert into cust values(?,?,?)");
			psInsTrans=cn.prepareStatement("insert into trans values(?,?,?,?,?,?)");
			psUpdCust=cn.prepareStatement("update cust set balance=? where actid=?");
			psGetCust=cn.prepareStatement("select * from cust where actid=?");
			psGetTrans=cn.prepareStatement("select * from trans where actid=?");
			psUpdTrans=cn.prepareStatement("delete from trans where transid=?");
			
			psGetAct=cn.prepareStatement("select actid from cust");
			
			/*rs=psGetAct.executeQuery();
			while(rs.next())
				vct.add(rs.getInt("actid")+"  ");

			*/
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this, "SQL Alert - "+e.getMessage());
		}
		jtp=new JTabbedPane();
		jtp.setFont(new Font("lucida console",Font.PLAIN,18));
		
		
		//-------Open Account ---------//
		
		
		
		RacBankPanel p1=new RacBankPanel(30, 10, 10, 10);
		
		RacBankPanel p11=new RacBankPanel(5, 25, 5, 300);
		 JLabel l11=new JLabel("Acct ID");
		 l11.setFont(new Font("lucida console",Font.PLAIN,18));
		 l11.setForeground(Color.WHITE);
		 
		 tId1=new JTextField(actid+"");
		 tId1.setFont(new Font("lucida console",Font.PLAIN,18));
		 tId1.setBackground(new Color(150,80,80));
		 tId1.setForeground(Color.WHITE);
		 tId1.setEditable(false);
		 
		 p11.setLayout(new BorderLayout(20,1));
		 p11.add(l11,BorderLayout.WEST);
		 p11.add(tId1,BorderLayout.CENTER);
		 
		RacBankPanel p12=new RacBankPanel(5, 25, 5, 50);
		 JLabel l12=new JLabel("Name");
		 l12.setFont(new Font("lucida console",Font.PLAIN,18));
		 l12.setForeground(Color.WHITE);
		 
		 tNam1=new JTextField();
		 tNam1.setFont(new Font("lucida console",Font.PLAIN,18));
		 tNam1.setEditable(true);
		
		 p12.setLayout(new BorderLayout(51, 1));
		 p12.add(l12,BorderLayout.WEST);
		 p12.add(tNam1,BorderLayout.CENTER);
		 
		RacBankPanel p13=new RacBankPanel(5, 25, 5, 300);
		 JLabel l13=new JLabel("Balance");
		 l13.setFont(new Font("lucida console",Font.PLAIN,18));
		 l13.setForeground(Color.WHITE);
		 
		 tBal1=new JTextField();
		 tBal1.setFont(new Font("lucida console",Font.PLAIN,18));
		 tBal1.setEditable(true);
		 
		 p13.setLayout(new BorderLayout(18, 1));
		 p13.add(l13,BorderLayout.WEST);
		 p13.add(tBal1,BorderLayout.CENTER);
		 
		RacBankPanel p14=new RacBankPanel(5, 25, 5, 25);
		 bSub1=new JButton("Submit");
		 bSub1.setFont(new Font("lucida console",Font.PLAIN,18));
		 
		 bClr1=new JButton("Clear");
		 bClr1.setFont(new Font("lucida console",Font.PLAIN,18));
		  
		 bCls1=new JButton("Close");
		 bCls1.setFont(new Font("lucida console",Font.PLAIN,18));
		 
		
		 p14.setLayout(new GridLayout(1,3,10,1));
		 p14.add(bSub1);
		 p14.add(bClr1);
		 p14.add(bCls1);
		 
		p1.setLayout(new GridLayout(5,1,1,1)); 
		p1.add(p11);
		p1.add(p12);
		p1.add(p13);
		p1.add(new JLabel());
		p1.add(p14);
		
		
		//-------------Transation--------//
		RacBankPanel p2=new RacBankPanel(10,10,10,10);
		
		RacBankPanel p21=new RacBankPanel(5, 25, 5, 300);
		 JLabel l21=new JLabel("Trans Id");
		 l21.setFont(new Font("lucida console",Font.PLAIN,18));
		 l21.setForeground(Color.WHITE);
		 
		 tId2=new JTextField(trnid+"");
		 l21.setFont(new Font("lucida console",Font.PLAIN,18));
		 tId2.setBackground(new Color(150,80,80));
		 tId2.setForeground(Color.WHITE);
		 tId2.setEditable(false);
		 
		 p21.setLayout(new BorderLayout(30,1));
		 p21.add(l21,BorderLayout.WEST);
		 p21.add(tId2,BorderLayout.CENTER);
		 
		 
		RacBankPanel p22=new RacBankPanel(5, 25, 5, 300);
		 JLabel l22=new JLabel("Acct Id");
		 l22.setFont(new Font("lucida console",Font.PLAIN,18));
		 l22.setForeground(Color.WHITE);
		
		 tAct2=new JTextField();
		 l22.setFont(new Font("lucida console",Font.PLAIN,18));
		 
		 p22.setLayout(new BorderLayout(40,1));
		 p22.add(l22,BorderLayout.WEST);
		 p22.add(tAct2,BorderLayout.CENTER);
		 
		RacBankPanel p23=new RacBankPanel(5, 25, 5, 25);
		 JLabel l23=new JLabel("Name");
		 l23.setFont(new Font("lucida console",Font.PLAIN,18));
		 l23.setForeground(Color.white);
		 
		 tNam2=new JTextField();
		 tNam2.setFont(new Font("lucida console",Font.PLAIN,18));
		 tNam2.setEditable(false);
		 
		 p23.setLayout(new BorderLayout(73,1));
		 p23.add(l23,BorderLayout.WEST);
		 p23.add(tNam2,BorderLayout.CENTER);
		 
		RacBankPanel p24=new RacBankPanel(5, 25, 5, 25);
		 JLabel l24=new JLabel("Type");
		 l24.setFont(new Font("lucida console",Font.PLAIN,18));
		 l24.setForeground(Color.white);
		 
		 RacBankPanel p241=new RacBankPanel(5, 5, 5, 50);
		  rCr2=new JRadioButton("Credit",true);
		  rCr2.setFont(new Font("lucida console",Font.PLAIN,18));
		  rCr2.setForeground(Color.white);
		  rCr2.setBackground(new Color(80,80,120));	
		  
		  
		  rDr2=new JRadioButton("Debit",true);
		  rDr2.setFont(new Font("lucida console",Font.PLAIN,18));
		  rDr2.setForeground(Color.white);
		  rDr2.setBackground(new Color(80,80,120));	
		  
		 
		  ButtonGroup bg=new ButtonGroup();
		  bg.add(rCr2);
		  bg.add(rDr2);
		  
		 
		 p241.setLayout(new BorderLayout(50,1));
		 p241.add(rDr2,BorderLayout.WEST);
		 p241.add(rCr2,BorderLayout.CENTER);
		
		p24.setLayout(new BorderLayout(65,1));
		p24.add(l24,BorderLayout.WEST);
		p24.add(p241,BorderLayout.CENTER);
		
		
		RacBankPanel p25=new RacBankPanel(5, 25, 5, 300);
		 JLabel l25=new JLabel("Amount");
		 l25.setFont(new Font("lucida console",Font.PLAIN,18));
		 l25.setForeground(Color.WHITE);
		 
		 tAmt2=new JTextField();
		 tAmt2.setFont(new Font("lucida console",Font.PLAIN,18));
		 
		 p25.setLayout(new BorderLayout(51,1));
		 p25.add(l25,BorderLayout.WEST);
		 p25.add(tAmt2,BorderLayout.CENTER);
		
		RacBankPanel p26=new RacBankPanel(5, 25, 5, 25);
		 bSub2=new JButton("Submit");
		 bSub2.setFont(new Font("lucida console",Font.PLAIN,18));
		 
		 bClr2=new JButton("Clear");
		 bClr2.setFont(new Font("lucida console",Font.PLAIN,18));
		  
		 bCls2=new JButton("Close");
		 bCls2.setFont(new Font("lucida console",Font.PLAIN,18));
		 
		
		 p26.setLayout(new GridLayout(1,3,10,1));
		 p26.add(bSub2);
		 p26.add(bClr2);
		 p26.add(bCls2);
		 
		p2.setLayout(new GridLayout(7,1,1,1)); 
		p2.add(p21);
		p2.add(p22);
		p2.add(p23);
		p2.add(p24);
		p2.add(p25);
		p2.add(new JLabel());
		p2.add(p26);
		p2.add(p26);
		
		
		//-------Delete Trans-------------//
	   RacBankPanel p3=new RacBankPanel(10, 10, 10, 10);
		p3.setLayout(new BorderLayout(15,5));
		
		lst3=new JList();
		lst3.setFont(new Font("lucida console",Font.PLAIN,18));
		lst3.setSelectionBackground(Color.red);
		lst3.setSelectionForeground(Color.white);
		lst3.setBackground(new Color(100,100,100));

		
		JScrollPane jsp31=new JScrollPane(lst3);
		jsp31.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel lNam3=new JLabel("Name");
        lNam3.setFont(new Font("lucida console",Font.PLAIN,18));
        lNam3.setBackground(Color.white);
        
        JLabel lBal3=new JLabel("Balance");
        lBal3.setFont(new Font("lucida console",Font.PLAIN,18));
        lBal3.setBackground(Color.white);
       
        
        tNam3=new JTextField();
        tNam3.setFont(new Font("lucida console",Font.PLAIN,18));
        tNam3.setEditable(false);
        
        tBal3=new JTextField();
        tBal3.setFont(new Font("lucida console",Font.PLAIN,18));
        tBal3.setEditable(false);
        
        tMsg3=new JTextField();
        tMsg3.setFont(new Font("lucida console",Font.PLAIN,18));
        tMsg3.setEditable(false);
        tMsg3.setBackground(new Color(100,100,150));
        tMsg3.setForeground(Color.WHITE);
        
        bDel3=new JButton("Delete");
        bDel3.setFont(new Font("lucida console",Font.PLAIN,18));
        bDel3.setBackground(new Color(180,80,80));
        bDel3.setForeground(Color.WHITE);
        
        RacBankPanel pDet=new RacBankPanel(1, 1, 1, 1);
        pDet.setLayout(new GridLayout(100,1,5,5));
        
        
        RacBankPanel pArr[]=new RacBankPanel[100];
        for(int i=0;i<100;i++)
        {
        	jcb3[i]=new JCheckBox();
        	jcb3[i].setFont(new Font("lucida console",Font.PLAIN,18));
        	jcb3[i].setBackground(new Color(80,80,120));
        	jcb3[i].setVisible(false);
        	
        	jcb3[i].addItemListener(this);
        	
        	tDet3[i]=new JTextField();
        	tDet3[i].setFont(new Font("lucida console",Font.PLAIN,18));
        	tDet3[i].setVisible(false);
        	tDet3[i].setEditable(false);
        	
        	pArr[i]=new RacBankPanel(1, 1, 1, 1);
        	pArr[i].setLayout(new BorderLayout(1,1));
        	pArr[i].add(jcb3[i],BorderLayout.WEST);
        	pArr[i].add(tDet3[i],BorderLayout.CENTER);
        	
        	pDet.add(pArr[i]);
        	
        }
        
        JScrollPane jsp32=new JScrollPane(pDet);
        jsp32.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        RacBankPanel p32=new RacBankPanel(1, 1, 1, 1);
        p32.setLayout(new BorderLayout(5,5));
        
         RacBankPanel p321=new RacBankPanel(10, 10, 10, 10);
         p321.setBorder(BorderFactory.createLineBorder(Color.white,1));
         p321.setLayout(new GridLayout(2,1,5,5));
         
          RacBankPanel p3211=new RacBankPanel(1, 1, 1, 1);
          p3211.setLayout(new BorderLayout(38,5));
          p3211.add(lNam3,BorderLayout.WEST);
          p3211.add(tNam3,BorderLayout.CENTER);
          
          RacBankPanel p3212=new RacBankPanel(1, 1, 1, 1);
          p3212.setLayout(new BorderLayout(5,5));
          p3212.add(lBal3,BorderLayout.WEST);
          p3212.add(tBal3,BorderLayout.CENTER);
          
        p321.add(p3211);
        p321.add(p3212);
        
        RacBankPanel p322=new RacBankPanel(10, 10, 10, 10);
		p322.setBorder(BorderFactory.createLineBorder(Color.white,1));
		p322.setLayout(new BorderLayout(5,5));
		p322.add(jsp32,BorderLayout.CENTER);
		
		RacBankPanel p323=new RacBankPanel(1, 1, 1, 1);
		p323.setLayout(new BorderLayout(5,5));
		p323.add(tMsg3,BorderLayout.CENTER);
		p323.add(bDel3,BorderLayout.EAST);
		
		p32.add(p321,BorderLayout.NORTH);
		p32.add(p322,BorderLayout.CENTER);
		p32.add(p323,BorderLayout.SOUTH);
		
		p3.add(jsp31,BorderLayout.WEST);
		p3.add(p32,BorderLayout.CENTER);
		
		//------------------//
		jtp.add("Open Account", p1);
		jtp.add("Transation",p2);
		jtp.add("Delect Trans", p3);
		
		jtp.setBackgroundAt(0, new Color(80,180,80));
		jtp.setForegroundAt(0, Color.GRAY);
		
		jtp.setBackgroundAt(1, new Color(180,80,80));
		jtp.setForegroundAt(1, Color.GRAY);
		
		jtp.setBackgroundAt(2, new Color(80,80,180));
		jtp.setForegroundAt(2, Color.GRAY);
			
		
		
		//-----------------//
		
		bp.setLayout(new BorderLayout(5,5));
		bp.add(jtp,BorderLayout.CENTER);
		
		
		bSub1.addActionListener(this);
		bClr1.addActionListener(this);
		bCls1.addActionListener(this);
		
		bSub2.addActionListener(this);
		bClr2.addActionListener(this);
		bCls2.addActionListener(this);
		
		tAct2.addFocusListener(this);
		
		jtp.addChangeListener(this);
		
		lst3.addListSelectionListener(this);
	
		bDel3.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource()==bSub1)
		{
			try
			{
				int bal=Integer.parseInt(tBal1.getText());
				
				psInsCust.setInt(1,actid);
				psInsCust.setString(2, tNam1.getText());
				psInsCust.setInt(3, bal);
				
				
				psInsCust.executeUpdate();
				
				JOptionPane.showMessageDialog(this, "Account Created Sucessfully");
				actid++;
				
				
				tId1.setText(actid+"");
				tNam1.setText("");
				tBal1.setText("");
				
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this, "Illegal Balance");
				tBal1.select(0, tBal1.getText().length());
				tBal1.requestFocus();
			}
			catch(SQLException e)
			{
				JOptionPane.showMessageDialog(this, "Sql Alert - "+e.getMessage());
				
			}
		}
		else if(ae.getSource()==bClr1)
		{
			tNam1.setText("");
			tBal1.setText("");
		}
		else if(ae.getSource()==bCls1)
		{
			System.exit(0);
		}
		else if(ae.getSource()==bSub2)
		{
			try
			{
				int bal=Integer.parseInt(tAmt2.getText());
				
				System.out.println("bal     : "+bal);
				System.out.println("balance : "+balance);
				
				psInsTrans.setInt(1, trnid);
				psInsTrans.setInt(2,id);
				
				String status="Success";
				java.sql.Date dot=new java.sql.Date(new java.util.Date().getTime());
				
				if(rCr2.isSelected())
				{
					psInsTrans.setString(3, "CR");
					balance=balance+bal;
				}
				else if(rDr2.isSelected())
				{
					psInsTrans.setString(3, "DR");
					if(bal>balance)
					{
						status="Failed";
					}
					else
					{
						balance=balance-bal;
					}
				}
				
				
				psInsTrans.setInt(4,bal);
				psInsTrans.setString(5,status);
				psInsTrans.setDate(6,dot);
				
				psInsTrans.executeUpdate();
				
				
				
				if(status=="Success")
				{
					psUpdCust.setInt(1,balance);
					psUpdCust.setInt(2, id);
					psUpdCust.executeUpdate();
				}
				
				if(status=="Success")
					JOptionPane.showMessageDialog(this,"Transaction Successful");
				else
					JOptionPane.showMessageDialog(this, "Transaction Failed");

				trnid++;
				tId2.setText(trnid+"");
				tNam2.setText("");
				tAmt2.setText("");
				tAct2.setText("");
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this, "Illegal Amount");
				tAmt2.select(0,tAmt2.getText().length());
				tAmt2.requestFocus();
				
			}
			catch(SQLException e)
			{
				JOptionPane.showMessageDialog(this, "SQL Alert - "+e.getMessage());
			}
		}
		else if(ae.getSource()==bClr2)
		{
			tAct2.setText("");
			tNam2.setText("");
			tAmt2.setText("");
		}
		else if(ae.getSource()==bCls2)
		{
			System.exit(0);
		}
		else if(ae.getSource()==bDel3)
		{
			
			System.out.println("Delete");
			int nq=0,temp=0;
			for(int i=0;i<totTrans;i++)
			{

				if(arr[i]!=0)
				{
					System.out.println(arr[i]);
				
					try
					{
						psUpdTrans.setInt(1, arr[i]);
						nq=psUpdTrans.executeUpdate();
						jcb3[i].setVisible(false);
						tDet3[i].setVisible(false);
				
			
						System.out.println("EEEEEEEEEE"+nq);
						
					}
					catch(SQLException e)
					{
						JOptionPane.showMessageDialog(this, "Unable to delete - "+e.getMessage() );
					}
				}
				
				
				
			}
			if(nq==1)
			{
				JOptionPane.showMessageDialog(this, "Deleted successfully");
/*			  int id=Integer.parseInt((((String)lst3.getSelectedValue()).trim()));

				try
				{
					psGetTrans.setInt(1,id);
					ResultSet rs=psGetTrans.executeQuery();
					totTrans=0;
					
					System.out.println(id);
					while(rs.next())
					{
						
						System.out.println("YAHA2");
						jcb3[totTrans].setSelected(false);
						jcb3[totTrans].setVisible(true);
						tDet3[totTrans].setVisible(true);
						
						java.sql.Date dt=rs.getDate("dot");
						String sdt=dt+"";
						
						String stxt=String.format("%5d %s %s %5d %s", 
						rs.getInt("transid"),
						sdt,
						rs.getString("crdebit"),
						rs.getInt("bal"),
						rs.getString("status"));
						
					    /*String stxt=String.format("%5d  %s  %s %5d  %s",
		                        rs.getInt("transid")	,
		                        sdt,
		                        rs.getString("crdebit"),
		                        rs.getInt("bal"),
		                        rs.getString("status"));
						*/
					/*	tDet3[totTrans].setText(stxt);
						
					
					}
		
			      
			
				}
				catch(SQLException e)
				{
					JOptionPane.showMessageDialog(this, "SQL ALERT");
					
				}
				*/
				
			}
			
		}
	
		
	}

	@Override
	public void focusGained(FocusEvent fe) {}

	@Override
	public void focusLost(FocusEvent fe) 
	{
		if(jtp.getSelectedIndex()!=1)
			return;
		
		try
		{
			id=Integer.parseInt(tAct2.getText());
			
			psGetCust.setInt(1,id);
			ResultSet rs=psGetCust.executeQuery();
			
			
			if(rs.next())
			{
				tNam2.setText(rs.getString("name"));
				balance=rs.getInt("balance");
				
			}
			else
			{
				throw new NumberFormatException();
			}
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Invaild Accound Id");
			tAct2.select(0, tAct2.getText().length());
			tAct2.requestFocus();
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this, "SQL Alert - "+e.getMessage());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent lse) 
	{
		for(int i=0;i<totTrans;i++)
		{
			jcb3[i].setSelected(false);
			jcb3[i].setVisible(false);
			tDet3[i].setVisible(false);
		}
		
		int id=Integer.parseInt((((String)lst3.getSelectedValue()).trim()));
		
		try
		{
			psGetCust.setInt(1,id);
			ResultSet rs=psGetCust.executeQuery();
			if(rs.next())
			{
				tNam3.setText(rs.getString("name"));
				tBal3.setText(rs.getInt("balance")+"");
			}
			
			System.out.println("YES");
			psGetTrans.setInt(1,id);
			rs=psGetTrans.executeQuery();
			totTrans=0;
			System.out.println(id);
			while(rs.next())
			{
				System.out.println("YAHA");
				jcb3[totTrans].setSelected(false);
				jcb3[totTrans].setVisible(true);
				tDet3[totTrans].setVisible(true);
				
				java.sql.Date dt=rs.getDate("dot");
				String sdt=dt+"";
				
				String stxt=String.format("%5d %s %s %5d %s", 
				rs.getInt("transid"),
				sdt,
				rs.getString("crdebit"),
				rs.getInt("bal"),
				rs.getString("status"));
				
			    /*String stxt=String.format("%5d  %s  %s %5d  %s",
                        rs.getInt("transid")	,
                        sdt,
                        rs.getString("crdebit"),
                        rs.getInt("bal"),
                        rs.getString("status"));
				*/
				tDet3[totTrans].setText(stxt);
				
				System.out.println(totTrans);
				totTrans++;
			}	
			tMsg3.setText("[0] Rows Selected for Deletion");	
			
			
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this, "SQL Alert - "+e.getMessage());
			
		}
			
		
		
		
		
	}

	@Override
	public void stateChanged(ChangeEvent ce) 
	{
		if(jtp.getSelectedIndex()==0)
		{
			setSize(600,350);

			
		}
		else if(jtp.getSelectedIndex()==1)
		{
			setSize(600, 450);

			
			tAct2.requestFocus();
		}
		else if(jtp.getSelectedIndex()==2)
		{
			lst3.setListData(new Vector());
			Vector vct1=new Vector();
			setSize(700,480);
			try
			{
				ResultSet rs=psGetAct.executeQuery();
				while(rs.next())
					vct1.add(rs.getInt("actid")+"  ");
			}
			catch(SQLException e)
			{
				JOptionPane.showMessageDialog(this, "ERROR "+e.getMessage());
			}
			
			lst3.setListData(vct1);
			
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent Ie) 
	{
		int tot=0;
		for(int i=0;i<totTrans;i++)
		{
			if(jcb3[i].isSelected())
			{
				int ndel=Integer.parseInt(tDet3[i].getText().substring(0, 5).trim());

				System.out.println(ndel);
				
				arr[i]=ndel;
				tot++;
			} 
			else if(!jcb3[i].isSelected())
			{
				arr[i]=0;
			}
		}
		
		tMsg3.setText("["+tot+"] Row selected for Deletion");
		System.out.println(arr[0]);
		
		System.out.println(arr[1]);
		System.out.println(arr[2]);
		
	}
	
	
	

}
