import java.sql.*;
import javax.sql.*;
//import javax.sql.rowset.*;
//import com.sun.rowset.*;
public class JiayuDB{
	String strDBName;
	Connection con;
	Statement sql;
    ResultSet rs;
	public void setDBName(String name){
		strDBName = name.trim();
	}
	public String getDBName(){
		return strDBName;
	}
	public JiayuDB(){
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}catch(Exception e){
			System.out.println(e);
		}
	}
    public void initTable(){
        //connect();
        //String SQL="create table userInfo(uid int primary key, userName char(20), userPasswd char(20), nickName char(20), phoneNumber char(20), profile varchar(100), followCnt int, fansCnt int, microCnt int, headImage varchar(500))";
        //update(SQL);
        //SQL = "create table follow(fanId int, starId int, followTime varchar(50))";
        //update(SQL);
        //SQL = "create table microBlog(blogId int primary key, uid int, blogContent varchar(128), commentCnt int, imageUrl varchar(500),rePostCnt int, postTime varchar(50), niceCnt int)";
        //update(SQL);
        //SQL = "create table sysBlog(pName char(50) ,pValue varchar(200), lValue int)";
        //update(SQL);
        //close();
        //initDate();
    }
    public void initDate(){
        connect();
        String SQL = "delete from ";
        update(SQL + "userInfo");
        update(SQL + "follow");
        update(SQL + "microBlog");
        update(SQL + "sysBlog");
        SQL = "insert into sysBlog values('userCnt','',1000)";
        insert(SQL);
        SQL = SQL.replaceFirst("user", "blog");
        insert(SQL);
        close();
    }
	public boolean insert(String SQL){
		connect();
        boolean result=false;
        try{
	    	result = sql.execute(SQL);
        }catch(SQLException e){
            System.out.println(e);
        }
		close();
		return result;
	}
    public void update(String SQL){
        connect();
        try{
            sql.executeUpdate(SQL);
        }catch(SQLException e){
            System.out.println(e);
        }
        close();
    }
    public boolean delete(String SQL){
        return insert(SQL);
    }
    public String[][] query(String SQL){
        connect();
        String[][] ans=null;
        ResultSetMetaData meta;
        int col=0, row=0;
        //CachedRowSet cls = new CachedRowSetImpl();
        try{
            rs = sql.executeQuery(SQL);
            meta = rs.getMetaData();
            col = meta.getColumnCount();
            rs.last();
            row = rs.getRow();
            ans = new String[row][col];
            //System.out.println(row);
            //System.out.println(col);
            rs.beforeFirst();
            int i=0;
            while(rs.next()){
                for(int j=0;j<col;j++)
                    ans[i][j] = rs.getString(j+1).trim();
                i++;
            }
            //cls.populate(rs);
            //System.out.println(con.getAutoCommit());
            //con.setAutoCommit(false);
            //System.out.println(con.getAutoCommit());
        }catch(SQLException e){
            System.out.println(e);
        }
        close();
        return ans;
    }
	private void connect(){
		try{
			con = DriverManager.getConnection("jdbc:derby:"+strDBName+";create=true");
            //可滚动的，敏感的， 不可更新的结果集
    		sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	private void close(){
        try{
		    con.close();
            if(rs!=null)
                rs.close();
        }catch(SQLException e){
            System.out.println(e);
        }
	}
}
