package greenAppleInsertData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 
public class InsertMember {
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
            String sql = "INSERT INTO member VALUES (?, ?, ?, ?, sysdate, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql);
            for (int i = 1; i < 120; i++) {
	            pstmt.setString(1,"member" + i); // id s
	            pstmt.setString(2, "1111"); // pw s
	            pstmt.setString(3, "홍길동" + i); // name s
	            pstmt.setString(4, "010-1234-5678"); // tel s
	            // pstmt.setString(5, ""); // date d
	            pstmt.setString(5,"주소"); // addr1 s
	            pstmt.setString(6,"상세주소"); // addr2 s
	            pstmt.setString(7,"n"); // delete s
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
