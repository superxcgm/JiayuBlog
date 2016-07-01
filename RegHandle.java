import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.sql.*;
import javax.sql.rowset.*;
public class RegHandle implements ActionListener, FocusListener{
	RegView view;
    JiayuDB jiayuDB;
	public RegHandle(RegView view){
		this.view = view;
        jiayuDB = new JiayuDB(); 
        jiayuDB.setDBName("superxc");
	}
	public void actionPerformed(ActionEvent e){
        if(e.getSource() == view.btnReg){
            boolean checkLab = true;
            if(!view.labPasswordCheck.getText().equals("O"))checkLab=false;
            if(!view.labUserNameCheck.getText().equals("O"))checkLab=false;
            if(!view.labRePasswordCheck.getText().equals("O"))checkLab=false;
            if(!view.labNickNameCheck.getText().equals("O"))checkLab=false;
            if(checkLab==false){
                JOptionPane.showMessageDialog(view, "The info have some error,"+
                        " move the cursor to 'X' to get help.", "error", JOptionPane.INFORMATION_MESSAGE);
            }else{
                String userName = view.txtUserName.getText();
                String nickName = view.txtNickName.getText();
                String password = new String(view.pwdFir.getPassword());
                String SQL = "select * from userInfo where userName='"+view.txtUserName.getText()+"'";
                //System.out.println(SQL);
                String rs[][] = jiayuDB.query(SQL);
                //System.out.println(rs.length);
                if(rs.length!=0){
                    JOptionPane.showMessageDialog(view,"The user '"+view.txtUserName.getText()+
                            "' have exist. Please use another one.","error",JOptionPane.INFORMATION_MESSAGE);
                    return ;
                }
                /*userInfo(uid int primary key, userName char(20),
                 *         userPasswd char(20), nickName char(20), phoneNumber char(20),
                 *         profile varchar(100), followCnt int, fansCnt int, microCnt int, headImage varchar(500));
                 */
                SQL = "select * from sysBlog where pName='userCnt'";
                rs = jiayuDB.query(SQL);
                int uid=0;
                if(rs.length==0){
                    JOptionPane.showMessageDialog(view,"db have some error", "error",JOptionPane.INFORMATION_MESSAGE);
                    return ;
                }
                uid = Integer.parseInt(rs[0][2])+1;
                SQL = "insert into userInfo values(rep,'rep','rep','rep','','',0,0,0,'')";
                SQL = SQL.replaceFirst("rep", String.valueOf(uid));
                SQL = SQL.replaceFirst("rep", userName);
                SQL = SQL.replaceFirst("rep", password);
                SQL = SQL.replaceFirst("rep", nickName);
                //System.out.println(SQL);
                jiayuDB.insert(SQL);
                SQL = "update sysBlog set lValue="+String.valueOf(uid)+" where pName='userCnt'";
                jiayuDB.update(SQL);
                JOptionPane.showMessageDialog(view, "success!", "success", JOptionPane.INFORMATION_MESSAGE);
                view.dispose();
            }
        }
	}
	public void focusGained(FocusEvent e){
			
	}
	public void focusLost(FocusEvent e){
		if(e.getSource()==view.txtUserName){
			view.txtUserName.setText(view.txtUserName.getText().trim());
			checkUserNameAndNickName(view.txtUserName,
									view.labUserNameCheck, "UserName");
		}else if(e.getSource()==view.txtNickName){
			view.txtNickName.setText(view.txtNickName.getText().trim());
			checkUserNameAndNickName(view.txtNickName,
									view.labNickNameCheck, "NickName");
		}else if(e.getSource()==view.pwdFir){
			String pwd = new String(view.pwdFir.getPassword());
			if(pwd.length()<6){
                view.labPasswordCheck.setState(XCCheckLabel.CLOSE);
				//view.labPasswordCheck.setForeground(Color.RED);
				//view.labPasswordCheck.setText("X");
				//view.labPasswordCheck.setToolTipText("The length of password can not less than 6bits.");
			}else{
                view.labPasswordCheck.setState(XCCheckLabel.OK);
				//view.labPasswordCheck.setForeground(Color.GREEN);
				//view.labPasswordCheck.setText("O");
				//view.labPasswordCheck.setToolTipText("");
			}
		}else if(e.getSource()==view.pwdSec){
			String pwd1 = new String(view.pwdFir.getPassword());	
			String pwd2 = new String(view.pwdSec.getPassword());
			if(pwd1.equals(pwd2)){
                view.labRePasswordCheck.setState(XCCheckLabel.OK);
			}else{
                view.labRePasswordCheck.setState(XCCheckLabel.CLOSE);
				//view.labRePasswordCheck.setToolTipText("The password must be same as before.");
			}
		}
	}
	private void checkUserNameAndNickName(JTextField txt, XCCheckLabel lab, String strWhich){
        //bug, nickName should be Chinese able
		String str = txt.getText();
		if(str.equals(""))return;
		boolean check=true;
		if(!(str.charAt(0)+"").matches("[a-zA-z]"))
			check=false;
		for(int i=1;i<str.length();i++){
			String tmp = str.charAt(i)+"";
			if(!tmp.matches("[a-zA-Z0-9]")){
				check=false;
				break;
			}
		}
		if(check){
            lab.setState(XCCheckLabel.OK);
		}else{
            lab.setState(XCCheckLabel.CLOSE);
		}
	}
}
