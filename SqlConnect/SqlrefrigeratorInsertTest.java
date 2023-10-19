package ch16_sec02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SqlrefrigeratorInsertTest {
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		boolean flag = false;

		while (!flag) {
			System.out.println("1: select, 2: insert, 3: update, 4: delete, 5: exit");
			int num = scan.nextInt();
			scan.nextLine();

			switch (num) {
			case 1:
				refrigeratorSelect();
				break;
			case 2:
				refrigeratorInsert();
				break;
			case 3:
				refrigeratorUpdate();
				break;
			case 4:
				refrigeratorDelete();
				break;
			case 5:
				flag = true;
				break;
			}
		}
		System.out.println("The end");

	}

	private static void refrigeratorUpdate() {
		refrigeratorSelect();
		System.out.println("수정할 refrigerator_Id 입력>>");
		int refrigeratorId = scan.nextInt();
		scan.nextLine();
		Refrigerator ref = refrigeratorSelectRefrigeratorId(refrigeratorId);
		if(ref == null) {
			System.out.printf("refrigerator_id = %d 아이디는 존재하지 않습니다. \n",refrigeratorId);
			return;
		}
		Connection con = ConnectAdatabase.makeConnection();
		PreparedStatement pstmt = null;
		try {
			System.out.printf("(%s)수정 입력>>",ref.getProductName());
			String productName = scan.nextLine().trim();
			if(productName.equals("")) {
				productName = ref.getProductName();
			}
			System.out.printf("(%s)수정 입력>>",ref.getBrand());
			String brand = scan.nextLine().trim();
			if(brand.equals("")) {
				brand = ref.getBrand();
			}
			System.out.printf("(%s)년도 수정 입력>>",ref.getYear());
			String year = scan.nextLine().trim();
			if(year.equals("")) {
				year = ref.getYear();
			}
			System.out.printf("(%d)가격 수정 입력>>",ref.getPrice());
			int price = scan.nextInt();
						
			scan.nextLine(); // 버퍼 지우기
			// 3. CURD 실행
			String query = String.format(
					"update refrigerator set product_name = ?, brand = ?, year = ?, price = ? where refrigerator_id = ?");
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, productName);
			pstmt.setString(2, brand);
			pstmt.setString(3, year);
			pstmt.setInt(4, price);
			pstmt.setInt(5, refrigeratorId);
			int count = pstmt.executeUpdate();
			// 4. count 체크
			if (count == 0) {
				System.out.printf("refrigerator_id = %d  Update 실패 \n", refrigeratorId);
			} else {
				System.out.printf("refrigerator_id = %d Update 성공 \n", refrigeratorId);
			}

		} catch (SQLException e) {
			System.out.println("statement 오류");
		} finally {
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}

	}

	private static Refrigerator refrigeratorSelectRefrigeratorId(int refrigeratorId) {
	    Connection con = ConnectAdatabase.makeConnection();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Refrigerator ref = null;

	    try {
	        String query = "SELECT * FROM refrigerator WHERE refrigerator_id = ?";
	        pstmt = con.prepareStatement(query);
	        pstmt.setInt(1, refrigeratorId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int _refrigeratorId = rs.getInt("refrigerator_ID");
	            String productName = rs.getString("product_name");
	            String brand = rs.getString("brand");
	            String year = rs.getString("year");
	            int price = rs.getInt("price");
	            ref = new Refrigerator(_refrigeratorId, productName, brand, year, price);
	            String data = String.format("%3s \t %-30s \t %-10s \t %5s \t %6d \n", _refrigeratorId, productName, brand, year,
	                    price);
	            System.out.println(data);
	        }
	    } catch (SQLException e) {
	        System.out.println("statement 오류 ");
	        e.printStackTrace(); // 예외 메시지 출력
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return ref;
	}


	

	
	private static void refrigeratorDelete() {
		refrigeratorSelect();
		Connection con = ConnectAdatabase.makeConnection();
		PreparedStatement pstmt = null;
		try {
			System.out.println("삭제할 refrigeratorId 입력>>");
			int refrigeratorId = scan.nextInt();
			scan.nextLine(); // 버퍼 지우기
			// 3. CURD 실행
			String query = String.format("delete from refrigerator where refrigerator_id = ?",refrigeratorId );
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, refrigeratorId);
			int count = pstmt.executeUpdate();
			// 4. count 체크
			if (count == 0) {
				System.out.printf("refrigerator_id = %d delete 삭제 대상이 아닙니다. \n", refrigeratorId);
			} else {
				System.out.printf("refrigerator_id = %d delete 삭제 성공 \n", refrigeratorId);
			}

		} catch (SQLException e) {
			System.out.println("preparedStatement 오류");
		} finally {
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}

	}

	private static void refrigeratorInsert() {
		Connection con = ConnectAdatabase.makeConnection();
		PreparedStatement pstmt = null;
		try {
			System.out.println("제품명 입력>>");
			String productName = scan.nextLine().trim();
			System.out.println("제조사 입력>>");
			String brand = scan.nextLine().trim();
			System.out.println("출시년도 입력>>");
			String year = scan.nextLine().trim();
			System.out.println("금액 입력>>");
			int price = scan.nextInt();
			scan.nextLine(); // 버퍼 지우기
			// 3. CURD 실행
			String query = String.format("INSERT INTO refrigerator VALUES (refrigerator_id_seq.nextval, ?, ?, ?,?)");
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, productName);
			pstmt.setString(2, brand);
			pstmt.setString(3, year);
			pstmt.setInt(4, price);
			int count = pstmt.executeUpdate();
			// 4. count 체크
			if (count != 1) {
				System.out.println("Insert 오류발생 ");
			} else {
				System.out.println("Insert 성공 ");
			}

		} catch (SQLException e) {
			System.out.println("statement 오류");
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static void refrigeratorSelect() {
		Connection con = ConnectAdatabase.makeConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 3. CURD 실행
			String query = String.format("SELECT * FROM refrigerator");
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			// 4. ResultSet 화면출력
			while (rs.next()) {
				int refrigeratorId = rs.getInt("refrigerator_ID");
				String productName = rs.getString("product_name");
				String brand = rs.getString("brand");
				String year = rs.getString("year");
				int price = rs.getInt("price");
				String data = String.format("%3s \t %-30s \t %-10s \t %5s \t %6d \n", refrigeratorId, productName, brand, year,
						price);
				System.out.println(data);
			}

		} catch (SQLException e) {
			System.out.println("statement 오류 ");
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
