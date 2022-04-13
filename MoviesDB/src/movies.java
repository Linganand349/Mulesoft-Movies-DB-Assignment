import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
	
public class movies {

	    public static void connect() {
	        Connection conn = null;
	        try {
	       
	            String url = "jdbc:sqlite:C:/sqlite/db/moviesDB.db";
	           
	            conn = DriverManager.getConnection(url);
	            
	            System.out.println("Connection to SQLite has been established.");
	            
	            try {
					deleteTable(conn);
				}
				catch(Exception e)
				{
					System.out.println("Exception occurred : " + e);
				}
	            
	        createTable(conn);
        	insertMovie(conn,"Spider-Man: No Way Home","Tom Holland","Zendaya",2021,"Jon Watts");
        	insertMovie(conn,"Inception","Leonardo DiCaprio","Elliot Page",2010 ,"Christopher Nolan");
        	insertMovie(conn,"Kirik Party","Rakshit Shetty", "Rashmika Mandanna",2016 ,"Rishab Shetty");
        	insertMovie(conn,"3 Idiots","Aamir Khan", "Kareena Kapoor",2009 ,"Rajkumar Hirani");
        	insertMovie(conn,"Jai Bhim","Suriya", "Lijomol Jose",2021 ,"T.J.Gnanavel");
        	insertMovie(conn,"Doctor Strange ","Benedict Cumberbatch", "Rachel McAdams",2016 ,"Scott Derrickson");
        	displayDatabase(conn,"Movies");
        	
        	System.out.println();
			// Getting movies according to actors
        	
			displayActorMovies(conn,"Movies","Aamir Khan");
			
			System.out.println();			
			displayActressMovies(conn,"Movies","Zendaya");
	        }
			catch(SQLException e) {
	            System.out.println(e.getMessage());
	        } finally {
	            try {
	                if (conn != null) {
	                	conn.close();
	                }
	            } catch (SQLException ex) {
	                System.out.println(ex.getMessage());
	            }
	        }
	    }
	  
	    private static void displayDatabase(Connection conn, String movies) throws SQLException{
			String disp="SELECT * from " + movies;
			Statement st=conn.createStatement();
			ResultSet rs= st.executeQuery(disp);
			System.out.println("\t\t\t\t\t\t MOVIES\n");
			while(rs.next()) {
				System.out.print("Movie- "+ rs.getString("name")+", ");
				System.out.print("Lead Actor- "+ rs.getString("actor")+", ");
				System.out.print("Lead Actress- "+ rs.getString("actress")+", ");
				System.out.print("Year- "+ rs.getInt("year")+", ");
				System.out.print("Director- "+ rs.getString("director"));
				System.out.println("\n");
			}
		}

		private static void insertMovie(Connection conn, String name, String actor, String actress, int year, String director) throws SQLException {
			String insTable="INSERT INTO Movies(name,actor,actress,year,director) VALUES(?,?,?,?,?)";
			PreparedStatement pst= conn.prepareStatement(insTable);
			pst.setString(1, name);
			pst.setString(2, actor);
			pst.setString(3, actress);
			pst.setInt(4, year);
			pst.setString(5, director);
			pst.executeUpdate();
		}

		private static void createTable(Connection conn) throws SQLException {
			String cTable= "" +
					"CREATE TABLE Movies " +
					"( " +
					"name varchar(255), " +
					"actor varchar(255), " +
					"actress varchar(255), " +
					"year integer, " +
					"director varchar(255) " +
					"); " +
					"";
			Statement st = conn.createStatement();
			st.execute(cTable);
		}
		private static void deleteTable(Connection conn) throws SQLException {
			String deleteTableQuery ="DROP TABLE Movies";
			Statement deleteTableStmt = conn.createStatement();
			deleteTableStmt.execute(deleteTableQuery);		
		}
		
		private static void displayActorMovies(Connection conn, String Movies, String actor) throws SQLException {
			String actQuery ="SELECT name from " +Movies + " WHERE actor = '" + actor +"'";
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(actQuery);
			System.out.println("Movies by actor: " + actor);
			while(rs.next()) {
				System.out.println(rs.getString("name"));
			}
		}
		
			private static void displayActressMovies(Connection conn, String Movies, String actress) throws SQLException {
				String selectSQL ="SELECT name from " + Movies + " WHERE actress = '" + actress +"'";
				Statement stmt=conn.createStatement();
				ResultSet rs=stmt.executeQuery(selectSQL);
				System.out.println("Movies by actress: " + actress);
				while(rs.next()) {
					System.out.println(rs.getString("name"));
				}
			}
		public static void main(String[] args) {
	        connect();
	    }
	}

