import javax.swing.*;
import java.awt.event.*;

public class DropDownButton extends JButton{
    private JPopupMenu dropDownMenu;

    public DropDownButton(String title) {
        super(title);
        createDropDownMenu();
    }

    private void createDropDownMenu() {
        dropDownMenu = new JPopupMenu();
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropDownMenu.show(DropDownButton.this, 0, getHeight());
            }
        });
    }

    public void addMenuItem(JMenuItem item) {
        dropDownMenu.add(item);
    }
}