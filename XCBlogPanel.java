import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
public class XCBlogPanel extends JPanel{
    final int FRAME_HEIGHT = 600;
    final int FRAME_WIDEN = 800;
    final int BLOG_AREA = 550;
    final int RIGHT_AREA = FRAME_WIDEN-BLOG_AREA;
    final int BLOG_BOX_NOR_HEIGHT=150;
    JLabel labHead;
    JLabel labShow;
    JLabel labForward;
    JLabel labComment;
    JLabel labNice;
    JiayuDB jiayuDB;
    XCBlogPlHandle handle;
    User userHost;
    Blog blog;
    MainView mainView;
    public XCBlogPanel(MainView mainView,User userHost, Blog blog){
    //public XCBlogPanel(){
        this.userHost = userHost;
        this.blog = blog;
        this.mainView = mainView;
        handle = new XCBlogPlHandle(userHost, this, mainView);
        jiayuDB = new JiayuDB();
        jiayuDB.setDBName("superxc");
        String SQL="";
        String ans[][];
        JPanel plCenter = new JPanel();
        JPanel plSouth = new JPanel();
        plCenter.setLayout(new BoxLayout(plCenter ,BoxLayout.X_AXIS));
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.BLACK));
        setSize(BLOG_AREA, BLOG_BOX_NOR_HEIGHT);
        //setPreferredSize( new Dimension(BLOG_AREA, BLOG_BOX_NOR_HEIGHT));
        labHead = new JLabel();
        //labHead.setIcon(new ImageIcon("img/head_72px.png"));
        User userTmp = new User(blog.getUid());
        labHead.setIcon(userTmp.getHeadIcon());
        labHead.setPreferredSize(new Dimension(80, 80));
        JPanel plTmp = new JPanel();
        plTmp.add(labHead);
        plTmp.setPreferredSize(new Dimension(80, BLOG_BOX_NOR_HEIGHT-40));
        plCenter.add(plTmp);
        plTmp = new JPanel();
        plTmp.setPreferredSize(new Dimension(BLOG_AREA-80, BLOG_BOX_NOR_HEIGHT-40));
        plTmp.setLayout(null);
        //Box box = Box.createVerticalBox();
        //box.setPreferredSize(new Dimension(BLOG_AREA-80, BLOG_BOX_NOR_HEIGHT));
        labShow = new JLabel();
        labShow.setBounds(0,5, BLOG_AREA-80, BLOG_BOX_NOR_HEIGHT-40);
        //small BUG
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("<br>");
        sb.append(blog.getNickName());
        sb.append("<br>");
        sb.append(blog.getPostTime());
        sb.append("<br>");
        sb.append(blog.getContent());
        if(sb.length()<180){
            sb.append("<br><br><br><br><br><br>");
        }
        sb.append("</html>");
        labShow.setText(sb.toString());
        //System.out.println(labShow.getText().length());
        //box.add(new LabelPanel(labShow));
        plTmp.add(labShow);
        //add(box);
        plCenter.add(plTmp);
        plTmp = new JPanel();
        add(plCenter);
        //plSouth.setLayout();
        labForward = new JLabel("0");
        labForward.setIcon(new ImageIcon("img/forward_16px.png"));
        //labForward.addFocusListener(handle);
        labForward.addMouseListener(handle);

        SQL = "select commentCnt from microBlog where blogId=rep";
        SQL = SQL.replaceFirst("rep", ""+blog.getId());
        ans = jiayuDB.query(SQL);
        labComment = new JLabel(ans[0][0]);
        labComment.setIcon(new ImageIcon("img/comment_16px.png"));
        //labComment.addFocusListener(handle);
        labComment.addMouseListener(handle);

        SQL = "select niceCnt from microBlog where blogId=rep";
        SQL = SQL.replaceFirst("rep", ""+blog.getId());
        ans = jiayuDB.query(SQL);
        labNice = new JLabel(ans[0][0]);
        labNice.setIcon(new ImageIcon("img/nice_16px.png"));
        //labNice.addFocusListener(handle);
        labNice.addMouseListener(handle);

        plSouth.add(labForward);
        plSouth.add(new JSeparator(SwingConstants.VERTICAL));
        plSouth.add(labComment);
        plSouth.add(new JSeparator(SwingConstants.VERTICAL));
        plSouth.add(labNice);
        plSouth.add(new JLabel("   "));
        plTmp.setLayout(new BorderLayout());
        plTmp.add(plSouth, BorderLayout.EAST);
        //add(plSouth, BorderLayout.SOUTH);
        add(plTmp, BorderLayout.SOUTH);
    }
}
