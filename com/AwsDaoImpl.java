

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AwsDaoImpl implements AwsDao {
	Connection conn = null;
	PreparedStatement exec = null;
	ResultSet rs = null;
	String URL = "jdbc:oracle:thin:@yoonhonas.synology.me:12000:xe";
	String ID = "hr";
	String PW = "codbsgh1!";
	public AwsDaoImpl() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("class load OK");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public int insert(AwsVo AwsVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(AwsVo AwsVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(AwsVo AwsVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<TestVo> selectAll() {
		// TODO Auto-generated method stub
		ArrayList<TestVo> voList = new ArrayList<TestVo>();
		try {

			conn = DriverManager.getConnection(URL, ID, PW);
			System.out.println("연결성공");
			String sql = "select * from departments";
			exec = conn.prepareStatement(sql);
			rs = exec.executeQuery();
			while (rs.next()) {
				TestVo vo = new TestVo();
				vo.setDepartment_id(rs.getInt("department_id"));
				vo.setDepartment_name(rs.getString("department_name"));
				vo.setManager_id(rs.getInt("manager_id"));
				vo.setLocation_id(rs.getInt("location_id"));
				voList.add(vo);
				// 어느순간 vos값이똑같아지는경우 추측 한번 생성된 vo의 인스턴스가 할당된 메모리의 주소가
				// vos에 대입된 vo를 가리키는 주소가 같아서 어느순간 vo.set으로 설정된 값이 일괄적으로
				// 변경된 것이 아닐까
				// 매번 새로운 new로 새로운 메모리주소를 할당해서 이미 vos에 할당된 vo의 메모리주소에
				// 간섭하지 않도록 하기

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("실행실패");
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (exec != null) {
				try {
					exec.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return voList;
		
	}

	@Override
	public AwsVo selectOne() {
		// TODO Auto-generated method stub
		return null;
	}

}
