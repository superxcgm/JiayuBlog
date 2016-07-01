import java.awt.*;
import javax.swing.*;
public class XCCheckLabel extends JLabel{
    public static final int OK=1;
    public static final int CLOSE=2;
    private Icon okIcon = new ImageIcon("img/ok_16px.png");
    private Icon closeIcon = new ImageIcon("img/close_16px.png");
    String hintMsg;
    public XCCheckLabel(String text, String hintMsg){
        super(text);
        this.hintMsg = hintMsg;
    }
    public void setState(int state){
        if(state==OK){
            setText("O");
            setToolTipText("");
            setIcon(okIcon);
        }else{
            setText("X");
            setToolTipText(hintMsg);
            setIcon(closeIcon);
        }
    }
}
