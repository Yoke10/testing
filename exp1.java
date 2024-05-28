import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        DatabaseConnection dbc = new DatabaseConnection();

        while (true) {
            System.out.println("Press 1 to create table.");
            System.out.println("Press 2 to insert data.");
            System.out.println("Press 3 to update data.");
            System.out.println("Press 4 to delete data.");
            System.out.println("Press 5 to read data.");
            System.out.println("Press 0 to exit.");
            int n = s.nextInt();

            switch (n) {
                case 1:
                    dbc.createTable();
                    break;
                case 2:
                    dbc.insertData(s);
                    break;
                case 3:
                    dbc.updateData(s);
                    break;
                case 4:
                    dbc.deleteData(s);
                    break;
                case 5:
                    dbc.readData();
                    break;
                case 0:
                    dbc.closeConnection();
                    s.close();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    static class DatabaseConnection {
        private String url = "jdbc:mysql://localhost:3306/model";
        private String username = "root";
        private String password = "blacky062005";
        private Connection con;

        public DatabaseConnection() throws SQLException {
            this.con = DriverManager.getConnection(url, username, password);
        }

        public void closeConnection() throws SQLException {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }

        public void createTable() throws SQLException {
            String query = "create table passenger(pid int primary key, pname varchar(20), address varchar(150), sourcepoint varchar(30), destination varchar(20), phoneno varchar(11));";
            Statement st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Table created.");
            st.close();
        }

        public void readData() throws SQLException {
            String query = "select * from passenger;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.println("Id: " + rs.getInt(1));
                System.out.println("Name: " + rs.getString(2));
                System.out.println("Address: " + rs.getString(3));
                System.out.println("Source: " + rs.getString(4));
                System.out.println("Destination: " + rs.getString(5));
                System.out.println("Phone no: " + rs.getString(6));
            }
            System.out.println("Read completed.");
            st.close();
        }

        public void insertData(Scanner s) throws SQLException {
            System.out.println("Enter ID: ");
            int id = s.nextInt();
            s.nextLine(); // Consume newline left-over
            System.out.println("Enter Name: ");
            String name = s.nextLine();
            System.out.println("Enter Address: ");
            String address = s.nextLine();
            System.out.println("Enter Source Point: ");
            String source = s.nextLine();
            System.out.println("Enter Destination: ");
            String destination = s.nextLine();
            System.out.println("Enter Phone Number: ");
            String phone = s.nextLine();

            String query = "insert into passenger values (?, ?, ?, ?, ?, ?);";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setString(3, address);
            pst.setString(4, source);
            pst.setString(5, destination);
            pst.setString(6, phone);

            int row = pst.executeUpdate();
            System.out.println("Number of rows affected: " + row);
            pst.close();
        }

        public void updateData(Scanner s) throws SQLException {
            System.out.println("Enter ID to update: ");
            int id = s.nextInt();
            s.nextLine(); // Consume newline left-over
            System.out.println("Enter new Name: ");
            String name = s.nextLine();
            System.out.println("Enter new Address: ");
            String address = s.nextLine();
            System.out.println("Enter new Source Point: ");
            String source = s.nextLine();
            System.out.println("Enter new Destination: ");
            String destination = s.nextLine();
            System.out.println("Enter new Phone Number: ");
            String phone = s.nextLine();

            String query = "update passenger set pname=?, address=?, sourcepoint=?, destination=?, phoneno=? where pid=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, address);
            pst.setString(3, source);
            pst.setString(4, destination);
            pst.setString(5, phone);
            pst.setInt(6, id);

            int row = pst.executeUpdate();
            System.out.println("Number of rows updated: " + row);
            pst.close();
        }

        public void deleteData(Scanner s) throws SQLException {
            System.out.println("Enter ID to delete: ");
            int idToDelete = s.nextInt();

            String query = "delete from passenger where pid=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idToDelete);

            int row = pst.executeUpdate();
            System.out.println("Number of rows deleted: " + row);
            pst.close();
        }
    }
}
