package emotionalsongs;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//bvssra02c69l682d

public class controlloCF {

    public static Boolean controlloCarattere(String s){

        String carattere_controllo;
        String[] caratteri={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        int[] valori_pari={0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
        int[] valori_dispari={1,0,5,7,9,13,15,17,19,21,1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
        String[] caratteri_controllo={"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        int[] valori_pari_controllo={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
        int somma=0;
        for (int i=0;i<15;i++) {
            if (i % 2 != 0) {
                for (int y = 0; y < caratteri.length; y++) {
                    if (caratteri[y].equals(String.valueOf(s.charAt(i)))) {
                        somma += valori_pari[y];
                        System.out.println(somma);
                    }
                }
            } else {
                for (int y = 0; y < caratteri.length; y++) {
                    if (caratteri[y].equals(String.valueOf(s.charAt(i)))) {
                        somma += valori_dispari[y];
                        System.out.println(somma);
                    }
                }
            }
        }
        somma=somma%26;
        System.out.println(somma);
        carattere_controllo=caratteri_controllo[valori_pari_controllo[somma]];
        System.out.println(carattere_controllo);

        if(carattere_controllo.equals(String.valueOf(s.charAt(15))))
            return true;
        else{
            return false;
        }

    }

    public static boolean isConsonant ( char ch){
        ch = Character.toLowerCase(ch);
        return (!(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') && ch >= 'a' && ch <= 'z');
    }
    //metodo per controllare che una stringa cotenga / non contenga caratteri
    public static Boolean StringContainsLetter(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] MESI ={"a","b","c","d","e","h","l","m","p","r","s","t"};
        int i;
        System.out.println("sei un maschio o una femmina");
        String sesso=in.nextLine();
        while(!(sesso.equals("maschio")) && !(sesso.equals("femmina"))){
            System.out.println("sei un maschio o una femmina");
            sesso=in.nextLine();
        }


        System.out.println("inserisci il tuo CF");
        String cf = in.nextLine();
        while(cf.length()!=16){
            System.out.println("Deve essere lungo almeno 15 caratteri");
            cf = in.nextLine();
        }
        cf=cf.toLowerCase();

        System.out.println("inserisci il tuo cognome");
        String cognome = in.nextLine();

        //controlliamo che il cognome inserito sia corretto
        String lettere_in_cognome;
        if (cognome.length()<=2){
            lettere_in_cognome= cognome + "x";
        }
        else{
            String consonanti="";
            String vocali="";
            for(i=0; i< cognome.length();i++)
                if( isConsonant(cognome.charAt(i))){
                    consonanti= consonanti.concat(String.valueOf(cognome.charAt(i)));
                }else{
                    vocali= vocali.concat(String.valueOf(cognome.charAt(i)));
                }
            if(consonanti.length()>=3){
                lettere_in_cognome=consonanti.substring(0,3);
            }
            else{
                lettere_in_cognome=consonanti + vocali.substring(0,(3-(consonanti.length())));
            }
        }


        lettere_in_cognome=lettere_in_cognome.toLowerCase();
        System.out.println(lettere_in_cognome);

        System.out.println("inserisci il tuo nome");

        String nome = in.nextLine();  //controlliamo che il nome inserito sia corretto
        String lettere_in_nome;
        if (cognome.length()<=2){
            lettere_in_nome= nome + "x";
        }
        else{
            String consonanti="";
            String vocali="";
            for(i=0; i< nome.length();i++)
                if( isConsonant(nome.charAt(i))){
                    consonanti=consonanti.concat(String.valueOf(nome.charAt(i)));
                }else{
                    vocali= vocali.concat(String.valueOf(nome.charAt(i)));
                }
            if(consonanti.length()>3){
                lettere_in_nome=consonanti.charAt(0)+consonanti.substring(2,4);
            }
            else{
                lettere_in_nome=consonanti + vocali.substring(0,(3-(consonanti.length())));
            }
        }


        lettere_in_nome=lettere_in_nome.toLowerCase();
        System.out.println(lettere_in_nome);


        System.out.println("inserisci la tua data di nascita nel formato GGMMAAAA(DDMMYYYY)");
        String data_di_nascita = in.nextLine();
        while(data_di_nascita.length()!=8 || StringContainsLetter(data_di_nascita)){
            System.out.println("hai inserito qualcosa che non andava bene");
            System.out.println("inserisci la tua data di nascita nel formato GGMMAAAA(DDMMYYYY)");
            data_di_nascita = in.nextLine();
        }
        data_di_nascita=data_di_nascita.toLowerCase();
        System.out.println(data_di_nascita);
        //estrae il giorno dalla data
        String giorno = data_di_nascita.substring(0,2);
        if(sesso.equals("femmina")){
            int gg = Integer. parseInt(giorno) + 40 ;
            giorno= String.valueOf(gg);

        }
        //estrae il mese dalla data
        String mese = data_di_nascita.substring(2,4);
        //trasformo la string in int per usarlo nell'array come indice
        String mese_per_confronto = MESI[(Integer. parseInt(mese))-1];
        String Anno = data_di_nascita.substring(6);
        System.out.println(Anno);

        // testiamo tutti i controlli per poter dire che il codice vada bene
        if(cf.substring(0,3).equals(lettere_in_cognome) && cf.substring(3,6).equals(lettere_in_nome) && cf.substring(6,8).equals(Anno) && cf.substring(8,9).equals(mese_per_confronto) && cf.substring(9,11).equals(giorno)&&controlloCarattere(cf))
            System.out.println("il codice fiscale è corretto");

    }
}