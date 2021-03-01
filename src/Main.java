import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Connection connection;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ArrayList<Items> items = new ArrayList<Items>();
        Scanner in = new Scanner(System.in);
        connectToDb();
        while (true) {
        System.out.println("PRESS [1] TO ADD ITEMS");
        System.out.println("PRESS [2] TO LIST ITEMS");
        System.out.println("PRESS [3] TO DELETE ITEMS");
        System.out.println("PRESS [0] TO EXIT");
        String choise = in.next();
            if (choise.equals("1")){
                System.out.println("INSERT NAME");
                String name = in.next();
                System.out.println("INSERT PRICE");
                int price = in.nextInt();
                Items i = new Items(null,name,price);
                addItems(i);
            }
        if (choise.equals("2")){
            items = getAllItems();
            for (Items i : items){
                System.out.println(i);
            }
        }
        if (choise.equals("3")){
            System.out.println("INSERT ID");
            Long id = in.nextLong();
            deleteItem(id);
        }

        if (choise.equals("0")) break;
        }
    }
        public static void connectToDb() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/group0801?useUnicode=true&serverTimezone=UTC","root", ""
            );
            System.out.println("CONNECTED"); 
        }
        public static ArrayList<Items> getAllItems() throws SQLException {
        ArrayList<Items> items = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("select * from items");
        ResultSet rs = st.executeQuery();//executeQuery(); - получить данные
        while (rs.next()){
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            items.add(new Items(id,name,price));
        }
        return items;
    }
    public static void addItems (Items i) throws SQLException {
        PreparedStatement st = connection.prepareStatement("insert into items(name,price) values (?,?) ");
        st.setString(1,i.getName());
        st.setInt(2,i.getPrice());
        st.executeUpdate();//Обновление данных
    }
    public static void deleteItem(Long id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM items where id = ?");
        st.setLong(1,id);
        st.executeUpdate();
        st.close();
    }
}


