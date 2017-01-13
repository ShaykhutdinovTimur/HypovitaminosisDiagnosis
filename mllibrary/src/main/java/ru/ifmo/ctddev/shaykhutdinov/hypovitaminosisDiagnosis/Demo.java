package ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis;

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

public class Demo extends JPanel implements ActionListener {
    static private final String newline = "\n";
    JButton openButton;
    JTextArea log;
    JFileChooser fc;
    private RegressorInvocator reg;

    public Demo() {
        super(new BorderLayout());
        log = new JTextArea(5, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser();

        openButton = new JButton("Open a File...");
        fc.setCurrentDirectory(
                new File("/home/lightning95/studies/software-design/cp-hp-diag/HypovitaminosisDiagnosis/server/src/main/resources/photos"));

        openButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);

        reg = new RegressorInvocator();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new Demo());
        Dimension dim = new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 5, Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.setSize(dim);

        frame.pack();
        frame.setVisible(true);
    }

    private static void showImage(File file) {
        JFrame frame = new JFrame("Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();


        JLabel label = new JLabel(new ImageIcon(file.getAbsolutePath()));
        frame.add(label);

        frame.setLocation(dim.width / 2 - label.getIcon().getIconWidth() / 2, dim.height / 2 - label.getIcon().getIconHeight() / 2);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
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
                    showImage(file);
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
        }
    }
}