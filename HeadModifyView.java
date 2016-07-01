import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
public class HeadModifyView extends JDialog{
    JLabel labShow;
    JLabel[] labGrp;
    JPanel plHead;
    JPanel plSouth;
    JButton btnConfirm;
    JButton btnCancel;
    HeadModifyHandle handle;
    User user;
    MainView mainView;

    public HeadModifyView(MainView mainView, User user){  
        setModal(true);
        this.user = user;
        this.mainView = mainView;
        setTitle("Modify head image");
        setBounds( 0, 0, 400, 300);
        init();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    public void init(){
         JPanel plTmp = new JPanel();
         //plTmp.setLayout(null);
         //plTmp.setLayout(new BoxLayout(plTmp,BoxLayout.Y_AXIS));
         handle = new HeadModifyHandle(mainView, this);
         labShow = new JLabel();
         labShow.setPreferredSize(new Dimension(80, 80));
         labShow.setIcon(user.getHeadIcon());
         //labShow.setBounds(3,80,80,80);
         //labShow.setLocation(3,80);
         //labShow.setBorder(new LineBorder(Color.RED));
         labGrp = new JLabel[8];
         for(int i=0;i<8;i++){
             labGrp[i] = new JLabel(new ImageIcon("img/head"+(i+1)+"_72px.png"));
             labGrp[i].addMouseListener(handle);
             labGrp[i].setName("img/head"+(i+1)+"_72px.png");
         }
         plHead = new JPanel();
         plHead.setLayout(new GridLayout(2, 4));
         plHead.setBorder(new LineBorder(Color.BLACK));
         plHead.setPreferredSize(new Dimension(160, 80));
         for(int i=0;i<8;i++)
             plHead.add(labGrp[i]);
         plSouth = new JPanel();
         btnConfirm = new JButton("Confirm");
         btnCancel = new JButton("Cancel");
         plSouth.add(btnConfirm);
         plSouth.add(btnCancel);
         btnConfirm.addActionListener(handle);
         btnCancel.addActionListener(e -> this.dispose());
         plTmp.add(labShow);
         add(plTmp, BorderLayout.WEST);
         add(plHead, BorderLayout.CENTER);
         add(plSouth, BorderLayout.SOUTH);
    }
    public static void main(String[] args){

        //new HeadModifyView();
    }
}
