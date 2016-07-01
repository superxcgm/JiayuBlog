import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class XCBlogPlHandle implements MouseListener,ActionListener,FocusListener{
    final int FRAME_HEIGHT = 600;
    final int FRAME_WIDEN = 800;
    final int BLOG_AREA = 550;
    final int RIGHT_AREA = FRAME_WIDEN-BLOG_AREA;
    final int BLOG_BOX_NOR_HEIGHT=150;
    XCBlogPanel view;
    String ans[][];
    String SQL;
    JiayuDB jiayuDB;
    User userHost;
    MainView mainView;
    JLabel labComment;
    JTextArea taComment;
    public XCBlogPlHandle(User userHost, XCBlogPanel view, MainView mainView){
        this.view = view;
        this.mainView = mainView;
        this.userHost = userHost;
        jiayuDB = new JiayuDB();
        jiayuDB.setDBName("superxc");
    }
    public void focusGained(FocusEvent e){
    }
    public void focusLost(FocusEvent e){
    }
    public void actionPerformed(ActionEvent e){

    }
    private void refresh(){
        SQL = "select commentCnt from microBlog where blogId=rep";
        SQL = SQL.replaceFirst("rep", ""+view.blog.getId());
        ans = jiayuDB.query(SQL);
        view.labComment.setText(ans[0][0]);
    }
    public void mouseClicked(MouseEvent e){
        if(e.getSource()==view.labForward){
            JOptionPane.showMessageDialog(null,"Sorry, Do not support this feature.");
        }else if(e.getSource()==view.labComment){
            mainView.plBlog.removeAll();
            mainView.plBlog.repaint();

            mainView.plBlog.setPreferredSize(new Dimension(BLOG_AREA, 4*BLOG_BOX_NOR_HEIGHT));
            view.setLocation(0, 0);
            mainView.plBlog.add(view);
            //do not finish 
            taComment = new JTextArea(2, 20);
            ActionListener listener = er->{
                //...
                SQL = "insert into commentBlog values(rep, rep,'rep','rep')";
                SQL = SQL.replaceFirst("rep", view.blog.getId()+"");
                SQL = SQL.replaceFirst("rep", userHost.getUid()+"");
                SQL = SQL.replaceFirst("rep", taComment.getText().trim());
                SQL = SQL.replaceFirst("rep", (new BlogStdTime()).toString());
                //System.out.println(SQL);
                jiayuDB.insert(SQL);
                StringBuilder sb = new StringBuilder(labComment.getText().replaceFirst("</html>", ""));
                sb.append(userHost.getNickName()+""+taComment.getText().trim()+"<br>");
                sb.append("<br>");
                labComment.setText(sb.toString()+"</html>");
                view.validate();
                SQL = "update microBlog set commentCnt=rep where blogId=rep";
                SQL = SQL.replaceFirst("rep", (1+view.blog.getCommentCnt())+"");
                SQL = SQL.replaceFirst("rep", view.blog.getId()+"");
                //System.out.println(SQL);
                jiayuDB.update(SQL);
                refresh();
                JOptionPane.showMessageDialog(null, "Comment success!");
                //...refresh have some error
            };
            taComment.setSize(BLOG_AREA-130, 25);
            taComment.setLocation(5, BLOG_BOX_NOR_HEIGHT+5);
            mainView.plBlog.add(taComment); 

            JButton btnComment = new JButton("comment");
            btnComment.setSize(100, 25);
            btnComment.setLocation(5+BLOG_AREA-130+5, BLOG_BOX_NOR_HEIGHT+5);
            btnComment.addActionListener(listener);
            mainView.plBlog.add(btnComment);
            StringBuilder sb = new StringBuilder("<html>"); 
            sb.append("<br>");
            //sb.append("ttesttesttesttesttesttesttestest");
            //sb.append("<br>");
            //sb.append("ttesttesttesttesttesttesttestest");
            //sb.append("<br>");
            //sb.append("ttesttesttesttesttesttesttestest");
            SQL = "select * from commentBlog where blogId=rep order by commentTime" ;
            SQL = SQL.replaceFirst("rep", view.blog.getId()+"");
            //System.out.println(SQL);
            ans = jiayuDB.query(SQL);
            //System.out.println(ans);
            int len = Math.min(10, ans.length);
            for(int i=0;i<len;i++){
                int uid = Integer.parseInt(ans[i][1]);
                String strComment = ans[i][2];
                User user = new User(uid);
                sb.append(user.getNickName()+": "+strComment);
                sb.append("<br>");
                sb.append("<br>");
            }
            //getComment and add to sb;
            //sb.append("</html>");
            labComment = new JLabel(sb.toString()+"</html>");
            labComment.setLocation(5, BLOG_BOX_NOR_HEIGHT+5+25+5);
            labComment.setSize(BLOG_AREA, (len+2)*40);
            //labComment.setSize(BLOG_AREA, (10+2)*20);
            mainView.plBlog.add(labComment);
            mainView.validate();
        }else if(e.getSource()==view.labNice){
            SQL = "select * from niceBlog where uid=rep and blogId=rep";
            SQL = SQL.replaceFirst("rep", ""+userHost.getUid());
            SQL = SQL.replaceFirst("rep", ""+view.blog.getId());
            //System.out.println(SQL);
            ans = jiayuDB.query(SQL);
            if(ans.length!=0){
                //host have been nice this, click to unNice
                SQL = "delete from niceBlog where uid=rep and blogId=rep";
                SQL = SQL.replaceFirst("rep", userHost.getUid()+"");
                SQL = SQL.replaceFirst("rep", view.blog.getId()+"");
                //System.out.println(SQL);
                jiayuDB.delete(SQL);
                int niceCnt = Integer.parseInt(view.labNice.getText())-1;
                view.labNice.setText(""+niceCnt);
                SQL = "update microBlog set niceCnt=rep where blogId=rep";
                SQL = SQL.replaceFirst("rep", niceCnt+"");
                SQL = SQL.replaceFirst("rep", view.blog.getId()+"");
                //System.out.println(SQL);
                jiayuDB.update(SQL);
                return ;
            }
            int niceCnt = Integer.parseInt(view.labNice.getText())+1;
            view.labNice.setText(""+niceCnt);
            SQL = "update microBlog set niceCnt=rep where blogId=rep";
            SQL = SQL.replaceFirst("rep", niceCnt+ "");
            SQL = SQL.replaceFirst("rep", view.blog.getId()+"");
            //System.out.println(SQL);
            jiayuDB.update(SQL);
            SQL = "insert into niceBlog values(rep,rep)";
            SQL = SQL.replaceFirst("rep", userHost.getUid()+"");
            SQL = SQL.replaceFirst("rep", view.blog.getId()+"");
            //System.out.println(SQL);
            jiayuDB.insert(SQL);
        }
    }
    public void mouseEntered(MouseEvent e){
        JLabel lab = (JLabel)e.getSource();
        lab.setForeground(Color.RED);
    }
    public void mouseExited(MouseEvent e){
        JLabel lab = (JLabel)e.getSource();
        lab.setForeground(Color.BLACK);

    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){
    }
}
