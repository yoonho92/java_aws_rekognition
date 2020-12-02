

import java.util.ArrayList;

public interface AwsDao {
	public int insert(AwsVo AwsVo);
	public int delete(AwsVo AwsVo);
	public int update(AwsVo AwsVo);
	public ArrayList<TestVo> selectAll();
	public AwsVo selectOne();

}
