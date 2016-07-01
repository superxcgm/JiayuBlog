import javax.swing.*;
public class User{
   private String nickName;
   private String userName;
   private int uid;
   private String password;
   private String phoneNum;
   private String profile;
   private int followCnt;
   private int fansCnt;
   private int blogCnt;
   private String headUrl;
   private Icon headIcon;

   public User(int uid){
       this.uid = uid;
       refreshInfo();
   }
   public void refreshInfo(){
       JiayuDB jiayuDB = new JiayuDB();
       jiayuDB.setDBName("superxc");
       String SQL = "select * from userInfo where uid=" + uid;
       String[][] ans = jiayuDB.query(SQL);
       userName = ans[0][1];
       password = ans[0][2];
       nickName = ans[0][3];
       phoneNum = ans[0][4];
       profile = ans[0][5];
       followCnt = Integer.parseInt(ans[0][6]);
       fansCnt = Integer.parseInt(ans[0][7]);
       blogCnt = Integer.parseInt(ans[0][8]);
       headUrl = ans[0][9];
       headIcon = new ImageIcon(headUrl);
   }
   public Icon getHeadIcon(){
       if(headUrl.equals(""))
           return new ImageIcon("img/head_72px.png");
       return headIcon;
   }
   public String getUserName(){
       return userName;
   }
   public String getNickName(){
       return nickName;
   }
   public int getUid(){
       return uid;
   }
   public String getPassword(){
       return password;
   }
   public String getProfile(){
       return profile;
   }
   public int getFollowCnt(){
       return followCnt;
   }
   public int getFansCnt(){
       return fansCnt;
   }
   public int getBlogCnt(){
       return blogCnt;
   }
   public String getHeadUrl(){
       return headUrl;
   }
}
