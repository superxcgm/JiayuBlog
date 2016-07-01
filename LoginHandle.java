import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class LoginHandle implements ActionListener{
    LoginView view;
    JiayuDB jiayuDB; 
    public LoginHandle(LoginView view){
        this.view = view;
        jiayuDB = new JiayuDB();
        jiayuDB.setDBName("superxc");
        jiayuDB.initTable();
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==view.btnLogin || e.getSource()==view.pwdLogin){
            String userName = view.txtUserName.getText().trim();
            String pwdInput = new String(view.pwdLogin.getPassword());
            view.txtUserName.setText(userName);   
            String SQL = "select userPasswd,uid from userInfo where userName='"+userName+"'";
            String[][] ans = jiayuDB.query(SQL);
            if(ans.length==0){
                JOptionPane.showMessageDialog(view, "username do not exist.", "error", JOptionPane.INFORMATION_MESSAGE);
                return ;
            }
            //this is a pit, must trim();
            String pwdQuery = ans[0][0];
            int uid = Integer.parseInt(ans[0][1]);
            //System.out.println("pwdQuery:"+ pwdQuery);
            //System.out.println("pwdInput:"+ pwdInput);
            if(!pwdQuery.equals(pwdInput)){
                JOptionPane.showMessageDialog(view, "Password do not match, try again?", "error", JOptionPane.INFORMATION_MESSAGE);
                return ;
            }
            //System.out.println("Login Success.");
            view.dispose();
            new MainView(new User(uid));
            //already login success;
        }
    }
}
