import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
public class MainView extends JFrame{
    final int FRAME_HEIGHT = 600;
    final int FRAME_WIDEN = 800;
    final int BLOG_AREA = 550;
    final int RIGHT_AREA = FRAME_WIDEN-BLOG_AREA;
    final int BLOG_BOX_NOR_HEIGHT=150;
    JPanel plLeft;
    JPanel plRight;
    JPanel plBlog;
    JLabel labNew;
    JLabel labHome;
    JLabel labSearch;
    JLabel labHead;
    JLabel labFollow;
    JLabel labFans;
    JLabel labBlogCnt;
    JLabel labNickName;
    JTextArea taNew;
    JButton btnPost;
    private User user;
    //for post a new Blog
    Box boxOne;
    Box boxTwo;
    Box box;
    private MainHandle handle;
    boolean showNewState;
    public void refreshBlog(){
        //refresh home blog
        plBlog.removeAll();
        plBlog.repaint();
        //plBlog.setPreferredSize(new Dimension(BLOG_AREA, 10*BLOG_BOX_NOR_HEIGHT));
        //for(int i=0;i<10;i++){
        //    XCBlogPanel blogPlTmp = new XCBlogPanel();
        //    blogPlTmp.setLocation(0, i*BLOG_BOX_NOR_HEIGHT);
        //    plBlog.add(blogPlTmp);
        //}
        JiayuDB jiayuDB = new JiayuDB();
        jiayuDB.setDBName("superxc");
        String SQL = "select starId from follow where fanId="+user.getUid(); 
        String[][] ans = jiayuDB.query(SQL);
        LinkedList<Blog> blogGrp = new LinkedList<Blog>();
        if(ans.length!=0){
            for(int i=0;i<ans.length;i++){
                SQL = "select blogId from microBlog where uid="+ans[i][0];
                String[][] strTmp = jiayuDB.query(SQL);
                if(strTmp.length!=0){
                    int len = Math.min(strTmp.length, 10);
                    for(int j=0;j<len;j++)
                        blogGrp.add(new Blog(Integer.parseInt(strTmp[j][0])));
                }
                //System.out.println(SQL);
                //XCBlogPanel blogPlTmp = new XCBlogPanel();
                //blogPlTmp.setLocation(0, i*BLOG_BOX_NOR_HEIGHT);
                //plBlog.add(blogPlTmp);
            }
        }
       //userself blog
       SQL = "select blogId from microBlog where uid="+user.getUid();
       ans = jiayuDB.query(SQL);
       if(ans.length!=0){
           int len = Math.min(10, ans.length);
           for(int i=0;i<len;i++)
               blogGrp.add(new Blog(Integer.parseInt(ans[i][0])));
       }
        //sort
       blogGrp.sort(new BlogComparator());
       int len = Math.min(blogGrp.size(), 10);
       plBlog.setPreferredSize(new Dimension(BLOG_AREA, len*BLOG_BOX_NOR_HEIGHT));
       for(int i=0;i<len;i++){
            XCBlogPanel blogPlTmp = new XCBlogPanel(this, user, blogGrp.get(i));
            blogPlTmp.setLocation(0, i*BLOG_BOX_NOR_HEIGHT);
            plBlog.add(blogPlTmp);
            
       }
       validate();
    }
    public void showNew(boolean show){
        //show new panel for user to post a blog
        box.removeAll();
        if(show){
            taNew.setText("Have something interesting want to tell your friends?");
            btnPost.setEnabled(false);
            taNew.setForeground(Color.GRAY);
            box.add(boxOne);
        }
        box.add(boxTwo);
        showNewState = show;
        validate();
    }
    private void init(){
        showNewState=false;
        JPanel plPostTa = new JPanel();
        JPanel plPostBtn = new JPanel();
        plPostTa.setLayout(new BorderLayout());
        plPostBtn.setLayout(new BorderLayout());
        handle = new MainHandle(this, user);
        taNew = new JTextArea(5, 22);
        taNew.setLineWrap(true);
        taNew.addFocusListener(handle);
        plPostTa.add(taNew);
        btnPost = new JButton("Post");
        btnPost.addActionListener(handle);
        plPostBtn.add(btnPost, BorderLayout.EAST);
        boxOne = Box.createVerticalBox();
        boxOne.add(plPostTa);
        boxOne.add(plPostBtn);
        plLeft = new JPanel();
        plRight = new JPanel();
        plLeft.setPreferredSize(new Dimension(BLOG_AREA, FRAME_HEIGHT));
        plLeft.setBorder(new LineBorder(Color.BLACK));
        //plLeft.setLayout(new BoxLayout(plLeft, BoxLayout.Y_AXIS));
        //plLeft.setLayout(null);
        plLeft.setLayout(new BorderLayout());
        plBlog = new JPanel();
        plBlog.setLayout(null);
        //plBlog.setLocation(0, 0);
        //plBlog.setBounds(0,0, 300, 400);
        //plBlog.setPreferredSize( new Dimension(550, 1230));
        //plBlog.setPreferredSize( new Dimension(200, 300));
        //plBlog.setBackground(Color.RED);
        plLeft.add(new JScrollPane(plBlog,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        plRight.setPreferredSize(new Dimension(RIGHT_AREA, FRAME_HEIGHT));

        labHome = new JLabel("Home");
        labHome.addMouseListener(handle);
        labHome.setIcon(new ImageIcon("img/home_16px.png"));
        labNew = new JLabel("New");
        labNew.addMouseListener(handle);
        labNew.setIcon(new ImageIcon("img/new_16px.png"));
        labSearch = new JLabel("Search");
        labSearch.addMouseListener(handle);
        labSearch.setIcon(new ImageIcon("img/search_16px.png"));
        JPanel plNewSearch = new JPanel();
        plNewSearch.add(labHome);
        plNewSearch.add(new JLabel("  "));
        plNewSearch.add(labNew);
        plNewSearch.add(new JLabel("  "));
        plNewSearch.add(labSearch);
       
        JPanel plHead = new JPanel();
        labHead = new JLabel(new ImageIcon("img/head_72px.png"));
        labHead.addMouseListener(handle);
        plHead.add(labHead);
        //plHead.setPreferredSize(new Dimension(RIGHT_AREA, 110));
        JPanel plFollowFanBlogCnt = new JPanel();
        plFollowFanBlogCnt.setLayout(new BoxLayout(plFollowFanBlogCnt, BoxLayout.X_AXIS));
        Box boxVertTmp = Box.createVerticalBox();
        labFollow = new JLabel("999");
        labFollow.addMouseListener(handle);
        labFollow.setFont(new Font("黑体", Font.BOLD, 15));
        boxVertTmp.add(new LabelPanel(labFollow));
        JLabel labTmp = new JLabel("Following");
        labTmp.setForeground(Color.GRAY);
        boxVertTmp.add(new LabelPanel(labTmp));
        plFollowFanBlogCnt.add(boxVertTmp);
        plFollowFanBlogCnt.add(new JSeparator(SwingConstants.VERTICAL));
        boxVertTmp = Box.createVerticalBox();
        labFans = new JLabel("9999");
        labFans.addMouseListener(handle);
        labFans.setFont(new Font("黑体", Font.BOLD, 15));
        boxVertTmp.add(new LabelPanel(labFans));
        labTmp = new JLabel("Followers");
        labTmp.setForeground(Color.GRAY);
        boxVertTmp.add(new LabelPanel(labTmp));
        plFollowFanBlogCnt.add(boxVertTmp);
        plFollowFanBlogCnt.add(new JSeparator(SwingConstants.VERTICAL));
        boxVertTmp = Box.createVerticalBox();
        labBlogCnt = new JLabel("999");
        labBlogCnt.addMouseListener(handle);
        labBlogCnt.setFont(new Font("黑体", Font.BOLD, 15));
        boxVertTmp.add(new LabelPanel(labBlogCnt));
        labTmp = new JLabel("Weibo");
        labTmp.setForeground(Color.GRAY);
        boxVertTmp.add(new LabelPanel(labTmp));
        plFollowFanBlogCnt.add(boxVertTmp);


        boxTwo = Box.createVerticalBox();
        boxTwo.add(plNewSearch);
        boxTwo.add(plHead);
        JPanel panelTmp = new JPanel();
        //user.nickName
        labNickName = new JLabel("小川Kevin");
        labNickName.addMouseListener(handle);
        panelTmp.add(labNickName);
        boxTwo.add(panelTmp);
        boxTwo.add(plFollowFanBlogCnt);
        
        box = Box.createVerticalBox();
        box.add(boxTwo);
        plRight.add(box);

        add(plLeft);
        add(plRight, BorderLayout.EAST);
    }
    public void updateUserView(){
        user.refreshInfo();
        labNickName.setText(user.getNickName());
        labFollow.setText(user.getFollowCnt()+"");
        labFans.setText(user.getFansCnt()+"");
        labBlogCnt.setText(user.getBlogCnt()+"");
        labHead.setIcon(user.getHeadIcon());
    }
	public MainView(User user){
		setTitle("JiayuBlog v1.0 by Superxc.com");
		setBounds(10,10, 800, 600);
        this.user = user;
        init();
        updateUserView();
        validate();
        refreshBlog();
		setVisible(true);
        //setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
    public static void main(String[] args){
        new MainWnd(new User(1001));
    }
}
