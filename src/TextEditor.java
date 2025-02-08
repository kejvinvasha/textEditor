import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor {
    private File file;

    public TextEditor() {
        String frameTitle = "Text Editor";

        //create frame component
        JFrame frame = new JFrame(frameTitle);

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
                    file = chooser.getSelectedFile();
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

        //adding the save and saveAs button
        JMenuItem saveItem = new JMenuItem("Save");
        fileButton.addMenuItem(saveItem);
        JMenuItem saveAsItem = new JMenuItem("Save As");
        fileButton.addMenuItem(saveAsItem);
        //adding save functionality
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if the file is not already open, use the save as functionality
                if (frameTitle.equals(frame.getTitle())) {
                    saveAsItem.doClick();
                }else {
                    //use a fileWriter to write the new changes
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(textArea.getText());
                    } catch (IOException err) {
                        System.out.println("An error occurred while saving the file.");
                    }
                }
            }
        });
        //adding save as functionality
        saveAsItem.addActionListener(new ActionListener() {
            //adding three file extensions
            final FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text File", "txt");
            final FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF File", "pdf");
            final FileNameExtensionFilter docxFilter = new FileNameExtensionFilter("Word Document File", "docx");
            @Override
            public void actionPerformed(ActionEvent e) {
                //creating fileChooser
                JFileChooser saveChooser = new JFileChooser();
                //adding the extensions to the chooser and selecting txt as default
                saveChooser.addChoosableFileFilter(txtFilter);
                saveChooser.addChoosableFileFilter(pdfFilter);
                saveChooser.addChoosableFileFilter(docxFilter);
                saveChooser.setFileFilter(txtFilter);
                int result = saveChooser.showSaveDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    //getting the extension and fileName of the new file
                    String extension = ((FileNameExtensionFilter)saveChooser.getFileFilter()).getExtensions()[0];
                    String fileName = saveChooser.getSelectedFile().getAbsolutePath() + "." + extension;
                    //creating new file and setting the new window title
                    file = new File(fileName);
                    String title = file.getName().split("\\.")[0];
                    frame.setTitle("Text Editor - " + title);
                    //create the file using FileWriter
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(textArea.getText());
                    } catch (IOException err) {
                        System.out.println("An error occurred while saving the file.");
                    }
                }
            }
        });
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
