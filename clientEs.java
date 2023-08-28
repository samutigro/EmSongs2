package EmotionalSongs;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

import Database.InterfacciaDatabase;
import Database.Query;

import static EmotionalSongs.Brani.*;
import static EmotionalSongs.Playlist.rimuoviDuplicati;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe relativa all'interfaccia grafica; implementa l'interfaccia ActionListener, utilizzata per gestire gli eventi correlati alle azioni dell'utente su un componente dell'interfaccia grafica utente
 * @author De Vito Francesco
 * @author Auteri Samuele
 */
public class clientEs implements ActionListener {
    Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8999);
    InterfacciaDatabase databaseInterface = (InterfacciaDatabase)registry.lookup("SERVER");

    private int contatoreCanzoniSelezionate;

    private final JFrame frame;

    private JPanel buttonPanel, playlistPanelB, mainPanel, registrationPanel, searchPanel, loginPanel, playlistPanel, createPlaylistPanel, tablePanel;

    private JButton emotionButton, confermaRating, searchAutAnnoAddPlaylist, confermaAggiuntaPlaylist, bottoneLista, bottoneLista2, searchTitoloRating, avanti, confermaPlaylist, searchTitoloAddPlaylist, searchTitoloPlaylist, searchAutAnnoPlaylist, logoutButton, viewPlaylistButton, registrationButton, loginButton, searchPlaylistButton, searchButton, registrationSaveButton, login, searchTitolo, playlistButton, ratingButton, searchAutAnno, creaplaylistButton, addsongButton, indietro;

    private JLabel nameLabel, surnameLabel, usernameLabel, emailLabel, dateLabel, codiceFiscale, passwordLabel, yearLabel, authorLabel, titleLabel, namePLaylistLabel, userLable, pwdLable, loginLabel, searchLabel;

    private JTextField  textRating, contatore, usernameField, emailField, dateField, CFfield, yearfield, Authorfield, Titlefield, namePlaylistfield, nameTextField, surnameTextField, userField;

    private JPasswordField passwordField, pwdField;

    private JTable table;

    private Utente utenteLoggato;

    private Playlist playlistTransizione;

    private ArrayList<String> canzoniPlaylistGlobale;

    public static JButton avantiRating;

    public static String playlistVisualizzazione, canzoneDaValutare, emozioneRating;

    private JButton searchTitoloEmozioni;

    private JButton confermaEmozioni;

    private String branoEmozione;
    private JButton bottoneConferma;
    private JListUtility lista;

    /**
     * Costruttore della classe EmotionalSongs.GUI che implementa l'interfaccia grafica
     * @throws SQLException Rappresenta un'eccezione generata dall'API JDBC; quando si utilizza un database tramite JDBC, possono verificarsi diversi potenziali errori come ad esempio errori di connessione al database, errori nelle istruzioni SQL o problemi con il database stesso. Quando si verifica uno di questi errori, l'API JDBC genera un SQLException per indicare che si è verificato un errore.
     */
    public clientEs() throws SQLException, RemoteException, NotBoundException {
        try{
            //Permette il Cross Platform permettendo quindi alla EmotionalSongs.GUI di funzionare su diversi sistemi operativi come ad esempio macOS e Windows
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }

        //Frame principale
        frame = new JFrame("Emotional Song");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(new Point(200, 100));

        titleLabel = new JLabel("Emotional Song", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);

        //Creazione del main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(32, 33, 35));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(32, 33, 35));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10); //Aggiunge margine tra i pulsanti

        //Creazione registration button
        registrationButton = new JButton("Registrazione");
        registrationButton.addActionListener(this);
        registrationButton.setForeground(new Color(255, 255, 255));
        registrationButton.setBackground(new Color(70, 80, 120));

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        buttonPanel.add(registrationButton, c);

        //Creazione login button
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(new Color(70, 80, 120));

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(loginButton, c);

        //Creazione search button
        searchButton = new JButton("Ricerca");
        searchButton.addActionListener(this);
        searchButton.setForeground(new Color(255, 255, 255));
        searchButton.setBackground(new Color(70, 80, 120));

        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        buttonPanel.add(searchButton, c);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        //Immissione del main panel nel frame
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setBounds(250, 90, 800, 500);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Metodo per la gestione degli eventi relativi ai bottoni
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        //Bottone per l'apertura del pannello di registrazione
        if (e.getSource() == registrationButton) {
            frame.setTitle("Registrazione");

            //Creazione registration panel
            registrationPanel = new JPanel();
            registrationPanel.setLayout(new GridBagLayout());
            JLabel titlereg = new JLabel("Registrazione");
            titlereg.setForeground(new Color(255, 255, 255));
            titlereg.setFont(new Font("Arial", Font.PLAIN, 30));
            titlereg.setSize(300, 30);
            titlereg.setLocation(300, 30);
            registrationPanel.setName("Registrazione");

            nameLabel = new JLabel("Nome:");

            surnameLabel = new JLabel("Cognome:");

            codiceFiscale = new JLabel("Codice Fiscale:");

            dateLabel = new JLabel("Data nascita (DD/MM//YYYY):");

            emailLabel = new JLabel("Email:");

            usernameLabel = new JLabel("Username:");

            passwordLabel = new JLabel("Password:");

            nameTextField = new JTextField(20);
            nameTextField.setCaretColor(Color.WHITE);

            surnameTextField = new JTextField(20);
            surnameTextField.setCaretColor(Color.WHITE);

            usernameField = new JTextField(20);
            usernameField.setCaretColor(Color.WHITE);

            emailField = new JTextField(20);
            emailField.setCaretColor(Color.WHITE);

            dateField = new JTextField(20);
            dateField.setCaretColor(Color.WHITE);

            CFfield = new JTextField(20);
            CFfield.setCaretColor(Color.WHITE);

            passwordField = new JPasswordField(20);
            passwordField.setCaretColor(Color.WHITE);

            GridBagConstraints constr = new GridBagConstraints();
            constr.insets = new Insets(5, 5, 5, 5);
            constr.anchor = GridBagConstraints.WEST;

            passwordField.setBackground(new Color(32, 33, 35));
            passwordField.setForeground(new Color(255, 255, 255));

            registrationPanel.setBackground(new Color(32, 33, 35));
            registrationPanel.setForeground(new Color(255, 255, 255));

            CFfield.setBackground(new Color(32, 33, 35));
            CFfield.setForeground(new Color(255, 255, 255));

            dateField.setBackground(new Color(32, 33, 35));
            dateField.setForeground(new Color(255, 255, 255));

            usernameField.setBackground(new Color(32, 33, 35));
            usernameField.setForeground(new Color(255, 255, 255));

            emailField.setBackground(new Color(32, 33, 35));
            emailField.setForeground(new Color(255, 255, 255));

            nameTextField.setBackground(new Color(32, 33, 35));
            nameTextField.setForeground(new Color(255, 255, 255));

            surnameTextField.setBackground(new Color(32, 33, 35));
            surnameTextField.setForeground(new Color(255, 255, 255));

            nameLabel.setForeground(new Color(255, 255, 255));

            dateLabel.setForeground(new Color(255, 255, 255));

            emailLabel.setForeground(new Color(255, 255, 255));

            surnameLabel.setForeground(new Color(255, 255, 255));

            codiceFiscale.setForeground(new Color(255, 255, 255));

            passwordLabel.setForeground(new Color(255, 255, 255));

            usernameLabel.setForeground(new Color(255, 255, 255));

            registrationSaveButton = new JButton("Salva");
            registrationSaveButton.addActionListener(this);
            registrationSaveButton.setBackground(new Color(70, 80, 120));
            registrationSaveButton.setForeground(new Color(255, 255, 255));

            registrationPanel.add(titlereg, constr);
            constr.gridx=0;
            constr.gridy=1;

            registrationPanel.add(nameLabel, constr);
            constr.gridx=1;

            registrationPanel.add(nameTextField, constr);
            constr.gridx=0;
            constr.gridy=2;

            registrationPanel.add(surnameLabel, constr);
            constr.gridx=1;

            registrationPanel.add(surnameTextField, constr);
            constr.gridx=0;
            constr.gridy=3;

            registrationPanel.add(codiceFiscale, constr);
            constr.gridx=1;

            registrationPanel.add(CFfield, constr);
            constr.gridx=0;
            constr.gridy=4;

            registrationPanel.add(dateLabel,constr);
            constr.gridx=1;

            registrationPanel.add(dateField, constr);
            constr.gridx=0;
            constr.gridy=5;

            registrationPanel.add(emailLabel, constr);
            constr.gridx=1;

            registrationPanel.add(emailField, constr);
            constr.gridx=0;
            constr.gridy=6;

            registrationPanel.add(usernameLabel, constr);
            constr.gridx=1;

            registrationPanel.add(usernameField, constr);
            constr.gridx=0;
            constr.gridy=7;

            registrationPanel.add(passwordLabel, constr);
            constr.gridx=1;

            registrationPanel.add(passwordField, constr);
            constr.gridx=0;
            constr.gridy=8;

            indietro = new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            registrationPanel.add(indietro, constr);
            constr.gridx=1;

            registrationPanel.add(registrationSaveButton, constr);
            constr.gridx=1;

            frame.getContentPane().removeAll();
            frame.add(registrationPanel);
            frame.revalidate();
            frame.repaint();

            //Bottone per la conferma della registrazione dopo aver effettuato l'inserimento dei dati
        } else if (e.getSource() == registrationSaveButton) {
            //Salvataggio dei dati di registrazione nel database
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String codiceFiscale = CFfield.getText();
            String date = convertDateFormat(dateField.getText());
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            Utente utente = new Utente(name, surname, codiceFiscale, date, email, username, password);
            boolean flag = false;

            try{
                databaseInterface.Registrazione(utente);
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(frame, "ERRORE!");
                flag = true;
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            if(!flag){
                JOptionPane.showMessageDialog(frame, "Registrazione fatta!");
                frame.getContentPane().removeAll();
                frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
            //Bottone per l'apertura del pannello di login
        } else if (e.getSource() == loginButton) {
            loginPanel = new JPanel(new GridBagLayout());
            loginPanel.setBackground(new Color(32, 33, 35));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            loginLabel = new JLabel("Login");
            loginLabel.setForeground(Color.WHITE);
            loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            loginPanel.add(loginLabel, gbc);

            userLable = new JLabel("Username:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            userLable.setForeground(Color.WHITE);
            userLable.setBackground(new Color(32, 33, 35));
            loginPanel.add(userLable, gbc);

            pwdLable = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            pwdLable.setBackground(new Color(32, 33, 35));
            pwdLable.setForeground(Color.WHITE);
            loginPanel.add(pwdLable, gbc);

            userField = new JTextField(20);
            gbc.gridx = 1;
            gbc.gridy = 1;
            userField.setForeground(Color.WHITE);
            userField.setBackground(new Color(32, 33, 35));
            loginPanel.add(userField, gbc);

            pwdField = new JPasswordField(20);
            gbc.gridx = 1;
            gbc.gridy = 2;
            pwdField.setBackground(new Color(32, 33, 35));
            pwdField.setForeground(Color.WHITE);
            loginPanel.add(pwdField, gbc);

            indietro = new JButton("Indietro");
            gbc.gridx = 0;
            gbc.gridy = 3;
            indietro.setForeground(Color.WHITE);
            indietro.setBackground(new Color(70, 80, 120));
            loginPanel.add(indietro, gbc);

            login = new JButton("Accedi");
            gbc.gridx = 1;
            gbc.gridy = 3;
            login.setBackground(new Color(70, 80, 120));
            login.setForeground(Color.WHITE);
            loginPanel.add(login, gbc);

            login.addActionListener(this);

            indietro.addActionListener(this);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(loginPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            //Bottone per la conferma del login dopo aver inserito username e password
        }else if(e.getSource() == login){
            boolean flag;
            String user = userField.getText();
            String pwd = String.valueOf(pwdField.getPassword());
            String q = "Select * from utenti where username = '"+ user  + "'";
            Query query = new Query(q);

            try {
                Utente u = databaseInterface.QueryLogin(query);
                this.utenteLoggato = u;
                flag = databaseInterface.Login(user, pwd);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            if(flag == true){
                String username = userField.getText();
                JOptionPane.showMessageDialog(frame, "Benvenuto " + username + "!");

                buttonPanel.remove(registrationButton);
                buttonPanel.remove(loginButton);
                buttonPanel.remove(searchButton);

                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(10, 10, 10, 10); //Aggiunge margine tra i pulsanti

                playlistButton = new JButton("Gestisci playlist");
                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.LINE_START;

                playlistButton.setBackground(new Color(70, 80, 120));
                playlistButton.setForeground(Color.WHITE);
                playlistButton.addActionListener(this);
                buttonPanel.add(playlistButton, c);

                //Creazione di textfield e label per inserire le credenziali
                ratingButton = new JButton("Valuta Canzoni");
                ratingButton.addActionListener(this);
                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.CENTER;

                ratingButton.setBackground(new Color(70, 80, 120));
                ratingButton.setForeground(Color.WHITE);
                buttonPanel.add(ratingButton, c);

                emotionButton = new JButton("Visualizza voti emozioni brani");
                emotionButton.addActionListener(this);
                c.gridx = 2;
                c.gridy = 0;
                c.anchor = GridBagConstraints.CENTER;

                emotionButton.setBackground(new Color(70, 80, 120));
                emotionButton.setForeground(Color.WHITE);
                buttonPanel.add(emotionButton, c);



                logoutButton = new JButton("Logout");

                c.gridx = 3;
                c.gridy = 0;
                c.anchor = GridBagConstraints.LINE_END;
                logoutButton.setBackground(new Color(70, 80, 120));
                logoutButton.setForeground(Color.WHITE);
                logoutButton.addActionListener(this);
                buttonPanel.add(logoutButton, c);

                mainPanel.revalidate();
                mainPanel.repaint();
                frame.getContentPane().removeAll();
                frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }else{
                this.utenteLoggato = null;
                JOptionPane.showMessageDialog(frame, "login fallito");
            }
            //
        }else if(e.getSource()==avanti){
            JOptionPane.showMessageDialog(frame, "Ora registrati per sfruttare l'app al meglio!");
            frame.getContentPane().removeAll();
            frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            //Bottone per l'apertura del pannello per la gestione delle playlist
        }else if(e.getSource() == playlistButton){
            playlistPanelB = new JPanel(new GridBagLayout());
            playlistPanel = new JPanel();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 5, 10, 5);
            gbc.anchor = GridBagConstraints.WEST;

            //Creazione titolo panel
            JLabel creaplaylistLabel = new JLabel("Crea Playlist");
            creaplaylistLabel.setForeground(Color.WHITE);
            creaplaylistLabel.setFont(new Font("Arial", Font.BOLD, 24));
            creaplaylistLabel.setForeground(Color.WHITE);

            playlistPanel.add(creaplaylistLabel, BorderLayout.CENTER);
            playlistPanelB.setBackground(new Color(32, 33, 35));
            playlistPanel.setBackground(new Color(32, 33, 35));

            creaplaylistButton = new JButton("Crea playlist");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_START;

            creaplaylistButton.addActionListener(this);
            creaplaylistButton.setForeground(Color.WHITE);
            creaplaylistButton.setBackground(new Color(70, 80, 120));
            playlistPanelB.add(creaplaylistButton, gbc);

            addsongButton = new JButton("Aggiungi canzone alla playlist");
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_START;
            addsongButton.addActionListener(this);
            addsongButton.setForeground(Color.WHITE);
            addsongButton.setBackground(new Color(70, 80, 120));
            playlistPanelB.add(addsongButton, gbc);

            indietro = new JButton("Indietro");
            gbc.gridx = 3;
            gbc.gridy = 0;
            indietro.setForeground(Color.WHITE);
            gbc.anchor = GridBagConstraints.LINE_START;
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);
            playlistPanelB.add(indietro, gbc);

            viewPlaylistButton = new JButton("visualizza playlist");
            viewPlaylistButton.addActionListener(this);
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_START;
            viewPlaylistButton.setBackground(new Color(70, 80, 120));
            viewPlaylistButton.setForeground(Color.WHITE);
            playlistPanelB.add(viewPlaylistButton, gbc);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(playlistPanelB, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            //Bottone per la creazione di una playlist--> apre il pannello nel quale scegliere le canzoni
        }else if(e.getSource()==creaplaylistButton){
            createPlaylistPanel = new JPanel(new FlowLayout());

            namePLaylistLabel = new JLabel("Dai il nome alla playlist: ");
            namePLaylistLabel.setForeground(Color.WHITE);
            namePlaylistfield = new JTextField(20);

            searchPlaylistButton = new JButton("Avanti");
            searchPlaylistButton.setBackground(new Color(70, 80, 120));
            searchPlaylistButton.setForeground(Color.WHITE);
            searchPlaylistButton.addActionListener(this);

            indietro = new JButton("Indietro");
            indietro.setForeground(Color.WHITE);
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            createPlaylistPanel.add(namePLaylistLabel);
            createPlaylistPanel.add(namePlaylistfield);
            createPlaylistPanel.add(searchPlaylistButton);
            createPlaylistPanel.add(indietro);
            createPlaylistPanel.setBackground(new Color(32, 33, 35));

            frame.getContentPane().removeAll();
            frame.getContentPane().add(createPlaylistPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            //Bottone per l'assegnamento di un voto ad una canzone
        } else if(e.getSource()==ratingButton){
            //Creazione del search panel
            searchPanel = new JPanel(new GridBagLayout());
            searchPanel.setForeground(new Color(255, 255, 255));
            searchPanel.setBackground(new Color(32, 33, 35));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            searchLabel = new JLabel("Cerca la canzone da valutare per titolo");
            searchLabel.setForeground(Color.WHITE);
            searchLabel.setFont(new Font("Arial", Font.BOLD, 24));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            searchPanel.add(searchLabel, gbc);

            //Creazione di label e field per la ricerca
            titleLabel = new JLabel("Titolo:");
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            titleLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(titleLabel, gbc);


            Titlefield = new JTextField(20);
            Titlefield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 1;
            Titlefield.setForeground(new Color(255, 255, 255));
            Titlefield.setBackground(new Color(32, 33, 35));
            searchPanel.add(Titlefield, gbc);

            //Ricerca per titolo
            searchTitoloRating = new JButton("Cerca per titolo");
            gbc.gridx = 1;
            gbc.gridy = 4;
            searchTitoloRating.addActionListener(this);
            searchTitoloRating.setForeground(new Color(255, 255, 255));
            searchTitoloRating.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchTitoloRating, gbc);


            indietro = new JButton("Indietro");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            indietro.addActionListener(this);
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            searchPanel.add(indietro, gbc);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(searchPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

        }else if(e.getSource()==logoutButton) {
            utenteLoggato = null;
            JOptionPane.showMessageDialog(frame, "logout effettuato");

            frame.getContentPane().removeAll();
            mainPanel.removeAll();
            //Frame principale

            frame.setLocation(new Point(200, 100));

            titleLabel = new JLabel("Emotional Song", SwingConstants.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
            titleLabel.setForeground(Color.WHITE);

            //Creazione del main panel
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(32, 33, 35));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setBackground(new Color(32, 33, 35));

            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(10, 10, 10, 10); // Aggiungi margine tra i pulsanti

            //Creazione registration button
            registrationButton = new JButton("Registrazione");
            registrationButton.addActionListener(this);
            registrationButton.setForeground(new Color(255, 255, 255));
            registrationButton.setBackground(new Color(70, 80, 120));

            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.LINE_START;
            buttonPanel.add(registrationButton, c);

            //Creazione login button
            loginButton = new JButton("Login");
            loginButton.addActionListener(this);
            loginButton.setForeground(new Color(255, 255, 255));
            loginButton.setBackground(new Color(70, 80, 120));

            c.gridx = 1;
            c.gridy = 0;
            c.anchor = GridBagConstraints.CENTER;
            buttonPanel.add(loginButton, c);

            //Creazione search button
            searchButton = new JButton("Ricerca");
            searchButton.addActionListener(this);
            searchButton.setForeground(new Color(255, 255, 255));
            searchButton.setBackground(new Color(70, 80, 120));

            c.gridx = 2;
            c.gridy = 0;
            c.anchor = GridBagConstraints.LINE_END;
            buttonPanel.add(searchButton, c);
            mainPanel.add(buttonPanel, BorderLayout.CENTER);

            //Immissione del main panel nel frame
            frame.add(mainPanel, BorderLayout.CENTER);
            frame.setBounds(250, 90, 800, 500);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            //Bottone indietro per tornare alla home
        }else if(e.getSource() == indietro){
            frame.setTitle("Emotional Songs");
            frame.getContentPane().removeAll();
            frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            //Tasto per entrare nel menu di inserimento canzoni nella playlist
        }else if (e.getSource() == searchPlaylistButton) {
            String nomePlaylist = namePlaylistfield.getText();
            playlistTransizione = new Playlist(nomePlaylist, utenteLoggato);

            //Creazione del search panel
            searchPanel = new JPanel(new GridBagLayout());
            searchPanel.setForeground(new Color(255, 255, 255));
            searchPanel.setBackground(new Color(32, 33, 35));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            searchLabel = new JLabel("Ricerca canzoni della playlist per titolo o autore/anno");
            searchLabel.setForeground(Color.WHITE);
            searchLabel.setFont(new Font("Arial", Font.BOLD, 24));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            searchPanel.add(searchLabel, gbc);

            //Creazione di label e field per la ricerca
            titleLabel = new JLabel("Titolo:");
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            titleLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(titleLabel, gbc);

            authorLabel = new JLabel("Autore:");
            gbc.gridx = 0;
            gbc.gridy = 3;
            authorLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(authorLabel, gbc);

            yearLabel = new JLabel("Anno:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            yearLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(yearLabel, gbc);

            yearfield = new JTextField(20);
            yearfield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 2;
            yearfield.setForeground(new Color(255, 255, 255));
            yearfield.setBackground(new Color(32, 33, 35));
            searchPanel.add(yearfield, gbc);

            Authorfield = new JTextField(20);
            Authorfield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 3;
            Authorfield.setForeground(new Color(255, 255, 255));
            Authorfield.setBackground(new Color(32, 33, 35));
            searchPanel.add(Authorfield, gbc);

            Titlefield = new JTextField(20);
            Titlefield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 1;
            Titlefield.setForeground(new Color(255, 255, 255));
            Titlefield.setBackground(new Color(32, 33, 35));
            searchPanel.add(Titlefield, gbc);

            //Ricerca per titolo
            searchTitoloPlaylist = new JButton("Cerca per titolo");
            gbc.gridx = 1;
            gbc.gridy = 5;
            searchTitoloPlaylist.addActionListener(this);
            searchTitoloPlaylist.setForeground(new Color(255, 255, 255));
            searchTitoloPlaylist.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchTitoloPlaylist, gbc);

            //Ricerca per autore e anno
            searchAutAnnoPlaylist = new JButton("Cerca per autore e anno");
            gbc.gridx = 1;
            gbc.gridy = 4;
            searchAutAnnoPlaylist.addActionListener(this);
            searchAutAnnoPlaylist.setForeground(new Color(255, 255, 255));
            searchAutAnnoPlaylist.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchAutAnnoPlaylist, gbc);

            indietro = new JButton("Indietro");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            indietro.addActionListener(this);
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            searchPanel.add(indietro, gbc);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(searchPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();


        }else if (e.getSource() == searchTitoloPlaylist){
            tablePanel = new JPanel();
            tablePanel.setBackground(new Color(32, 33, 35));
            String [] col = {"titolo","codice"};
            String titolo = Titlefield.getText();

            try{
                table = new JTable(cercaBranoMusicale(titolo, databaseInterface.getInstance()) ,col);
                TableColumn column = table.getColumnModel().getColumn(1);
                column.setMinWidth(0);
                column.setMaxWidth(0);

                ArrayList<String> canzoniPlaylist = new ArrayList<>();
                //Listener per quando si schiaccia su una riga della tabella


                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) {
                            JTable target = (JTable)e.getSource();
                            int row = target.getSelectedRow();
                            String value = (String) target.getValueAt(row, 1);
                            canzoniPlaylist.add(value);
                            rimuoviDuplicati(canzoniPlaylist);


                            playlistTransizione.addCanzoni(canzoniPlaylist);
                            contatoreCanzoniSelezionate++;
                            contatore.setText(Integer.toString(contatoreCanzoniSelezionate));

                        }
                    }
                });



                table.setBackground(new Color(32, 33, 35));
                table.setForeground(new Color(255, 255, 255));
                table.setDefaultEditor(Object.class, null);                               //Disabilita la modifica delle celle con doppio clic
            } catch(SQLException ex){
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.getViewport().setForeground(Color.white);
            scrollPane.getViewport().setBackground(Color.BLACK);

            contatoreCanzoniSelezionate = 0;

            JLabel label = new JLabel("brani selezionati: ");
            label.setForeground(new Color(255, 255, 255));
            label.setBackground(new Color(70, 80, 120));

            contatore = new JTextField(Integer.toString(contatoreCanzoniSelezionate), 10);
            contatore.setEnabled(false);
            contatore.setForeground(new Color(255, 255, 255));
            contatore.setBackground(new Color(70, 80, 120));
            contatore.setCaretColor(new Color(255, 255, 255));


            indietro = new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            confermaPlaylist = new JButton("conferma");
            confermaPlaylist.setForeground(new Color(255, 255, 255));
            confermaPlaylist.setBackground(new Color(70, 80, 120));
            confermaPlaylist.addActionListener(this);

            tablePanel.add(scrollPane);
            tablePanel.add(label);
            tablePanel.add(contatore);
            tablePanel.add(confermaPlaylist);
            tablePanel.add(indietro);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();


        }else if (e.getSource() == confermaPlaylist){
            JOptionPane.showMessageDialog(frame,"Playlist creata!");

            contatoreCanzoniSelezionate=0;
            contatore = null;
            try {
                registraPlaylist(playlistTransizione);
                frame.setTitle("Emotional Songs");
                frame.getContentPane().removeAll();
                frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            playlistTransizione = null;
        }else if (e.getSource() == searchAutAnnoPlaylist){
            tablePanel = new JPanel();
            tablePanel.setBackground(new Color(32, 33, 35));
            String [] col = {"titolo","codice"};
            String autore = Authorfield.getText();
            int anno = Integer.parseInt(yearfield.getText());

            try{
                table = new JTable(cercaBranoMusicale(autore,anno, databaseInterface.getInstance()) ,col);
                TableColumn column = table.getColumnModel().getColumn(1);
                column.setMinWidth(0);
                column.setMaxWidth(0);

                ArrayList<String> canzoniPlaylist = new ArrayList<>();
                //Listener per quando si schiaccia su una riga della tabella

                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) {
                            JTable target = (JTable)e.getSource();
                            int row = target.getSelectedRow();
                            String value = (String) target.getValueAt(row, 1);
                            canzoniPlaylist.add(value);
                            rimuoviDuplicati(canzoniPlaylist);
                            playlistTransizione.addCanzoni(canzoniPlaylist);
                            contatoreCanzoniSelezionate++;
                            contatore.setText(Integer.toString(contatoreCanzoniSelezionate));
                        }
                    }
                });

                table.setBackground(new Color(32, 33, 35));
                table.setForeground(new Color(255, 255, 255));
                table.setDefaultEditor(Object.class, null);                               //Disabilita la modifica delle celle con doppio clic
            } catch(SQLException ex){
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.getViewport().setForeground(Color.white);
            scrollPane.getViewport().setBackground(Color.BLACK);

            contatoreCanzoniSelezionate = 0;

            contatore = new JTextField(Integer.toString(contatoreCanzoniSelezionate), 10);
            contatore.setEnabled(false);
            contatore.setForeground(new Color(255, 255, 255));
            contatore.setBackground(new Color(70, 80, 120));

            indietro = new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            confermaPlaylist = new JButton("conferma");
            confermaPlaylist.setForeground(new Color(255, 255, 255));
            confermaPlaylist.setBackground(new Color(70, 80, 120));
            confermaPlaylist.addActionListener(this);

            tablePanel.add(scrollPane);
            tablePanel.add(contatore);
            tablePanel.add(confermaPlaylist);
            tablePanel.add(indietro);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
            //Bottone per l'apertura del pannello di ricerca delle canzoni

        }else if (e.getSource() == searchButton) {
            //Creazione del search panel
            searchPanel = new JPanel(new GridBagLayout());
            searchPanel.setForeground(new Color(255, 255, 255));
            searchPanel.setBackground(new Color(32, 33, 35));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            searchLabel = new JLabel("Ricerca");
            searchLabel.setForeground(Color.WHITE);
            searchLabel.setFont(new Font("Arial", Font.BOLD, 24));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            searchPanel.add(searchLabel, gbc);

            //Creazione di label e field per la ricerca
            titleLabel = new JLabel("Titolo:");
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            titleLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(titleLabel, gbc);

            authorLabel = new JLabel("Autore:");
            gbc.gridx = 0;
            gbc.gridy = 3;
            authorLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(authorLabel, gbc);

            yearLabel = new JLabel("Anno:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            yearLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(yearLabel, gbc);

            yearfield = new JTextField(20);
            yearfield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 2;
            yearfield.setForeground(new Color(255, 255, 255));
            yearfield.setBackground(new Color(32, 33, 35));
            searchPanel.add(yearfield, gbc);

            Authorfield = new JTextField(20);
            Authorfield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 3;
            Authorfield.setForeground(new Color(255, 255, 255));
            Authorfield.setBackground(new Color(32, 33, 35));
            searchPanel.add(Authorfield, gbc);

            Titlefield = new JTextField(20);
            Titlefield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 1;
            Titlefield.setForeground(new Color(255, 255, 255));
            Titlefield.setBackground(new Color(32, 33, 35));
            searchPanel.add(Titlefield, gbc);

            //Ricerca per titolo
            searchTitolo = new JButton("Cerca per titolo");
            gbc.gridx = 1;
            gbc.gridy = 5;
            searchTitolo.addActionListener(this);
            searchTitolo.setForeground(new Color(255, 255, 255));
            searchTitolo.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchTitolo, gbc);

            //Ricerca per autore e anno
            searchAutAnno = new JButton("Cerca per autore e anno");
            gbc.gridx = 1;
            gbc.gridy = 4;
            searchAutAnno.addActionListener(this);
            searchAutAnno.setForeground(new Color(255, 255, 255));
            searchAutAnno.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchAutAnno, gbc);

            indietro = new JButton("Indietro");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            indietro.addActionListener(this);
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            searchPanel.add(indietro, gbc);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(searchPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            //Bottone per confermare la ricerca una volta inserito il titolo
        }else if(e.getSource() == searchTitolo){
            tablePanel = new JPanel();
            tablePanel.setBackground(new Color(32, 33, 35));
            String[] col = {"titolo",""};
            String titolo = Titlefield.getText();

            try{
                table = new JTable(cercaBranoMusicale(titolo, databaseInterface.getInstance()),col);
                TableColumn column = table.getColumnModel().getColumn(1);
                column.setMinWidth(0);
                column.setMaxWidth(0);

                table.setBackground(new Color(32, 33, 35));
                table.setForeground(new Color(255, 255, 255));
                table.setDefaultEditor(Object.class, null);                             //Disabilita la modifica delle celle con doppio clic
            }catch(SQLException ex){
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.getViewport().setForeground(Color.white);
            scrollPane.getViewport().setBackground(Color.BLACK);

            indietro = new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            tablePanel.add(scrollPane);
            tablePanel.add(indietro);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            table.getModel().addTableModelListener(new TableModelListener() {
                /**
                 *
                 * @param e a {@code TableModelEvent} to notify listener that a table model
                 *          has changed
                 */
                public void tableChanged(TableModelEvent e){
                    int row = table.getSelectedRow();
                    int col = table.getSelectedColumn();
                    System.out.println(row + " " + col);
                }
            });

            //Bottone per confermare la ricerca una volta inseriti autore e anno
        }else if(e.getSource() == searchAutAnno){
            tablePanel = new JPanel();
            tablePanel.setBackground(new Color(32, 33, 35));
            String [] col = {"titolo","codice"};
            String autore = Authorfield.getText();
            int anno = Integer.parseInt(yearfield.getText());

            try{
                table = new JTable(cercaBranoMusicale(autore,anno, databaseInterface.getInstance()),col);
                TableColumn column = table.getColumnModel().getColumn(1);            //get the TableColumn object for the desired column index
                column.setMinWidth(0);                                                          //set the minimum width of the column to zero
                column.setMaxWidth(0);                                                          //set the maximum width of the column to zero

                table.setBackground(new Color(32, 33, 35));
                table.setForeground(new Color(255, 255, 255));
                table.setDefaultEditor(Object.class, null);                               //Disabilita la modifica delle celle con doppio clic
            } catch(SQLException ex){
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.getViewport().setForeground(Color.white);
            scrollPane.getViewport().setBackground(Color.BLACK);

            indietro = new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            tablePanel.add(scrollPane);
            tablePanel.add(indietro);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

        }else if (e.getSource() == addsongButton){
            //Database.Query per interrogare il database per capire che playlist l'utente loggato ha creata in modo da permetteregli di scegliere a quale playlist aggiungere canzoni
            String q = "select distinct(nomeplaylist) from playlist where codf = '" + utenteLoggato.getCodiceFiscale() +"'";
            Query query = new Query(q);
            lista = new JListUtility();
            try {
                ArrayList<String> arrayList =databaseInterface.QueryVisualizzaPlaylist(query);
                for(int i=0; i<arrayList.size(); i++){
                    lista.addStringToList(arrayList.get(i));
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }


            bottoneLista2 = new JButton("Conferma");
            bottoneLista2.addActionListener(this);
            lista.add(bottoneLista2);

            frame.getContentPane().removeAll();
            //Aggiungo la lista come panel al frame
            frame.getContentPane().add(lista);
            frame.revalidate();
            frame.repaint();



        }else if (e.getSource() == searchTitoloRating) {

            tablePanel = new JPanel();
            tablePanel.setBackground(new Color(32, 33, 35));
            String[] col = {"titolo", ""};
            String titolo = Titlefield.getText();

            try {
                table = new JTable(cercaBranoMusicale(titolo, databaseInterface.getInstance()), col);
                TableColumn column = table.getColumnModel().getColumn(1);
                column.setMinWidth(0);
                column.setMaxWidth(0);
                //Listener per quando si schiaccia su una riga della tabella


                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) {
                            JTable target = (JTable) e.getSource();
                            int row = target.getSelectedRow();
                            String value = (String) target.getValueAt(row, 1);
                            canzoneDaValutare = value;
                            avantiRating.setEnabled(true);
                        }
                    }
                });

                table.setBackground(new Color(32, 33, 35));
                table.setForeground(new Color(255, 255, 255));
                table.setDefaultEditor(Object.class, null);
                //Disabilita la modifica delle celle con doppio clic
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.getViewport().setForeground(Color.white);
            scrollPane.getViewport().setBackground(Color.BLACK);

            indietro = new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            avantiRating = new JButton("Avanti");
            avantiRating.setForeground(new Color(255, 255, 255));
            avantiRating.setBackground(new Color(70, 80, 120));
            avantiRating.addActionListener(this);
            avantiRating.setEnabled(false);


            tablePanel.add(scrollPane);
            tablePanel.add(indietro);
            tablePanel.add(avantiRating);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            table.getModel().addTableModelListener(new TableModelListener() {
                /**
                 * @param e a {@code TableModelEvent} to notify listener that a table model
                 *          has changed
                 */
                public void tableChanged(TableModelEvent e) {
                    int row = table.getSelectedRow();
                    int col = table.getSelectedColumn();
                    System.out.println(row + " " + col);
                }
            });

            //Bottone per confermare la ricerca una volta inseriti autore e anno

        }else if(e.getSource()==avantiRating){

            JPanel ratingPanel = new JPanel();
            ratingPanel.setLayout(new FlowLayout());
            ratingPanel.setBackground(new Color(32, 33, 35));

            String [] emozioni = {"Amazement","Solemnity","Tenderness","Nostalgia","Calmness","Power","Joy","Tension","Sadness"};
            JList<String> list=new JList(emozioni);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedIndex = list.getSelectedIndex();
                        if (selectedIndex != -1) {
                            String selectedElement = emozioni[selectedIndex];
                            emozioneRating = selectedElement;
                        }
                    }
                }
            });
            JLabel labelEmoz=new JLabel("Scegliere emozione da valutare   ");
            labelEmoz.setForeground(Color.WHITE);
            JLabel labelVoto=new JLabel("Inserire il voto relativo all'emozione(tra 1 e 5) ");
            labelVoto.setForeground(Color.WHITE);
            JLabel spazio = new JLabel("               ");
            textRating=new JTextField(10);
            confermaRating = new JButton("Conferma voto");
            confermaRating.addActionListener(this);

            JScrollPane scrollPane = new JScrollPane(list);
            ratingPanel.add(labelEmoz);
            ratingPanel.add(list);
            ratingPanel.add(spazio);
            ratingPanel.add(labelVoto);
            ratingPanel.add(textRating);
            ratingPanel.add(confermaRating);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(ratingPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

        }else if (e.getSource() == confermaRating){
            Boolean flag=true;
            String voto = textRating.getText();
            String emozione = emozioneRating;
            emozioneRating=null;
            String brano = canzoneDaValutare;
            canzoneDaValutare=null;
            String cf = utenteLoggato.getCodiceFiscale();
            String q = "insert into emozioni(codcanz,emozione,voto,codf) values ('"+brano+"','"+emozione+"','"+voto+"','"+cf+"')";
            Query query = new Query(q);
            if(Double.parseDouble(voto) <= 5){
                try {
                    registraVotoEmozione(query);
                } catch (org.postgresql.util.PSQLException ex){
                    JOptionPane.showMessageDialog(frame,"Errore, hai già dato un voto a questo brano relativo a questa emozione!");
                     flag=false;
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                if(flag==true){
                    JOptionPane.showMessageDialog(frame,"valutazione inserita!");
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                }

            }else{
                JOptionPane.showMessageDialog(frame,"errore, il voto deve essere compreso tra 1 e 5!");
            }


        }else if (e.getSource() == viewPlaylistButton){

            String q = "select distinct(nomeplaylist) from playlist where codf = '" + utenteLoggato.getCodiceFiscale() +"'";
            Query query = new Query(q);
            lista = new JListUtility();

            try {
                ArrayList<String> arrayList =databaseInterface.QueryVisualizzaPlaylist(query);
                for(int i=0; i<arrayList.size(); i++){
                    lista.addStringToList(arrayList.get(i));
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            bottoneLista = new JButton("conferma");
            bottoneLista.setForeground(new Color(255, 255, 255));
            bottoneLista.setBackground(new Color(70, 80, 120));
            bottoneLista.addActionListener(this);

            lista.add(bottoneLista);



            frame.getContentPane().removeAll();
            //Aggiungo la lista come panel al frame
            frame.getContentPane().add(lista);
            frame.revalidate();
            frame.repaint();





        }else if(e.getSource() == bottoneLista){

            clientEs.playlistVisualizzazione = lista.nomePlaylistSelezionata();
            lista=null;
            String q = "select titolo from canzoni where codcanz IN( Select codcanz from playlist where nomeplaylist = "+"'"+playlistVisualizzazione+"'"+" AND codf = "+"'"+ utenteLoggato.getCodiceFiscale()+"'"+")";
            Query query = new Query(q);

            JListUtility lista = new JListUtility();


            try {
                ArrayList<String> arrayList =databaseInterface.QueryVisualizzaPlaylist(query);
                for(int i=0; i<arrayList.size(); i++){
                    lista.addStringToList(arrayList.get(i));
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }


            indietro=new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);
            lista.add(indietro);
            frame.getContentPane().removeAll();
            //Aggiungo la lista come panel al frame
            playlistVisualizzazione=null;
            frame.getContentPane().add(lista);
            frame.revalidate();
            frame.repaint();

        }else if(e.getSource() == bottoneLista2){
            clientEs.playlistVisualizzazione = lista.nomePlaylistSelezionata();
            lista=null;
            //Usato in aggiungi canzone a playlist
            searchPanel = new JPanel(new GridBagLayout());
            searchPanel.setForeground(new Color(255, 255, 255));
            searchPanel.setBackground(new Color(32, 33, 35));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            searchLabel = new JLabel("Ricerca");
            searchLabel.setForeground(Color.WHITE);
            searchLabel.setFont(new Font("Arial", Font.BOLD, 24));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            searchPanel.add(searchLabel, gbc);

            //Creazione di label e field per la ricerca
            titleLabel = new JLabel("Titolo:");
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            titleLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(titleLabel, gbc);

            authorLabel = new JLabel("Autore:");
            gbc.gridx = 0;
            gbc.gridy = 3;
            authorLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(authorLabel, gbc);

            yearLabel = new JLabel("Anno:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            yearLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(yearLabel, gbc);

            yearfield = new JTextField(20);
            yearfield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 2;
            yearfield.setForeground(new Color(255, 255, 255));
            yearfield.setBackground(new Color(32, 33, 35));
            searchPanel.add(yearfield, gbc);

            Authorfield = new JTextField(20);
            Authorfield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 3;
            Authorfield.setForeground(new Color(255, 255, 255));
            Authorfield.setBackground(new Color(32, 33, 35));
            searchPanel.add(Authorfield, gbc);

            Titlefield = new JTextField(20);
            Titlefield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 1;
            Titlefield.setForeground(new Color(255, 255, 255));
            Titlefield.setBackground(new Color(32, 33, 35));
            searchPanel.add(Titlefield, gbc);

            //Ricerca per titolo
            searchTitoloAddPlaylist = new JButton("Cerca per titolo");
            gbc.gridx = 1;
            gbc.gridy = 5;
            searchTitoloAddPlaylist.addActionListener(this);
            searchTitoloAddPlaylist.setForeground(new Color(255, 255, 255));
            searchTitoloAddPlaylist.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchTitoloAddPlaylist, gbc);

            //Ricerca per autore e anno
            searchAutAnnoAddPlaylist = new JButton("Cerca per autore e anno");
            gbc.gridx = 1;
            gbc.gridy = 4;
            searchAutAnnoAddPlaylist.addActionListener(this);
            searchAutAnnoAddPlaylist.setForeground(new Color(255, 255, 255));
            searchAutAnnoAddPlaylist.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchAutAnnoAddPlaylist, gbc);

            indietro = new JButton("Indietro");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            indietro.addActionListener(this);
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            searchPanel.add(indietro, gbc);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(searchPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

        } else if(e.getSource() == searchTitoloAddPlaylist){
            //Ricerca della canzone da inserire in una playlist tramite Titolo
            tablePanel = new JPanel();
            tablePanel.setBackground(new Color(32, 33, 35));
            String [] col = {"titolo","codice"};
            String titolo = Titlefield.getText();

            try{
                table = new JTable(cercaBranoMusicale(titolo, databaseInterface.getInstance()) ,col);
                TableColumn column = table.getColumnModel().getColumn(1);
                column.setMinWidth(0);
                column.setMaxWidth(0);

                canzoniPlaylistGlobale = new ArrayList<>();
                //Listener per quando si schiaccia su una riga della tabella


                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) {
                            JTable target = (JTable)e.getSource();
                            int row = target.getSelectedRow();
                            String value = (String) target.getValueAt(row, 1);
                            canzoniPlaylistGlobale.add(value);
                            rimuoviDuplicati(canzoniPlaylistGlobale);
                            contatoreCanzoniSelezionate++;
                            contatore.setText(Integer.toString(contatoreCanzoniSelezionate));
                        }
                    }
                });

                table.setBackground(new Color(32, 33, 35));
                table.setForeground(new Color(255, 255, 255));
                table.setDefaultEditor(Object.class, null);                               //Disabilita la modifica delle celle con doppio clic
            } catch(SQLException ex){
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }


            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.getViewport().setForeground(Color.white);
            scrollPane.getViewport().setBackground(Color.BLACK);

            contatoreCanzoniSelezionate = 0;

            contatore = new JTextField(Integer.toString(contatoreCanzoniSelezionate), 10);
            contatore.setEnabled(false);
            contatore.setForeground(new Color(255, 255, 255));
            contatore.setBackground(new Color(70, 80, 120));


            indietro = new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            confermaAggiuntaPlaylist = new JButton("conferma");
            confermaAggiuntaPlaylist.setForeground(new Color(255, 255, 255));
            confermaAggiuntaPlaylist.setBackground(new Color(70, 80, 120));
            confermaAggiuntaPlaylist.addActionListener(this);

            tablePanel.add(scrollPane);
            tablePanel.add(contatore);
            tablePanel.add(confermaAggiuntaPlaylist);
            tablePanel.add(indietro);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();


        } else if(e.getSource()==confermaAggiuntaPlaylist){

            String q="Select codcanz from Playlist where nomeplaylist = '"+ playlistVisualizzazione + "' and codF = '" + utenteLoggato.getCodiceFiscale() +"'";
            Query q1=new Query(q);

            ArrayList<String> canzoniPresenti;
            try {
                canzoniPresenti = databaseInterface.QueryRicercaCanzoniGiaInPlaylist(q1);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            //si raccolgono i codici delle canzoni da aggiungere --> in canzoniPlaylistGlobale

            //si confrontalo i due set di codici e si tolgono i duplicati se ci sono dal set delle canzoni d aggiungere
            ArrayList<String> canzoniDefintive = rimuoviDuplicati(canzoniPlaylistGlobale,canzoniPresenti);
            //si fa una query che inserisce le nuove canzoni nella playlist
            //ci serve il CF dell'utente + nomePlaylist+ codici
            Playlist playlist=new Playlist(playlistVisualizzazione, utenteLoggato);
            playlist.addCanzoni(canzoniDefintive);
            try {
                databaseInterface.RegistraPlaylist(playlist);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            canzoniPlaylistGlobale=null;
            JOptionPane.showMessageDialog(frame,"inserimento riuscito!");
            playlistVisualizzazione=null;
            frame.getContentPane().removeAll();
            frame.getContentPane().add(mainPanel);
            frame.revalidate();
            frame.repaint();

        } else if(e.getSource()==searchAutAnnoAddPlaylist){
            {
                tablePanel = new JPanel();
                tablePanel.setBackground(new Color(32, 33, 35));
                String [] col = {"titolo","codice"};
                String autore = Authorfield.getText();
                int anno = Integer.parseInt(yearfield.getText());

                try{
                    table = new JTable(cercaBranoMusicale(autore,anno, databaseInterface.getInstance()) ,col);
                    TableColumn column = table.getColumnModel().getColumn(1);
                    column.setMinWidth(0);
                    column.setMaxWidth(0);

                    canzoniPlaylistGlobale = new ArrayList<>();
                    //Listener per quando si schiaccia su una riga della tabella


                    table.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount() == 1) { // detect a single click
                                JTable target = (JTable)e.getSource(); // get the JTable object that triggered the event
                                int row = target.getSelectedRow(); // get the selected row index
                                String value = (String) target.getValueAt(row, 1); // get the value of the second column in the selected row
                                canzoniPlaylistGlobale.add(value);
                                rimuoviDuplicati(canzoniPlaylistGlobale);
                                contatoreCanzoniSelezionate++;
                                contatore.setText(Integer.toString(contatoreCanzoniSelezionate));


                            }
                        }
                    });



                    table.setBackground(new Color(32, 33, 35));
                    table.setForeground(new Color(255, 255, 255));
                    table.setDefaultEditor(Object.class, null);                               //Disabilita la modifica delle celle con doppio clic
                } catch(SQLException ex){
                    throw new RuntimeException(ex);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }


                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.getViewport().setForeground(Color.white);
                scrollPane.getViewport().setBackground(Color.BLACK);

                contatoreCanzoniSelezionate = 0;

                contatore = new JTextField(Integer.toString(contatoreCanzoniSelezionate), 10);
                contatore.setEnabled(false);
                contatore.setForeground(new Color(255, 255, 255));
                contatore.setBackground(new Color(70, 80, 120));


                indietro = new JButton("Indietro");
                indietro.setForeground(new Color(255, 255, 255));
                indietro.setBackground(new Color(70, 80, 120));
                indietro.addActionListener(this);

                confermaAggiuntaPlaylist = new JButton("conferma");
                confermaAggiuntaPlaylist.setForeground(new Color(255, 255, 255));
                confermaAggiuntaPlaylist.setBackground(new Color(70, 80, 120));
                confermaAggiuntaPlaylist.addActionListener(this);

                tablePanel.add(scrollPane);

                tablePanel.add(contatore);
                tablePanel.add(confermaAggiuntaPlaylist);
                tablePanel.add(indietro);

                frame.getContentPane().removeAll();
                frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();

            }
        }else if(e.getSource()==emotionButton) {

            searchPanel = new JPanel(new GridBagLayout());
            searchPanel.setForeground(new Color(255, 255, 255));
            searchPanel.setBackground(new Color(32, 33, 35));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            searchLabel = new JLabel("Cerca il brano di cui vuoi vedere i voti");
            searchLabel.setForeground(Color.WHITE);
            searchLabel.setFont(new Font("Arial", Font.BOLD, 24));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            searchPanel.add(searchLabel, gbc);

            //Creazione di label e field per la ricerca
            titleLabel = new JLabel("Titolo:");
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            titleLabel.setForeground(new Color(255, 255, 255));
            searchPanel.add(titleLabel, gbc);


            Titlefield = new JTextField(20);
            Titlefield.setCaretColor(Color.WHITE);
            gbc.gridx = 1;
            gbc.gridy = 1;
            Titlefield.setForeground(new Color(255, 255, 255));
            Titlefield.setBackground(new Color(32, 33, 35));
            searchPanel.add(Titlefield, gbc);

            //Ricerca per titolo
            searchTitoloEmozioni = new JButton("Cerca per titolo");
            gbc.gridx = 1;
            gbc.gridy = 4;
            searchTitoloEmozioni.addActionListener(this);
            searchTitoloEmozioni.setForeground(new Color(255, 255, 255));
            searchTitoloEmozioni.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchTitoloEmozioni, gbc);

            indietro = new JButton("Indietro");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            indietro.addActionListener(this);
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            searchPanel.add(indietro, gbc);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(searchPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

        }else if(e.getSource()==searchTitoloEmozioni){

            tablePanel = new JPanel();
            tablePanel.setBackground(new Color(32, 33, 35));
            String[] col = {"titolo",""};
            String titolo = Titlefield.getText();

            try{
                table = new JTable(cercaBranoMusicale(titolo, databaseInterface.getInstance()),col);
                TableColumn column = table.getColumnModel().getColumn(1);
                column.setMinWidth(0);
                column.setMaxWidth(0);

                table.setBackground(new Color(32, 33, 35));
                table.setForeground(new Color(255, 255, 255));
                table.setDefaultEditor(Object.class, null);                             //Disabilita la modifica delle celle con doppio clic
            }catch(SQLException ex){
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.getViewport().setForeground(Color.white);
            scrollPane.getViewport().setBackground(Color.BLACK);

            indietro = new JButton("Indietro");
            indietro.setForeground(new Color(255, 255, 255));
            indietro.setBackground(new Color(70, 80, 120));
            indietro.addActionListener(this);

            confermaEmozioni = new JButton("Conferma");
            confermaEmozioni.setEnabled(false);
            confermaEmozioni.setForeground(new Color(255, 255, 255));
            confermaEmozioni.setBackground(new Color(70, 80, 120));
            confermaEmozioni.addActionListener(this);

            tablePanel.add(scrollPane);
            tablePanel.add(indietro);
            tablePanel.add(confermaEmozioni);

            frame.getContentPane().removeAll();
            frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();


            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = table.getSelectedRow();

                        if (selectedRow >= 0) {
                            String selectedValue = (String) table.getValueAt(selectedRow, 1);
                            branoEmozione = selectedValue;
                            confermaEmozioni.setEnabled(true);
                        }
                    }
                }
            });

        }else if(e.getSource()==confermaEmozioni){
            //qua si cercano tutti i voti della canzone contenuta in branoEmozione e si ottengono e mostrano i dati
            String q = "select emozione,voto from emozioni where codcanz='"+branoEmozione+"'";
            Query query=new Query(q);

            JPanel panel = new JPanel();
            panel.setForeground(new Color(255, 255, 255));
            panel.setBackground(new Color(32, 33, 35));


            JLabel l1=new JLabel("Amazement");
            l1.setForeground(new Color(255, 255, 255));
            JLabel l2=new JLabel("Solemnity");
            l2.setForeground(new Color(255, 255, 255));
            JLabel l3=new JLabel("Tenderness");
            l3.setForeground(new Color(255, 255, 255));
            JLabel l4=new JLabel("Nostalgia");
            l4.setForeground(new Color(255, 255, 255));
            JLabel l5=new JLabel("Calmness");
            l5.setForeground(new Color(255, 255, 255));
            JLabel l6=new JLabel("Power");
            l6.setForeground(new Color(255, 255, 255));
            JLabel l7=new JLabel("Joy");
            l7.setForeground(new Color(255, 255, 255));
            JLabel l8=new JLabel("Tension");
            l8.setForeground(new Color(255, 255, 255));
            JLabel l9=new JLabel("Sadness");
            l9.setForeground(new Color(255, 255, 255));

            JTextField t1=new JTextField(5);
            t1.setBackground(new Color(32, 33, 35));
            JTextField t2=new JTextField(5);
            t2.setBackground(new Color(32, 33, 35));
            JTextField t3=new JTextField(5);
            t3.setBackground(new Color(32, 33, 35));
            JTextField t4=new JTextField(5);
            t4.setBackground(new Color(32, 33, 35));
            JTextField t5=new JTextField(5);
            t5.setBackground(new Color(32, 33, 35));
            JTextField t6=new JTextField(5);
            t6.setBackground(new Color(32, 33, 35));
            JTextField t7=new JTextField(5);
            t7.setBackground(new Color(32, 33, 35));
            JTextField t8=new JTextField(5);
            t8.setBackground(new Color(32, 33, 35));
            JTextField t9=new JTextField(5);
            t9.setBackground(new Color(32, 33, 35));

            t1.setEnabled(false);
            t2.setEnabled(false);
            t3.setEnabled(false);
            t4.setEnabled(false);
            t5.setEnabled(false);
            t6.setEnabled(false);
            t7.setEnabled(false);
            t8.setEnabled(false);
            t9.setEnabled(false);
            //somma valori dei voti
            Double v1=0.0;
            Double v2=0.0;
            Double v3=0.0;
            Double v4=0.0;
            Double v5=0.0;
            Double v6=0.0;
            Double v7=0.0;
            Double v8=0.0;
            Double v9=0.0;
            //numero utenti di un emozione
            int i1=0;
            int i2=0;
            int i3=0;
            int i4=0;
            int i5=0;
            int i6=0;
            int i7=0;
            int i8=0;
            int i9=0;
            //calcolo tra i due precedenti
            Double d1;
            Double d2;
            Double d3;
            Double d4;
            Double d5;
            Double d6;
            Double d7;
            Double d8;
            Double d9;

            String x;

            try {
                ArrayList<String> arrayList=databaseInterface.QueryCercaVoti(query);

                for(int i=0; i<arrayList.size(); i++){

                    if (arrayList.get(i).equals("Amazement")) {

                        x = arrayList.get(i + 1);
                        i1++;
                        v1 = v1 + Double.parseDouble(x);
                        //return;
                    }
                    if(arrayList.get(i).equals("Solemnity")) {

                        System.out.println("sono qua");
                        x = arrayList.get(i + 1);
                        i2++;
                        v2 = v2 + Double.parseDouble(x);
                        //return;
                    }
                    if(arrayList.get(i).equals("Tenderness")) {
                        x = arrayList.get(i + 1);
                        i3++;
                        v3 = v3 + Double.parseDouble(x);
                        //return;
                    }
                    if(arrayList.get(i).equals("Nostalgia")) {
                        x = arrayList.get(i + 1);
                        i4++;
                        v4 = v4 + Double.parseDouble(x);
                        // return;
                    }
                    if(arrayList.get(i).equals("Calmness")) {
                        x = arrayList.get(i + 1);
                        i5++;
                        v5 = v5 + Double.parseDouble(x);
                        //return;
                    }
                    if(arrayList.get(i).equals("Power")) {
                        x = arrayList.get(i + 1);
                        i6++;
                        v6 = v6 + Double.parseDouble(x);
                        //return;
                    }
                    if(arrayList.get(i).equals("Joy")) {
                        x = arrayList.get(i + 1);
                        i7++;
                        v7 = v7 + Double.parseDouble(x);
                        //return;
                    }
                    if(arrayList.get(i).equals("Tension")) {
                        x = arrayList.get(i + 1);
                        i8++;
                        v8 = v8 + Double.parseDouble(x);
                        //return;
                    }
                    if(arrayList.get(i).equals("Sadness")) {
                            x = arrayList.get(i + 1);
                            i9++;
                            v9 = v9 + Double.parseDouble(x);
                            // return;
                    }

                }


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }finally {
                branoEmozione=null;

                //fare calcolo tra int e double e mostrarlo nei text

                d1 = v1 / i1;
                d2 = v2 / i2;
                d3 = v3 / i3;
                d4 = v4 / i4;
                d5 = v5 / i5;
                d6 = v6 / i6;
                d7 = v7 / i7;
                d8 = v8 / i8;
                d9 = v9 / i9;

                //assegnamento voti ai textfield
                if(d1.toString().equals("NaN")){
                    t1.setText("");
                }else{
                    t1.setText(d1.toString());
                }

                if(d2.toString().equals("NaN")){
                    t2.setText("");
                }else{
                    t2.setText(d2.toString());
                }

                if(d3.toString().equals("NaN")){
                    t3.setText("");
                }else{
                    t3.setText(d3.toString());
                }

                if(d4.toString().equals("NaN")){
                    t4.setText("");
                }else{
                    t4.setText(d4.toString());
                }

                if(d5.toString().equals("NaN")){
                    t5.setText("");
                }else{
                    t5.setText(d5.toString());
                }

                if(d6.toString().equals("NaN")){
                    t6.setText("");
                }else{
                    t6.setText(d6.toString());
                }

                if(d7.toString().equals("NaN")){
                    t7.setText("");
                }else{
                    t7.setText(d7.toString());
                }

                if(d8.toString().equals("NaN")){
                    t8.setText("");
                }else{
                    t8.setText(d8.toString());
                }

                if(d9.toString().equals("NaN")){
                    t9.setText("");
                }else{
                    t9.setText(d9.toString());
                }


                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                //definisco la larghezza massima dei textfield
                t1.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                t2.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                t3.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                t4.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                t5.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                t6.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                t7.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                t8.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                t9.setMaximumSize(new Dimension(200, t1.getPreferredSize().height));
                //aggiunte al panel

                panel.add(l1);
                panel.add(t1);
                panel.add(l2);
                panel.add(t2);
                panel.add(l3);
                panel.add(t3);
                panel.add(l4);
                panel.add(t4);
                panel.add(l5);
                panel.add(t5);
                panel.add(l6);
                panel.add(t6);
                panel.add(l7);
                panel.add(t7);
                panel.add(l8);
                panel.add(t8);
                panel.add(l9);
                panel.add(t9);
                panel.add(Box.createHorizontalStrut(530));
                panel.add(Box.createVerticalStrut(10));
                indietro = new JButton("indietro");
                indietro.addActionListener(this);
                panel.add(indietro);
                indietro.setForeground(new Color(255, 255, 255));
                indietro.setBackground(new Color(70, 80, 120));


                frame.getContentPane().removeAll();
                frame.getContentPane().add(panel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }


        }else if(e.getSource()==bottoneConferma){

        }
    }

}