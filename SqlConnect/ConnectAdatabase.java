package ch16_sec02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectAdatabase {
	public static Connection makeConnection() {
		String ur1 = "jdbc:oracle:thin:@127.0.0.1:1521";
		String user = "hr";
		String pwd = "hr";
		Connection con = null;
		try {
			// 1. 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			System.out.println("OracleDriver 적재성공");
			
			// 2. 오라클데이타베이스 연결
			con = DriverManager.getConnection(ur1,user,pwd);
//			System.out.println("오라클 접속 성공");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("오라클 적재 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("오라클 접속 실패");
		}	
		return con;
	}

	public static void main(String[] args) {
		Connection con = makeConnection();
		try {
			// 3. CURD 실행
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM refrigerator");
			// 4. ResultSet 화면출력
			while(rs.next()) {
				int refrigerator = rs.getInt("refrigerator_id");
				String productName = rs.getString("product_name");
				String brand = rs.getString("brand");
				String year = rs.getString("year");
				int price = rs.getInt("price");
				String data = String.format("%3s \t %10s \t %10s \t %5s \t %6d \n",refrigerator,productName,brand,year,price);
				System.out.println(data);
			}
			
		} catch (SQLException e) {
			System.out.println("statement 오류");
		}
		
	}
}
