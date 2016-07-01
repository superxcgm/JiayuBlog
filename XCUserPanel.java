import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
public class XCUserPanel extends JPanel{

    final int FRAME_HEIGHT = 600;
    final int FRAME_WIDEN = 800;
    final int BLOG_AREA = 550;
    final int RIGHT_AREA = FRAME_WIDEN-BLOG_AREA;
    final int BLOG_BOX_NOR_HEIGHT=150;
    private User userGuest;
    private User userHost;
    JLabel labHead;
    JLabel labShow;
    JButton btnFollow;
    public XCUserPanel(MainView viewMain, User userHost, User userGuest){
    //public XCUserPanel(){
        JiayuDB jiayuDB = new JiayuDB();
        jiayuDB.setDBName("superxc");
        ActionListener actionListener = e->{
            //System.out.println(e.getActionCommand());
            if(e.getActionCommand().equals("Follow")){
                String SQL = "insert into follow values(rep,rep,'rep')";
                SQL = SQL.replaceFirst("rep", ""+userHost.getUid());
                SQL = SQL.replaceFirst("rep", ""+userGuest.getUid());
                SQL = SQL.replaceFirst("rep", (new BlogStdTime()).toString());
                //System.out.println(SQL);
                jiayuDB.insert(SQL);
                int followCnt = userHost.getFollowCnt()+1;
                SQL = "update userInfo set followCnt=rep where uid=rep";
                SQL = SQL.replaceFirst("rep", followCnt+"");
                SQL = SQL.replaceFirst("rep", ""+userHost.getUid());
                jiayuDB.update(SQL);
                int fansCnt = userGuest.getFansCnt()+1;
                SQL = "update userInfo set fansCnt=rep where uid=rep";
                SQL = SQL.replaceFirst("rep", ""+fansCnt);
                SQL = SQL.replaceFirst("rep", ""+userGuest.getUid());
                jiayuDB.update(SQL);
                userHost.refreshInfo();
                viewMain.updateUserView();
                JOptionPane.showMessageDialog(null, "You follow "+userGuest.getNickName(),"success.",JOptionPane.INFORMATION_MESSAGE);
                btnFollow.setText("unFoll");
                //btnFollow.setEnabled(false);
            }else{
                String SQL = "delete from follow where starId=rep and fanId=rep";
                SQL = SQL.replaceFirst("rep", ""+userGuest.getUid());
                SQL = SQL.replaceFirst("rep", ""+userHost.getUid());
                System.out.println(SQL);
                jiayuDB.delete(SQL);
                int followCnt = userHost.getFollowCnt()-1;
                SQL = "update userInfo set followCnt=rep where uid=rep";
                SQL = SQL.replaceFirst("rep", followCnt+"");
                SQL = SQL.replaceFirst("rep", ""+userHost.getUid());
                jiayuDB.update(SQL);
                int fansCnt = userGuest.getFansCnt()-1;
                SQL = "update userInfo set fansCnt=rep where uid=rep";
                SQL = SQL.replaceFirst("rep", ""+fansCnt);
                SQL = SQL.replaceFirst("rep", ""+userGuest.getUid());
                jiayuDB.update(SQL);
                userHost.refreshInfo();
                viewMain.updateUserView();
                JOptionPane.showMessageDialog(null, "You unfollow "+userGuest.getNickName(),"success.",JOptionPane.INFORMATION_MESSAGE);
                btnFollow.setText("Follow"); 
                //Bug
            }
        };
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setSize(BLOG_AREA, BLOG_BOX_NOR_HEIGHT);
        setBorder(new LineBorder(Color.BLACK));
        //setPreferredSize( new Dimension(BLOG_AREA, BLOG_BOX_NOR_HEIGHT));
        labHead = new JLabel();
        //labHead.setIcon(new ImageIcon("img/head_72px.png"));
        labHead.setIcon(userGuest.getHeadIcon());
        JPanel plTmp = new JPanel();
        plTmp.add(labHead);
        btnFollow = new JButton("Follow");
        btnFollow.addActionListener(actionListener);
        String SQL="select * from follow where fanId=rep and starId=rep";
        SQL = SQL.replaceFirst("rep", ""+userHost.getUid());
        SQL = SQL.replaceFirst("rep", ""+userGuest.getUid());
        String[][] ans = jiayuDB.query(SQL); 
        if(ans.length!=0 || userGuest.getUid()==userHost.getUid())btnFollow.setText("unFoll");
        plTmp.add(btnFollow);
        plTmp.setPreferredSize(new Dimension(80, BLOG_BOX_NOR_HEIGHT));
        add(plTmp);
        plTmp = new JPanel();
        plTmp.setLayout(null);
        plTmp.setPreferredSize(new Dimension(BLOG_AREA-80, BLOG_BOX_NOR_HEIGHT));
        //Box box = Box.createVerticalBox();
        //box.setPreferredSize(new Dimension(BLOG_AREA-80, BLOG_BOX_NOR_HEIGHT));
        labShow = new JLabel();
        labShow.setBounds(0, 0, BLOG_AREA-80,BLOG_BOX_NOR_HEIGHT);
        plTmp.add(labShow);
        StringBuilder sb = new StringBuilder("<html>");
        sb.append(userGuest.getNickName());
        sb.append("<br>");
        sb.append("Following:"+userGuest.getFollowCnt() + " Fans:"+ userGuest.getFansCnt() +" Weibo:"+userGuest.getBlogCnt());
        sb.append("<br>");
        sb.append("Profile: The person is lazy to fill this.");
        if(sb.length()<180){
            sb.append("<br><br><br><br><br><br>");
        }
        sb.append("</html>");
        labShow.setText(sb.toString());
        //box.add(new LabelPanel(labShow));
        //plTmp = new JPanel();
        //plTmp.setLayout(new BorderLayout());
        //btnFollow = new JButton("Follow");        
        //plTmp.add(btnFollow, BorderLayout.EAST);
        add(plTmp);
    }
}
