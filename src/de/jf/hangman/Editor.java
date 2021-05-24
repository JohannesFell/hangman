package de.jf.hangman;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class Editor extends JFrame {

    private static final long serialVersionUID = 4693515612480492549L;

    private JTextArea feld;
    private JButton laden, beenden, hinzufügen;
    private JLabel TextAnzahl, LabelWörterAnzahl;
    private int anzahlWörter = 0;
    private String dateiName;


    class MeinListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("laden")) {
                dateiLesen();
            }
            if(e.getActionCommand().equals("hinzufügen")) {
                dateiSchreiben();
            }
            if(e.getActionCommand().equals("ende")) {
                System.exit(0);
            }

        }
    }

    //Der Konstruktor
    public Editor (String titel) {
        super(titel);
        dateiName = "Wortliste.txt";
        JPanel tempPanel;
        feld = new JTextArea();

        laden = new JButton ("Laden");
        laden.setActionCommand("laden");
        hinzufügen = new JButton ("Hinzufügen");
        hinzufügen.setActionCommand("hinzufügen");
        beenden = new JButton ("Beenden");
        beenden.setActionCommand("ende");
        //neues Label für Anzeige der Wörteranzahl
        TextAnzahl = new JLabel("Anzahl Wörter: ");
        LabelWörterAnzahl = new JLabel("");


        MeinListener listener = new MeinListener();
        laden.addActionListener(listener);
        hinzufügen.addActionListener(listener);
        beenden.addActionListener(listener);

        //ein BorderLayout anwenden
        setLayout(new BorderLayout());
        //das Eingabefeld mit Scrollbars
        add(new JScrollPane(feld), BorderLayout.CENTER);

        //TextArea nicht editierbar machen
        //Neue Wörter werden über JOptionPane hinzugefügt
        feld.setEditable(false);


        //ein Panel für die Schaltflächen
        tempPanel = new JPanel();
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(laden);
        tempPanel.add(hinzufügen);
        tempPanel.add(beenden);
        tempPanel.add(TextAnzahl);
        tempPanel.add(LabelWörterAnzahl);
        add(tempPanel,BorderLayout.SOUTH);

        //Größe setzen, Position bestimmen, Standard-Verhalten festlegen und anzeigen
        setSize(new Dimension(500,300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    //Methode zum Lesen der Datei
    private void dateiLesen() {
        //BufferedReader initialisieren
        BufferedReader br = null;
        //String für einzelne Zeilen in Datei
        String line;
        //löschen des Tetxfeldes
        //keine dopplete Anzeige bei mehrmaligen laden
        feld.setText("");

        try {
            //Datei öffnen
            br = new BufferedReader(new FileReader(dateiName));
            //Falls Datei nicht vorhanden ist Fehler-Dialog anzeigen
            if(br.ready() == false) {
                JOptionPane.showMessageDialog(this, "Die Datei ist leer. Bitte fügen Sie ein Wort hinzu.");

            }
            else {
                //Wenn Zeilen in Datei beschrieben sind,
                //einzelne Zeilen lesen
                //Anzahl der Wörter um 1 erhöhen
                //einzelne Zeilen ausgeben
                while ((line = br.readLine()) != null) {
                    anzahlWörter++;
                    feld.append(line + "\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Problem beim Lesen der Datei aufgetreten");
        }
        //Text in Labelfür Wörteranzahl setzen
        LabelWörterAnzahl.setText(Integer.toString(anzahlWörter));

        //Zurücksetzen, da sonst Doppelte Zählung
        anzahlWörter = 0;

    }

    //Methode zum Schreiben in eine Datei
    private void dateiSchreiben() {
        //Neues Wort wird in einem eigenen Dialog eingegeben
        String neuesWort = JOptionPane.showInputDialog(null, "Neues Wort hinzufügen");
        try {
            //Neuen FileWriter erzeugen
            //Parameter true sorgt dafür, dass Datei nicht überschrieben wird
            //Der neue Text wird angehängt
            Writer out = new FileWriter(dateiName,true);
            //Datei öffnen
            BufferedWriter bw = new BufferedWriter(out);
            //String aus Dialog in Datei schreiben
            bw.write(neuesWort + "\n");
            //Datei schließen
            bw.close();
            //Datei gleich aktualsieren
            dateiLesen();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Problem beim Schreiben der Datei aufgetreten");
        }
    }
}
