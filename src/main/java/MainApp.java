import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by DIO
 */
public class MainApp {

    static Connection conn;
    static Statement st;
    static Repository rep = new Repository();
    static Random ran = new Random();

    public static void main(String[] args) {
        dropTable();
        createDatabase();
        putData();

//        System.out.println(s);
//        rep.insert(student3);
//        rep.update(s);
//        rep.insert(student4);
//       System.out.println(rep.get());

}

        public static void createDatabase(){

            try {
                conn= DriverManager.getConnection("jdbc:sqlite:doc_db");
                String sql = "CREATE TABLE IF NOT EXISTS DOC_STATUS(\n" +
                             "STATUS_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                             "STATUS_NAME TEXT NOT NULL)";
                st=conn.createStatement();
                st.execute(sql);

                sql = "CREATE TABLE IF NOT EXISTS DOC_CATEGORY(\n" +
                        "   CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "   CATEGORY_NAME TEXT )";
                st=conn.createStatement();
                st.execute(sql);

                sql = "CREATE TABLE IF NOT EXISTS DOC_USER(\n" +
                        "   USER_ID INTEGER PRIMARY KEY  AUTOINCREMENT,\n" +
                        "   USER_NAME TEXT NOT NULL)";
                st=conn.createStatement();
                st.execute(sql);

                sql = "CREATE TABLE IF NOT EXISTS DOC(\n" +
                        "   DOC_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "   USER_ID INTEGER NOT NULL,\n" +
                        "   TSHORT TEXT NOT NULL,\n" +
                        "   DOC_DATE TEXT NOT NULL,\n" +
                        "   STATUS_ID INTEGER NOT NULL,\n" +
                        "   CATEGORY_ID INTEGER DEFAULT NULL,\n" +
                        "   LINK_DOC_ID INTEGER DEFAULT NULL,\n" +
                        "   FOREIGN KEY (USER_ID) REFERENCES\n" +
                        "   DOC_USER(USER_ID)\n" +

                        "   FOREIGN KEY (STATUS_ID) REFERENCES\n" +
                        "   DOC_STATUS(STATUS_ID)\n" +

                        "   FOREIGN KEY (CATEGORY_ID) REFERENCES\n" +
                        "   DOC_CATEGORY(CATEGORY_ID) \n" +

                        "   FOREIGN KEY (LINK_DOC_ID) REFERENCES\n" +
                        "   DOC(DOC_ID))";
                st=conn.createStatement();
                st.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        public static void dropTable(){

            try {
                conn=DriverManager.getConnection("jdbc:sqlite:doc_db");
                String sql = "DROP TABLE DOC";
                st=conn.createStatement();
                st.execute(sql);
                sql = "DROP TABLE DOC_CATEGORY";
                st=conn.createStatement();
                st.execute(sql);
                sql = "DROP TABLE DOC_STATUS";
                st=conn.createStatement();
                st.execute(sql);
                sql = "DROP TABLE DOC_USER";
                st=conn.createStatement();
                st.execute(sql);
                System.out.println("DB was deleted");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        public static void putData(){
            Status status1 = new Status(1,"project");
            Status status2 = new Status(2,"published");
            ArrayList<User> users = new ArrayList<>();
            for (int i = 0; i <10 ; i++) {
                users.add( new User(i,"user_name "+i));
            }
            ArrayList<Category> categorys = new ArrayList<>(10);
            for (int i = 0; i <10 ; i++) {
                categorys.add( new Category(i,"cat_name "+i));
            }
            ArrayList<Document> docs = new ArrayList<>(300);
            for (int i = 0; i <500 ; i++) {
                docs.add(new Document(i,users.get(ran.nextInt(3)),"str","1"+(i+100)+"-11-11",status1));
            }


          //  Document doc= new Document(1,user1,"string","1111-11-11",status1);
          //  Document doc2= new Document(2,user2,"string","1411-11-11",status2);
         //   Document doc3= new Document(3,user1,"string","1311-11-11",status1);
         //   Document doc4= new Document(4,user3,"string","1211-11-11",status2);
        //    doc2.setCategory(categorys.get(2));
         //   doc3.setCategory(categorys.get(8));
         //   doc4.setCategory(categorys.get(2));
         //   doc.setCategory(categorys.get(8));

        //    doc.setLink_doc(null);
         //   doc2.setLink_doc(doc);
            rep.insert(status1);
            rep.insert(status2);
            for (User user:users
                 ) {
                rep.insert(user);
            }
            for (Category cat:categorys
            ) {
                rep.insert(cat);
            }
            for (Document doc:docs
                 ) {
                rep.insert(doc);
                System.out.println("Doc number "+doc.getId()+" added");
            }


         //   rep.insert(doc);
         //   rep.insert(doc2);
        }



}
