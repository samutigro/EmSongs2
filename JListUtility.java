package EmotionalSongs;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.io.Serializable;

public class JListUtility extends JPanel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private  JList<String> list;
    private  DefaultListModel<String> listModel;
    private String playlistSelezionata = "";

    public JListUtility() {
        //Creazione della lista e del modello di lista
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        //Aggiunta della lista in uno JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        JTextField text = new JTextField(25);
        add(scrollPane);
        setLayout(new FlowLayout());
        add(text);

        setBackground(new Color(32, 33, 35));
        setForeground(new Color(255, 255, 255));
        setSize(400,600);
        setVisible(true);

        //Aggiunta del listener per gestire il click sulla lista
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                //Ottiene l'elemento selezionato
                JList list = (JList)evt.getSource();
                String selectedValue = (String)list.getSelectedValue();
                playlistSelezionata = selectedValue;
                text.setText(selectedValue);
            }
        });
    }

    //Metodo per aggiungere una stringa alla lista
    public void addStringToList(String str) {
        listModel.addElement(str);
    }

    //Metodo che ritorna il nome della playlist selezionata
    public String nomePlaylistSelezionata(){
        if(playlistSelezionata.equals("")){
            return "";
        }else{
            return playlistSelezionata;
        }
    }

}
