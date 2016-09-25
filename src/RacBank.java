import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;



public class RacBank {

	public static void main(String[] args) {
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			
			RacBankFrame bf=new RacBankFrame(cn);
			bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			bf.setVisible(true);
		}
		catch(ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Error loading Driver ...Aborting");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "SQL Alert --"+e.getMessage());
			
		}

	}

}
