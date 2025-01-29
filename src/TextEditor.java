import java.awt.*;
import java.io.*;
import javax.swing.*;

public class TextEditor {
    public static void main(String[] args) {
        //create frame component
        JFrame frame = new JFrame("Text Editor");

        //create toolbar component
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);

        //create the toolbar panel component
        JPanel tbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //create the text area component
        JTextArea textArea = new JTextArea();

        //Create Dropdown for File Button
        String[] fileOptions = {"Open", "Save", "Save As", "Print", "Exit"};
        JComboBox<String> fileDropDown = new JComboBox<>(fileOptions);


        //Create Taskbar buttons
        //JButton fileButton = new JButton("File");
        JButton editButton = new JButton("Edit");

        tbPanel.add(fileDropDown);
        tbPanel.add(editButton);
        tb.add(tbPanel);
        frame.add(tb, BorderLayout.NORTH);
        frame.add(textArea, BorderLayout.CENTER);

        //change look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Unable to set look and feel");
        }

        // 400 width and 500 height
        frame.setSize(500, 600);

        // making the frame visible
        frame.setVisible(true);
    }
}
