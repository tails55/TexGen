package ru.omsu.imit.tails55.texgen.frontend;

import ru.omsu.imit.tails55.texgen.backend.io.IOClass;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.Arrays;

public class Main extends JFrame {
    private JPanel contentPane;
    private JButton executeButton;
    private JTextField xmlPathArea;
    private JTextField templatePathArea;
    private JTextField answerTemplatePathArea;
    private JButton openButton;
    private JButton openButton2;
    private JButton openButton3;
    private JSpinner variantSpinner;
    private JButton exportPathButton;
    private JTextField exportPath;

    private void onOpenButton() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String f2s = f.toString();
                return f2s.substring(f2s.length() - 4).compareTo(".xml") == 0;
            }

            @Override
            public String getDescription() {
                return "Файлы .xml";
            }
        });
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            xmlPathArea.setText(fileChooser.getSelectedFile().toString());
            exportPath.setText(fileChooser.getSelectedFile().getParent() + FileSystems.getDefault().getSeparator() + "output" + FileSystems.getDefault().getSeparator());
        }
    }

    private void onOpenButton2() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String f2s = f.toString();
                return f2s.substring(f2s.length() - 4).compareTo(".tex") == 0;
            }

            @Override
            public String getDescription() {
                return "Файлы .tex";
            }
        });
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            templatePathArea.setText(fileChooser.getSelectedFile().toString());
        }
    }

    private void onOpenButton3() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String f2s = f.toString();
                return f2s.substring(f2s.length() - 4).compareTo(".tex") == 0;
            }

            @Override
            public String getDescription() {
                return "Файлы .tex";
            }
        });
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            answerTemplatePathArea.setText(fileChooser.getSelectedFile().toString());
        }
    }

    private void onExportPathButton() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Путь экспорта";
            }
        });
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            exportPath.setText(fileChooser.getSelectedFile().toString());
        }
    }

    private void onExecuteButton() {

        File xml = new File(xmlPathArea.getText());
        File exportDir= new File(exportPath.getText());
        String fileName=xml.getName().split("\\.")[0];

        try {
            for(int i=1;i<=(Integer) variantSpinner.getValue();i++)
                IOClass.main(new String[]{xmlPathArea.getText(),
                        templatePathArea.getText(),
                        answerTemplatePathArea.getText(),
                        exportDir.toString()+FileSystems.getDefault().getSeparator()+fileName+"_variant_"+i+".tex",
                        exportDir.toString()+FileSystems.getDefault().getSeparator()+fileName+"_variant_"+i+"_answers.tex",
                        Integer.toString(i)});
            JOptionPane.showMessageDialog(this, "Билеты сгенерированы успешно!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Произошла ошибка! Информация об ошибке: \n"+ Arrays.toString(e.getStackTrace()));
        }
    }

    public Main() {
        setContentPane(contentPane);

        setMinimumSize(new Dimension(640, 280));
        setTitle("Генератор билетов по ДММЛ");

        getRootPane().setDefaultButton(executeButton);

        variantSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        variantSpinner.setEditor(new JSpinner.NumberEditor(variantSpinner));

        openButton.addActionListener(e -> onOpenButton());
        openButton2.addActionListener(e -> onOpenButton2());
        openButton3.addActionListener(e -> onOpenButton3());
        exportPathButton.addActionListener(e -> onExportPathButton());
        executeButton.addActionListener(e -> onExecuteButton());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        if(args.length>=5) {
            IOClass.main(args);
            return;
        }
        Main window = new Main();
        window.setVisible(true);
    }


}

