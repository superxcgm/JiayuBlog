import java.awt.*;
import javax.swing.*;

public class RegView extends JDialog{
	JTextField txtUserName;
	JPasswordField pwdFir;
	JPasswordField pwdSec;
	JTextField txtNickName;
	JButton btnReg;
	JButton btnCancel;
	JLabel labUserName;
	JLabel labPassword;
	JLabel labRePassword;
	JLabel labNickName;
	XCCheckLabel labUserNameCheck;
	XCCheckLabel labPasswordCheck;
	XCCheckLabel labRePasswordCheck;
	XCCheckLabel labNickNameCheck;
	RegHandle regHandle;
	public RegView(){
        //This must be modify when embed into mainFrame;
        setModal(true);
		setSize(250, 160);
		init();
		setTitle("Register");
		validate();
        //并不实用的自动设置最佳大小
        //pack();
        //测试用的自动填表
        //txtUserName.setText("yujia");
        //pwdFir.setText("123456");
        //pwdSec.setText("123456");
        //txtNickName.setText("QihongFang");
		setVisible(true);
        //setResizable(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	private void init(){
		setLayout(null);
		regHandle = new RegHandle(this);
		labUserName = new JLabel("UserName:", SwingConstants.RIGHT);
		labPassword = new JLabel("Password:", SwingConstants.RIGHT);
		labRePassword = new JLabel("Confirm:", SwingConstants.RIGHT);
		labNickName = new JLabel("NickName:", SwingConstants.RIGHT);		
		add(labUserName);
		add(labPassword); 
        add(labRePassword);
        add(labNickName); 
		txtUserName = new JTextField(30);
		pwdFir = new JPasswordField(30);
		pwdSec = new JPasswordField(30);
		txtNickName = new JTextField(30);
		btnReg = new JButton("Registe");
		btnCancel = new JButton("Cancel");
		btnReg.addActionListener(regHandle);
		btnCancel.addActionListener(e -> this.dispose());
		txtUserName.addFocusListener(regHandle);
		pwdFir.addFocusListener(regHandle);
		pwdSec.addFocusListener(regHandle);
		txtNickName.addFocusListener(regHandle);
		//txtUserName.setText("enter login name");
		//txtUserName.setForeground(Color.GRAY);
		add(txtUserName);
		add(pwdFir);
		add(pwdSec);
		add(txtNickName);
		add(btnReg);
		add(btnCancel);
		
		labUserNameCheck = new XCCheckLabel("-", "Username must start with letter.");
		labPasswordCheck = new XCCheckLabel("-", "The length of password can not less than 6bits.");
		labRePasswordCheck = new XCCheckLabel("-", "The password must be same as before.");
		labNickNameCheck = new XCCheckLabel("-", "Nickname must start with letter.");
		//JLabel没有addActionListener方法
		add(labUserNameCheck);
		add(labPasswordCheck);
		add(labRePasswordCheck);
		add(labNickNameCheck);

		labUserName.setBounds(8, 10, 100, 20);
		labPassword.setBounds(8, 40, 100, 20);
		labRePassword.setBounds(8, 70, 100, 20);
		labNickName.setBounds(8, 100, 100, 20);

		txtUserName.setBounds(110, 10, 100, 20);
		pwdFir.setBounds(110, 40, 100, 20);
		pwdSec.setBounds(110, 70, 100, 20);
		txtNickName.setBounds(110, 100, 100, 20);

		labUserNameCheck.setBounds(215, 10, 15, 25);
		labPasswordCheck.setBounds(215, 40, 15, 25);
		labRePasswordCheck.setBounds(215, 70, 15, 25);
		labNickNameCheck.setBounds(215, 100, 15, 25);

		btnReg.setBounds(8, 130, 100, 20);
		btnCancel.setBounds(130, 130, 100, 20);
		//System.out.println(labUserNameCheck);
	}
	//测试用的main入口
	public static void main(String[] args){
		new RegView();
	}
}
