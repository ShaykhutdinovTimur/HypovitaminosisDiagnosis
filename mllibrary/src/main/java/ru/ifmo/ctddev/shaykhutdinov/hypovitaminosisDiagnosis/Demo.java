package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

import ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.baseProvider.ImageProcessor;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/*
 * FileChooserDemo.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class Demo extends JPanel
        implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;

    public Demo() {
        super(new BorderLayout());
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser();

        openButton = new JButton("Open a File...",
                createImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);
        saveButton = new JButton("Save a File...",
                createImageIcon("images/Save16.gif"));
        saveButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);

        reg = new RegressorInvocator();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(Demo.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.append("Opening: " + file.getName() + "" + newline);
                try {
                    Instance ins = new DenseInstance(6);
                    BufferedImage bu = ImageIO.read(file);
                    double[] hihi = ImageProcessor.processMono(bu);
                    ArrayList<Attribute> atts = new ArrayList<>();
                    atts.add(reg.i.attribute(0));
                    for (int i = 1; i < 6; i++) {
                        ins.setValue(reg.i.attribute(i), hihi[i - 1]);
                        atts.add(reg.i.attribute(i));
                    }
                    Instances dataUnlabeled = new Instances("TestInstances", atts, 0);
                    dataUnlabeled.add(ins);
                    dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1);
                    double classif = reg.regression.classifyInstance(dataUnlabeled.firstInstance());
                    log.append(classif + "\n");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == saveButton) {
            int returnVal = fc.showSaveDialog(Demo.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.append("Saving: " + file.getName() + "" + newline);
            } else {
                log.append("Save command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }


    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Demo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Demo());
        frame.pack();
        frame.setVisible(true);
    }

    private RegressorInvocator reg;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}