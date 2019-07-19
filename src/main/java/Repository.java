
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DIO
 */
public class Repository implements IRepositiry {

    Connection conn;
    PreparedStatement st;

    public void insert(Category cat) {
        String sql = "INSERT INTO DOC_CATEGORY (CATEGORY_NAME) VALUES(?)";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setString(1,cat.getName());
            st.execute();
            System.out.println("add cat");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
           try {
               st.close();
               conn.close();
           } catch (SQLException e){
               e.printStackTrace();
           }
        }
    }

    public void insert(Status status) {
        String sql = "INSERT INTO DOC_STATUS (STATUS_NAME) VALUES(?)";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setString(1,status.getName());
            st.execute();
            System.out.println("add status");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void insert(User user) {
        String sql = "INSERT INTO DOC_USER (USER_NAME) VALUES(?)";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setString(1,user.getName());
            st.execute();
            System.out.println("add user");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    public void insert(Document doc) {
        Integer cat_id= null;
        if (!(doc.getCategory()==null)){
            cat_id=doc.getCategory().getId();
        }
        String sql = "INSERT INTO DOC (USER_ID,DOC_DATE,STATUS_ID,\n" +
                "TSHORT) VALUES(?,?,?,?)";
       // String sql = "INSERT INTO DOC (USER_ID,DOC_DATE,STATUS_ID,CATEGORY_ID,\n" +
       //              "TSHORT, LINK_DOC_ID VALUES(?,?,?,?,?,?)";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setInt(1,doc.getId());
            st.setString(2,doc.getDate());
            st.setInt(3,doc.getStatus().getId());
//            st.setInt(4,cat_id);
            st.setString(4,doc.getTshort());
            st.execute();

        //   System.out.println("add doc");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public List<Document> select(User user){

        String sql = "SELECT DOC_ID, TSHORT, DOC_DATE, STATUS_NAME, CATEGORY_NAME, \n" +
                     "LINK_DOC_ID FROM DOC LEFT OUTER JOIN DOC_STATUS ON DOC.STATUS_ID = DOC_STATUS.STATUS_ID \n" +
                     "LEFT OUTER JOIN DOC_CATEGORY ON DOC.CATEGORY_ID = DOC_CATEGORY.CATEGORY_ID \n" +
                     "WHERE USER_ID=?;";
        try {
            conn= MyConnection.getConnection();
            st=conn.prepareStatement(sql);
            st.setInt(1,user.getId());
            ResultSet rs =st.executeQuery();
            while (rs.next()){
                Document doc = new Document();
                doc.setId(rs.getInt("DOC_ID"));
                doc.setTshort(rs.getString("TSHORT"));
                doc.setDate(rs.getString("DOC_DATE"));
            }
            System.out.println("add user");
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }


    }




}
