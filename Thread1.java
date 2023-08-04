package emotionalsongs;

import javax.swing.*;

public class Thread1 extends Thread{
    JListUtility list;

    public Thread1(JListUtility lista) {
        list=lista;
    }
    public void run() {
        do{
            GUI.playlistVisualizzazione=list.nomePlaylistSelezionata();
        }while(1==1);
    }
}