import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

        //Create DropDownButtons
        DropDownButton fileButton = new DropDownButton("File");
        DropDownButton editButton = new DropDownButton("Edit");

        //Create MenuItems for File Button

        //Open Button and functionality
        JMenuItem openItem = new JMenuItem("Open");
        fileButton.addMenuItem(openItem);
        //Open Event Listener
        openItem.addActionListener(new ActionListener() {
            //When button is clicked perform action
            @Override
            public void actionPerformed(ActionEvent e) {
                //choose file and record if it was successful
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(frame);

                //if successful grab the content of the file and copy into textArea
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    String title = file.getName().split("\\.")[0];
                    frame.setTitle("Text Editor - " + title);
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        textArea.setText(content);
                    } catch (IOException err) {
                        JOptionPane.showMessageDialog(frame, "Error reading file");
                    }
                }
            }
        });

        JMenuItem saveItem = new JMenuItem("Save");
        fileButton.addMenuItem(saveItem);
        JMenuItem printItem = new JMenuItem("Print");
        fileButton.addMenuItem(printItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        fileButton.addMenuItem(exitItem);

        //Create MenuItems for Edit Button
        JMenuItem cutItem = new JMenuItem("Cut");
        editButton.addMenuItem(cutItem);
        JMenuItem copyItem = new JMenuItem("Copy");
        editButton.addMenuItem(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        editButton.addMenuItem(pasteItem);
        JMenuItem deleteItem = new JMenuItem("Delete");
        editButton.addMenuItem(deleteItem);


        //add buttons to panel
        tbPanel.add(fileButton);
        tbPanel.add(editButton);
        //add panel to toolbar
        tb.add(tbPanel);
        //add toolbar and textArea to frame
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
