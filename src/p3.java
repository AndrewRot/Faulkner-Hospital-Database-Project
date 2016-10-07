import java.sql.*;
import java.util.Scanner;

public class p3 {
		// Replace the "USERID" and "PASSWORD" with your username and password to get this to work.
		// Note: Remember that your Oracle USERID for many of you is different than your regular login name
 		private static final String USERID = "acrottier";
    	private static final String PASSWORD = "Treehouse";
    	
    	private static  String HUSER;
    	private static  String HPASS;
    	private static  String option;

	public static void main(String[] args) {
		//Get arguments
		if(args.length == 2){
			HUSER = args[0];
			HPASS = args[1];
			System.out.println("1 – Report Health Provider Information");
			System.out.println("2 – Report Health Service Information");
			System.out.println("3 – Report Path Information");
			System.out.println("4 – Update Health Service Information");
			return;//I think
		}
		if(args.length == 3){
				HUSER = args[0];
				HPASS = args[1];
				option = args[2];
		}
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		
		String s = "", x = "", start = "", end = "", newID4 = "", name4 = "";
		
		//option 1
		if(option.equals("1")){
			System.out.println("Enter a Provider ID: ");
			s = reader.next();
			System.out.println("You entered: "+ s);
		}
		
		//option 2
		if(option.equals("2")){
			System.out.println("Enter a Health Service Name: ");
			x = reader.next();
			System.out.println("You entered: "+ x);
		}
		
		//option 3
		if(option.equals("3")){
			System.out.println("Enter Starting Location Name: ");
	        start = reader.next();
	        System.out.println("You entered: "+ start);
	        
	        System.out.println("Enter Ending Location Service Name: ");
	        end = reader.next();
	        System.out.println("You entered: "+ end);
		}
		
      //option 4
		if(option.equals("4")){
			System.out.println("Enter A HealthService Name: ");
	        name4 = reader.next();
	        System.out.println("You entered: "+ name4);
	        
	        System.out.println("Enter the new Location ID");
	        newID4 = reader.next();
	        System.out.println("You entered: "+ newID4);
		}
        
		
		
		System.out.println("-------Oracle JDBC COnnection Testing ---------");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		} catch (ClassNotFoundException e){
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		
		try {
			 connection = DriverManager.getConnection(
			 		"jdbc:oracle:thin:@oracle.wpi.edu:1521:orcl", USERID, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		System.out.println("Oracle JDBC Driver Connected!");
		
		//Query 1, Get Provider's Info
		try {
			Statement stmt = connection.createStatement();
			String str = "";
			ResultSet rset= null;
			if(option.equals("1")){
				System.out.println();
				System.out.println("****Query 1*****************************");
				
				System.out.println("Health Provider Information ");
				
				 str = "SELECT * FROM Provider P where P.ProviderID = " + s;
				 rset = stmt.executeQuery(str); //or executeUpdate();
				
				int ProviderID	= 0;
				String FirstName = "";
				String LastName	= "";
				// Process the results
				while (rset.next()) {
					ProviderID = rset.getInt("ProviderID");
					FirstName = rset.getString("FirstName");
					LastName = rset.getString("LastName");
					System.out.println("ProviderID: " + ProviderID + "   FirstName: " + FirstName + "   LastName: " + LastName);
				} // end while
				
				str = "SELECT * FROM ProviderTitle PT where PT.ProviderID = " + s;
				rset = stmt.executeQuery(str);
				
				String Acronym = "";
				System.out.print("Title: ");
				while (rset.next()) {
					Acronym = rset.getString("Acronym");
					System.out.print(Acronym + ", ");
				}
				System.out.println();
				
				str = "SELECT * FROM Office O, Location L where O.ProviderID = " + s + " and O.LocationID = L.LocationID";
				rset = stmt.executeQuery(str);
				
				String LocationName = "";
				System.out.print("Office Location: ");
				while (rset.next()) {
					LocationName = rset.getString("LocationName");
					System.out.print(LocationName + ", ");
				}
				
				
			}
			if(option.equals("2")){
				System.out.println();
				System.out.println("****Query 2*****************************");
				//Query 2
				System.out.println("Health Service Information");
				str = "SELECT * "
						+ " FROM Location L, ResidesIn RI, Services S, Floor F"
						+ " Where L.LocationID = RI.LocationID"
						+ " and S.ServiceName = RI.ServiceName"
						+ " and F.FloorID = L.FloorID"
						+ " and S.ServiceName = '" + x + "'";
				rset = stmt.executeQuery(str); //or executeUpdate();
				
				
				String ServiceName = "";
				String HealthType = "";
				String LocationName2 = "";
				String FloorID = "";
				// Process the results
				while (rset.next()) {
					ServiceName = rset.getString("ServiceName");
					HealthType = rset.getString("HealthType");
					LocationName2 = rset.getString("LocationName");
					FloorID = rset.getString("FloorID");
					System.out.println("ServiceName: " + ServiceName + "   HealthType: " + HealthType + "   LocationName: " + LocationName2 + "  FloorID" +FloorID);
				} // end while
				
			}
			if(option.equals("3")){
				System.out.println();
				System.out.println("****Query 3*****************************");
				
				//MIKES CODE***************
	            //Query 3
	            System.out.println("Shortest Path Information");
	            str = "SELECT * "
	            + " FROM Path P"
	            + " WHERE PathStart = '"+ start + "'"
	            + " and PathEnd = '" + end +"'";
	            //System.out.println(str);
	            
	            rset = stmt.executeQuery(str); //or executeUpdate();
	            
	            int foundPath = 0;
	            
	            System.out.println("Start Location: " + start);
	            System.out.println("End Location: " + end);
	            int PathID	= 0;
	            // Process the results
	            while (rset.next()) {
	                foundPath = 1; //indicate path was found!
	            } // end while
	            
	            //if no path was found, tell the user
	            if(foundPath == 0){
	            	System.out.println("There is no path between these locations in the database");
	            }
	            else //otherwise print the path
	            {
	            	/*
	            	str = "SELECT * "
	            		+ " FROM PathContains P, Location L "
	            		+ " WHERE P.PathID = "+ PathID
	            	    + " and L.LocationID = P.LocationID "
	            	    + " Order By PathOrder asc";
	            	 rset = stmt.executeQuery(str); */
	            	
	            	str = "Select PC.PathOrder, L.LocationName, L.FloorID, PC.PathID from Location L, PathContains PC where L.LocationID = PC.LocationID and PC.PathID = (SELECT PC.PathID FROM PathContains PC Group By PathID Having count(PC.PathID) = ( SELECT Min(CNT) As Min FROM ( SELECT PC.PathID, count(PC.LocationID) As CNT FROM PathContains PC, Path P where P.PathStart = '"+start+"' and P.PathEnd = '" + end +"'"
	            			+ " and PC.PathID = P.PathID"
	            			+ " Group By PC.PathID) )) Order By PathOrder asc";
		  			
	            	    rset = stmt.executeQuery(str); //or executeUpdate();
	            	    
	            	    int firstFlag = 0;
	            	    
	            	    int PathID2	= 0;
	            	    int PathOrder	= 0;
	            	    String LocationName3 = "";
	            	    String FloorID3 = "";
	            	    while (rset.next()) {
	            	    	if(firstFlag ==0){
	            	    		PathID2 = rset.getInt("PathID");
	                    	    System.out.println("Path ID for shortest path: "+PathID2);
	            	    	}
	            	    	PathOrder = rset.getInt("PathOrder");
	            	        LocationName3 = rset.getString("LocationName");
	            	        FloorID3 = rset.getString("FloorID");
	            	        System.out.println(PathOrder + " " + LocationName3 + " "+FloorID3);
	            	        firstFlag = 1; //not the first time through anymore
	            	    }
	           }
	            //END QUERY 3
				
			}
			if(option.equals("4")){
				System.out.println();
				System.out.println("****Query 4*****************************");
	            
	            //QUERY 4
	            //Name4 NewID4
				System.out.println("Update Health Service Information");
	            //Statement stmt = connection.createStatement();
	            str = "Update ResidesIn "
	            + " SET LocationID = '" + newID4 + "'"
	            + " WHERE ServiceName = '"+ name4 + "'";
	            int blah = stmt.executeUpdate(str);
	            rset = stmt.executeQuery(str); //or executeUpdate();
			}
                        
			System.out.println();
			System.out.println();
            
			rset.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Get Data Failed! Check output console");
			e.printStackTrace();
			return;			
		}
		
		
		

	}
}