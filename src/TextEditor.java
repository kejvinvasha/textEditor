import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
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
        fileButton.setFocusable(false);
        DropDownButton editButton = new DropDownButton("Edit");
        editButton.setFocusable(false);

        //Create MenuItems for File Button

        //New Button
        JMenuItem newItem = new JMenuItem("New");
        fileButton.addMenuItem(newItem);

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
        //adding new file functionality
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if there is text in the file already
                if (!textArea.getText().isEmpty()) {
                    //check if the file is an already saved file or not
                    if (frameTitle.equals(frame.getTitle())) {
                        //create a JDialog to act as modal
                        JDialog modal = new JDialog(frame, "Unsaved Changes", true);
                        //create both save and don't save button and their functionalities
                        JButton saveModal = new JButton("Save Changes");
                        JButton dontSaveModal = new JButton("Don't Save");
                        saveModal.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //since file is not saved before do save as, close modal and reset frame and text
                                saveAsItem.doClick();
                                modal.setVisible(false);
                                modal.dispose();
                                textArea.setText("");
                                frame.setTitle(frameTitle);
                            }
                        });
                        dontSaveModal.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //close modal and reset text
                                modal.setVisible(false);
                                modal.dispose();
                                textArea.setText("");
                            }
                        });
                        //make panel and add buttons and label
                        JPanel modalPanel = new JPanel();
                        modalPanel.add(new JLabel("There are unsaved changes in this file, would you like to save?"));
                        modalPanel.add(dontSaveModal, BorderLayout.SOUTH);
                        modalPanel.add(saveModal, BorderLayout.SOUTH);
                        //add panel to modal dialog and set the needed preferences of the modal
                        modal.add(modalPanel);
                        modal.setSize(400, 200);
                        modal.setLocationRelativeTo(frame);
                        modal.setVisible(true);
                    } else {
                        //if a file is already opened save the file and reset text and frame
                        saveItem.doClick();
                        textArea.setText("");
                        frame.setTitle(frameTitle);
                    }
                } else {
                    //if text is empty, reset text and frame title
                    textArea.setText("");
                    frame.setTitle(frameTitle);
                }
            }
        });
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
            //adding txt file extension
            final FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text File", "txt");
            @Override
            public void actionPerformed(ActionEvent e) {
                //creating fileChooser
                JFileChooser saveChooser = new JFileChooser();
                //adding the extensions to the chooser and selecting txt as default
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
        //adding print button and functionality
        JMenuItem printItem = new JMenuItem("Print");
        fileButton.addMenuItem(printItem);
        printItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea.print();
                } catch (PrinterException err) {
                    System.out.println("Error occurred while printing the file.");
                }
            }
        });
        //adding the exit button and functionality
        JMenuItem exitItem = new JMenuItem("Exit");
        fileButton.addMenuItem(exitItem);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

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
