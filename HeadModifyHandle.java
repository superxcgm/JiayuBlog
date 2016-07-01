import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
public class HeadModifyHandle implements ActionListener, MouseListener{
    HeadModifyView view;
    MainView mainView;
    JLabel labSelect;
    public HeadModifyHandle(MainView mainView, HeadModifyView view){
        this.mainView = mainView;
        this.view = view;
    }
    public void actionPerformed(ActionEvent e){
        //only for confrom btn, to modify the headImage;
        JiayuDB jiayuDB = new JiayuDB();
        jiayuDB.setDBName("superxc");
        //do not finished.
        String headUrl = labSelect.getName();
        String SQL = "update userInfo set headImage='rep' where uid=rep";
        SQL = SQL.replaceFirst("rep", headUrl);
        SQL = SQL.replaceFirst("rep", ""+view.user.getUid());
        //System.out.println(SQL);
        jiayuDB.update(SQL);
        mainView.updateUserView();                 
        view.dispose();
    }
    public void mouseClicked(MouseEvent e){
        JLabel lab = (JLabel)e.getSource();
        if(lab==view.labGrp[7]){
            JOptionPane.showMessageDialog(view, "Do not support upload your own head image now.","info", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        view.labShow.setIcon(lab.getIcon());
        labSelect = lab;
        //System.out.println(labSelect.getName());
    }
    public void mouseEntered(MouseEvent e){
        JLabel lab = (JLabel)e.getSource();
        lab.setBorder(new LineBorder(Color.BLUE));
    }
    public void mouseExited(MouseEvent e){
        JLabel lab = (JLabel)e.getSource();
        lab.setBorder(null);
    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
}
