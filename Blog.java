import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Blog{
    private int id;
    private int uid;
    private String nickName;
    private String content;
    private int commentCnt;
    private String imgUrl;
    private int repostCnt;
    private String postTime;
    private int niceCnt;
    public Blog(int blogId){
       JiayuDB jiayuDB = new JiayuDB();
       jiayuDB.setDBName("superxc");
       String SQL = "select * from microBlog where blogId="+blogId;
       String[][] ans = jiayuDB.query(SQL);
       id=blogId;
       uid = Integer.parseInt(ans[0][1]);
       content = ans[0][2];
       commentCnt =Integer.parseInt(ans[0][3]); 
       imgUrl = ans[0][4];
       repostCnt = Integer.parseInt(ans[0][5]);
       postTime = ans[0][6];
       niceCnt = Integer.parseInt(ans[0][7]);
       SQL = "select nickName from userInfo where uid="+uid;
       ans = jiayuDB.query(SQL);
       nickName = ans[0][0];
    }
    public String getNickName(){
        return nickName;
    }
    public int getId(){
        return id;
    }
    public int getUid(){
        return uid;
    }
    public String getContent(){
        return content;
    }
    public int getCommentCnt(){
        return commentCnt;
    }
    public String getImgUrl(){
        return imgUrl;
    }
    public int getRepostCnt(){
        return repostCnt;
    }
    public String getPostTime(){
        return postTime;
    }
    public int getNiceCnt(){
        return niceCnt;
    }
}
