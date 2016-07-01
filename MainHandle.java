import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class MainHandle implements MouseListener,ActionListener,FocusListener{
    final int FRAME_HEIGHT = 600;
    final int FRAME_WIDEN = 800;
    final int BLOG_AREA = 550;
    final int RIGHT_AREA = FRAME_WIDEN-BLOG_AREA;
    final int BLOG_BOX_NOR_HEIGHT=150;
    private JiayuDB jiayuDB;
    private MainView view;
    private User user;
    public void focusGained(FocusEvent e){
        if(e.getSource()==view.taNew){
            if(view.taNew.getText().equals("Have something interesting want to tell your friends?")){
                view.taNew.setText("");
                view.btnPost.setEnabled(true);
                view.taNew.setForeground(Color.BLACK);
            }
        }
    }
    public void focusLost(FocusEvent e){
    }
    public MainHandle(MainView view, User user){
        this.user = user;
        jiayuDB = new JiayuDB();
        jiayuDB.setDBName("superxc");
        this.view = view;
    }
    public void mouseEntered(MouseEvent e){
        JLabel labTmp = (JLabel)e.getSource();
        labTmp.setForeground(Color.RED);
    }
    public void mouseExited(MouseEvent e){
        JLabel labTmp = (JLabel)e.getSource();
        labTmp.setForeground(Color.BLACK);
    }
    public void mouseClicked(MouseEvent e){
        if(e.getSource()==view.labHead){
            new HeadModifyView(view, user);
            //JOptionPane.showMessageDialog(view, "Do not surrort head image modify.","info",JOptionPane.INFORMATION_MESSAGE);
        }else if(e.getSource()==view.labNew){
            view.showNew(!view.showNewState);
        }else if(e.getSource()==view.labSearch){
            Object obj= JOptionPane.showInputDialog(view, "Please input the user's nickName you want to find.", "Search", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("img/Person_search_32px.png"),null,null);
            if(obj!=null){
                String strNickName = obj.toString();
                if(strNickName.equals(""))return;
                //ingnore lower and upper
                String SQL = "select uid from userInfo where lower(nickName) like lower('%rep%')";
                SQL = SQL.replaceFirst("rep", strNickName);
                String[][] ans = jiayuDB.query(SQL);
                view.plBlog.removeAll();
                view.plBlog.repaint();
                view.plBlog.setPreferredSize(new Dimension(BLOG_AREA, ans.length*150));
                for(int i=0;i<ans.length;i++){
                    XCUserPanel xcUserPanel = new XCUserPanel(view,user,new User(Integer.parseInt(ans[i][0])));
                    xcUserPanel.setLocation(0, i*150);
                    view.plBlog.add(xcUserPanel); 
                }
                view.validate();
                //System.out.println("nums:"+view.plBlog.getComponentCount());
            }
        }else if(e.getSource()==view.labHome){
            view.refreshBlog();
        }else if(e.getSource()==view.labFollow){
            //show the star you followed
            view.plBlog.removeAll();
            view.plBlog.repaint();
            String SQL = "select starId from follow where fanId=rep order by followTime desc";
            SQL = SQL.replaceFirst("rep", ""+user.getUid()); 
            //System.out.println(SQL);
            String[][] ans = jiayuDB.query(SQL);
            int len = Math.min(10, ans.length);
            //System.out.println(len);
            view.plBlog.setPreferredSize(new Dimension(BLOG_AREA, len*150));
            for(int i=0;i<len;i++){
                XCUserPanel xcUserPanel = new XCUserPanel(view,user,new User(Integer.parseInt(ans[i][0])));
                xcUserPanel.setLocation(0, i*150);
                view.plBlog.add(xcUserPanel); 
            }
            view.validate();
        }else if(e.getSource()==view.labFans){
            //show your fans
            view.plBlog.removeAll();
            view.plBlog.repaint();
            String SQL = "select fanId from follow where starId=rep order by followTime desc";
            SQL = SQL.replaceFirst("rep", ""+user.getUid()); 
            //System.out.println(SQL);
            String[][] ans = jiayuDB.query(SQL);
            int len = Math.min(10, ans.length);
            view.plBlog.setPreferredSize(new Dimension(BLOG_AREA, len*150));
            for(int i=0;i<len;i++){
                XCUserPanel xcUserPanel = new XCUserPanel(view,user,new User(Integer.parseInt(ans[i][0])));
                xcUserPanel.setLocation(0, i*150);
                view.plBlog.add(xcUserPanel); 
            }
            view.validate();
        }else if(e.getSource()==view.labBlogCnt){
            //show your blog;
            view.plBlog.removeAll();
            view.plBlog.repaint();
            String SQL = "select blogId from microBlog where uid=rep";
            SQL = SQL.replaceFirst("rep", ""+user.getUid());
            //this well have a bug when user blog number are huge;
            //others have the same bug
            String[][] ans = jiayuDB.query(SQL);
            LinkedList<Blog> blogGrp = new LinkedList<Blog>();
            for(int i=0;i<ans.length;i++)
                blogGrp.add(new Blog(Integer.parseInt(ans[i][0]))); 
            blogGrp.sort(new BlogComparator());
            int len = Math.min(10, ans.length);
            view.plBlog.setPreferredSize(new Dimension(BLOG_AREA, len*BLOG_BOX_NOR_HEIGHT));
            for(int i=0;i<len;i++){
                XCBlogPanel blogPlTmp = new XCBlogPanel(view, user, blogGrp.get(i));
                blogPlTmp.setLocation(0, i*BLOG_BOX_NOR_HEIGHT);
                view.plBlog.add(blogPlTmp);
            }
            view.validate();
        }else if(e.getSource()==view.labNickName){
            //modify your nickName;
            Object obj= JOptionPane.showInputDialog(view, "Please input the your new nickName.", "Rename", JOptionPane.INFORMATION_MESSAGE);
            if(obj!=null){
                String strNickName = obj.toString().trim();
                if(strNickName.equals(""))return;
                String SQL = "update userInfo set nickName='rep' where uid=rep";
                SQL = SQL.replaceFirst("rep", strNickName);
                SQL = SQL.replaceFirst("rep", ""+user.getUid());
                //do not finish
                //System.out.println(SQL);
                jiayuDB.update(SQL);
                view.updateUserView();
            }
        }
    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==view.btnPost){
            //post new blog
            String SQL = "select lValue from sysBlog where pName='blogCnt'";
            view.taNew.setText(view.taNew.getText().trim());
            int blogId=0;
            String[][] ans = jiayuDB.query(SQL);
            blogId = Integer.parseInt(ans[0][0])+1;
            //insert into microBlog values(1001,1001,'我去年买了个包',0,'',0,'2016-06-20 08:33:25',0)
            SQL = "insert into microBlog values(rep,rep,'rep',0,'',0,'rep',0)";
            SQL = SQL.replaceFirst("rep",""+blogId);
            SQL = SQL.replaceFirst("rep",""+user.getUid());
            SQL = SQL.replaceFirst("rep", view.taNew.getText());
            SQL = SQL.replaceFirst("rep", (new BlogStdTime()).toString());
            //System.out.println(SQL);
            jiayuDB.insert(SQL);
            SQL = "update sysBlog set lValue="+String.valueOf(blogId)+" where pName='blogCnt'";
            //System.out.println(SQL);
            jiayuDB.update(SQL);
            SQL = "update userInfo set microCnt=rep where uid=rep";
            SQL = SQL.replaceFirst("rep", ""+(1+user.getBlogCnt()));
            SQL = SQL.replaceFirst("rep", ""+user.getUid());
            jiayuDB.update(SQL);
            user.refreshInfo();
            view.updateUserView();
            JOptionPane.showMessageDialog(view,"Your weibo have benn posted.", "success", JOptionPane.INFORMATION_MESSAGE);
            view.taNew.setText(""); 
            view.showNew(false);
        }
    }
}
