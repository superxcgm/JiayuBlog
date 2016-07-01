import java.awt.*;
import javax.swing.*;
public class LabelPanel extends JPanel{
    // shortcur for adding lab to JPanel;

    public LabelPanel(JLabel lab){
       setLayout(new BorderLayout());
       lab.setHorizontalAlignment(JLabel.CENTER);
       add(lab);
    }
    public LabelPanel(JLabel lab, int state){
       setLayout(new BorderLayout());
       lab.setHorizontalAlignment(JLabel.RIGHT);
       add(lab, state);
    }
    public LabelPanel(JLabel lab,int state, int param){
        add(lab);
    }
}
