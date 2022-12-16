package greenAppleInsertData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
 
public class InsertReview {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
        String id = "scott";
        String pw = "tiger";
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, id, pw);
            conn.setAutoCommit(false);
            String sql = "INSERT INTO review VALUES (?, ?, ?, ?, ?, sysdate, ?)";
            
            pstmt = conn.prepareStatement(sql);
            for (int i = 1; i < 10; i++) {
	            pstmt.setString(1,"2212081121" + i); // reviewId s
	            pstmt.setString(2, "member" + i); // memberId s
	            pstmt.setString(3, "3333"); // productCode s
	            pstmt.setString(4, "review"); // content s
	            pstmt.setString(5, "n"); // fileName s
	            // pstmt.setString(5,"00"); // reviewDate d
	            pstmt.setString(6,"n"); // del s
	            pstmt.executeUpdate();
            }
            pstmt.close();
            conn.close();
            
            System.out.println("success");
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(pstmt!=null) {pstmt.close();}            
            }catch(Exception e) {
                e.printStackTrace();
            }
            try {
                if(conn!=null) {conn.close();}
                
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
