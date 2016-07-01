import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class LoginView extends JDialog{
    LoginHandle handle;
    JLabel labUserName;
    JLabel labPassword;
    JLabel labReg;
    JTextField txtUserName;
    JPasswordField pwdLogin;
    JButton btnLogin;
    JButton btnCancel;
    //JButton btnReg;
    public LoginView(){
        handle = new LoginHandle(this);
        init();
        validate();
        setVisible(true);
        //setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    private void init(){
       setLayout(null);
       setTitle("Login"); 
	   setBounds(10, 10, 230, 100);
       //Can add image to this dialog on the top of label and txtfield
       labUserName = new JLabel("username:", SwingConstants.RIGHT);
       labPassword = new JLabel("password:", SwingConstants.RIGHT);
       labReg = new JLabel("?");
       labReg.setToolTipText("do not have account? click to new one.");
       txtUserName = new JTextField(30);
       pwdLogin = new JPasswordField(30);
       pwdLogin.addActionListener(handle);
       btnLogin = new JButton("Login");
       btnCancel = new JButton("Cancel");
       //btnReg = new JButton("Reg");
       //btnReg.addActionListener(e -> new RegView());
       btnLogin.addActionListener(handle);
       btnCancel.addActionListener(e -> System.exit(0));
       labReg.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                new RegView();
            }
       });
       

       txtUserName.setHorizontalAlignment(JTextField.CENTER);
       pwdLogin.setHorizontalAlignment(JTextField.CENTER);
        
       labUserName.setBounds(5, 10, 80, 20);
       labPassword.setBounds(5, 40, 80, 20);
       labReg.setBounds(197, 10, 10, 20);
       txtUserName.setBounds(95, 10, 100, 20);
       pwdLogin.setBounds(95, 40, 100, 20);
       btnLogin.setBounds(10, 70, 100, 20);
       //btnReg.setBounds(100, 70, 90, 20);
       btnCancel.setBounds(120, 70, 100, 20);

       add(labUserName);
       add(labPassword);
       add(labReg);
       add(txtUserName);
       add(pwdLogin);
       add(btnLogin);
       add(btnCancel);
       //add(btnReg);
    }
    public static void main(String[] args){
        new LoginView();
    }
}

