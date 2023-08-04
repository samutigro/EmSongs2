package emotionalsongs;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

//Package della classe

//Importazione della libreria interna
import database.Database;
import database.InterfacciaDatabase;
import database.Query;

//Importazione di librerie esterne
import javax.swing.*;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//Importazione dei metodi
import static emotionalsongs.Brani.cercaBranoMusicale;
import static emotionalsongs.Brani.registraPlaylist;
import static emotionalsongs.Registrazione.login;
import static emotionalsongs.Registrazione.registrazione;

/**
 * Classe relativa all'interfaccia grafica; implementa l'interfaccia ActionListener, utilizzata per gestire gli eventi correlati alle azioni dell'utente su un componente dell'interfaccia grafica utente
 * @author De Vito Francesco
 * @author Auteri Samuele
 */
public class GUI implements ActionListener {
    Registry registry = LocateRegistry.getRegistry("127.0.0.1", 8999);
    InterfacciaDatabase databaseInterface = (InterfacciaDatabase)registry.lookup("SERVER");
    private  JPanel buttonPanel;
    private int contatoreCanzoniSelezionate;

    private final JFrame frame;

    private JPanel playlistPanelB,viewPlaylistPanel,mainPanel, registrationPanel, searchPanel, loginPanel, playlistPanel, ratingPanel, createPlaylistPanel, tablePanel, mainbuttonsPanel, maintitlePanel;

    private JButton bottoneLista,bottoneLista2, searchAutAnnoRating, searchTitoloRating, avanti, confermaPlaylist, searchTitoloPlaylist,searchAutAnnoPlaylist, logoutButton, viewPlaylistButton, registrationButton, loginButton, searchPlaylistButton, searchButton, registrationSaveButton, login, searchTitolo, playlistButton, ratingButton, searchAutAnno, creaplaylistButton, addsongButton, avantiPlay, indietro;

    private JLabel nameLabel, surnameLabel, usernameLabel, emailLabel, dateLabel, codiceFiscale, passwordLabel, yearLabel, authorLabel, titleLabel, insertsongLabel, namePLaylistLabel, userLable, pwdLable, loginLabel, searchLabel;

    private JTextField contatore, usernameField, emailField, dateField, CFfield, yearfield, Authorfield, Titlefield, insertsongField, namePlaylistfield, nameTextField, surnameTextField, userField;

    private JPasswordField passwordField, pwdField;

    private JTable table;

    private emotionalsongs.Utente utenteLoggato;

    private emotionalsongs.Playlist playlistTransizione;
    public static String playlistVisualizzazione;

    /**
     * Costruttore della classe GUI che implementa l'interfaccia grafica
     * @throws SQLException Rappresenta un'eccezione generata dall'API JDBC; quando si utilizza un database tramite JDBC, possono verificarsi diversi potenziali errori come ad esempio errori di connessione al database, errori nelle istruzioni SQL o problemi con il database stesso. Quando si verifica uno di questi errori, l'API JDBC genera un SQLException per indicare che si è verificato un errore.
     */
    public GUI() throws SQLException, RemoteException, NotBoundException {
        try{
            //Permette il Cross Platform permettendo quindi alla GUI di funzionare su diversi sistemi operativi come ad esempio macOS e Windows
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

            dateLabel = new JLabel("Data nascita (YYYY-MM-DD):");

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
            String date = dateField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            emotionalsongs.Utente utente = new emotionalsongs.Utente(name,surname,codiceFiscale,date,email,username,password);
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
            //client
            boolean flag;
            String user = userField.getText();
            String pwd = String.valueOf(pwdField.getPassword());
            String q = "Select * from utenti where username = '"+ user  + "'";
            Query query = new Query(q);
        //server
            try {
                Utente u=databaseInterface.QueryLogin(query);
                this.utenteLoggato=u;
                flag=databaseInterface.Login(user,pwd);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }


                //client
            if(flag == true){
                String username = userField.getText();
                JOptionPane.showMessageDialog(frame, "Benvenuto " + username + "!");

                buttonPanel.remove(registrationButton);
                buttonPanel.remove(loginButton);

                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(10, 10, 10, 10); // Aggiungi margine tra i pulsanti

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
            JLabel creaplaylistLabel = new JLabel("Crea emotionalsongs.Playlist");
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
            // frame.getContentPane().add(playlistPanel, BorderLayout.NORTH);
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

            searchLabel = new JLabel("Cerca la canzone da valutare per titolo o autore/anno");
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
            //da cambiare
            searchTitoloRating = new JButton("Cerca per titolo");
            gbc.gridx = 1;
            gbc.gridy = 5;
            searchTitoloRating.addActionListener(this);
            searchTitoloRating.setForeground(new Color(255, 255, 255));
            searchTitoloRating.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchTitoloRating, gbc);

            //Ricerca per autore e anno
            //da cambiare
            searchAutAnnoRating = new JButton("Cerca per autore e anno");
            gbc.gridx = 1;
            gbc.gridy = 4;
            searchAutAnnoRating.addActionListener(this);
            searchAutAnnoRating.setForeground(new Color(255, 255, 255));
            searchAutAnnoRating.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchAutAnnoRating, gbc);

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
            mainPanel.removeAll(); //qua bisogna aggiungere qualcosa!!
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
            //tasto per entrare nel menu di inserimento canzoni nella playlist
        }else if (e.getSource() == searchPlaylistButton) {
            String nomePlaylist = namePlaylistfield.getText();
            playlistTransizione = new emotionalsongs.Playlist(nomePlaylist, utenteLoggato);

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
                TableColumn column = table.getColumnModel().getColumn(1);            //get the TableColumn object for the desired column index
                column.setMinWidth(0);                                                          //set the minimum width of the column to zero
                column.setMaxWidth(0);                                                          //set the maximum width of the column to zero

                ArrayList<String> canzoniPlaylist = new ArrayList<>();
                //Listener per quando si schiaccia su una riga della tabella


                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) { // detect a single click
                            JTable target = (JTable)e.getSource(); // get the JTable object that triggered the event
                            int row = target.getSelectedRow(); // get the selected row index
                            String value = (String) target.getValueAt(row, 1); // get the value of the second column in the selected row
                            canzoniPlaylist.add(value);
                            playlistTransizione.addCanzoni(canzoniPlaylist);
                            contatoreCanzoniSelezionate++;
                            contatore.setText(Integer.toString(contatoreCanzoniSelezionate));

                            // do something with the selected row, for example:


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

            //JLabel label = new JLabel("canzoni selezionate:");
            //label.setForeground(new Color(255, 255, 255));
            //label.setBackground(new Color(70, 80, 120));

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
            //tablePanel.add(label);
            tablePanel.add(contatore);
            tablePanel.add(confermaPlaylist);
            tablePanel.add(indietro);





            frame.getContentPane().removeAll();
            frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();


        }else if (e.getSource() == confermaPlaylist){
            contatoreCanzoniSelezionate=0;
            contatore = null;
            try {
                registraPlaylist(playlistTransizione, databaseInterface.getInstance());
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
                TableColumn column = table.getColumnModel().getColumn(1);            //get the TableColumn object for the desired column index
                column.setMinWidth(0);                                                          //set the minimum width of the column to zero
                column.setMaxWidth(0);                                                          //set the maximum width of the column to zero

                ArrayList<String> canzoniPlaylist = new ArrayList<>();
                //Listener per quando si schiaccia su una riga della tabella


                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) { // detect a single click
                            JTable target = (JTable)e.getSource(); // get the JTable object that triggered the event
                            int row = target.getSelectedRow(); // get the selected row index
                            String value = (String) target.getValueAt(row, 1); // get the value of the second column in the selected row
                            canzoniPlaylist.add(value);
                            playlistTransizione.addCanzoni(canzoniPlaylist);
                            contatoreCanzoniSelezionate++;
                            contatore.setText(Integer.toString(contatoreCanzoniSelezionate));

                            // do something with the selected row, for example:


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

            //JLabel label = new JLabel("canzoni selezionate:");
            //label.setForeground(new Color(255, 255, 255));
            //label.setBackground(new Color(70, 80, 120));

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
            //tablePanel.add(label);
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
                TableColumn column = table.getColumnModel().getColumn(1);        //get the TableColumn object for the desired column index
                column.setMinWidth(0);                                                      // set the minimum width of the column to zero
                column.setMaxWidth(0);                                                      // set the maximum width of the column to zero
                ArrayList<String> canzoniPlaylist = new ArrayList<>();
                //Listener per quando si schiaccia su una riga della tabella


                /* table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) { // detect a single click
                            JTable target = (JTable)e.getSource(); // get the JTable object that triggered the event
                            int row = target.getSelectedRow(); // get the selected row index
                            String value = (String) target.getValueAt(row, 1); // get the value of the second column in the selected row
                            canzoniPlaylist.add(value);
                            // do something with the selected row, for example:
                            System.out.println(canzoniPlaylist);
                        }
                    }
                });*/

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
            //query per interrogare il database per capire che playlist l'utente loggato ha creata in modo da permetteregli di scegliere a quale playlist aggiungre canzoni
            //qualcosa del genere
            //client
            String q = "select distinct(nomeplaylist) from playlist where codf = '" + utenteLoggato.getCodiceFiscale() +"'";
            Query query = new Query(q);
            //
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


            bottoneLista2 = new JButton("Conferma");
            bottoneLista2.addActionListener(this);
            lista.add(bottoneLista2);

            frame.getContentPane().removeAll();
            //aggiungo la lista come panel al frame
            frame.getContentPane().add(lista);
            frame.revalidate();
            frame.repaint();
            //thread che fa il controllo continuo della playlist selezionata
            Thread1 t1 = new Thread1(lista);
            t1.start();


        }else if (e.getSource() == searchTitoloRating){
            //qua mettere il codice per produrre una tabella che contenere i brani simili a quello cercato, poi selezionarne uno e quello sarà quello che si valuterà
        }else if (e.getSource() == viewPlaylistButton){
            String q = "select distinct(nomeplaylist) from playlist where codf = '" + utenteLoggato.getCodiceFiscale() +"'";
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

            bottoneLista = new JButton("Conferma");
            bottoneLista.addActionListener(this);
            lista.add(bottoneLista);

            frame.getContentPane().removeAll();
            //aggiungo la lista come panel al frame
            frame.getContentPane().add(lista);
            frame.revalidate();
            frame.repaint();
            //thread che fa il controllo continuo della playlist selezionata
            Thread1 t1 = new Thread1(lista);
            t1.start();


        }else if(e.getSource() == bottoneLista){
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
            indietro.addActionListener(this);
            lista.add(indietro);
            frame.getContentPane().removeAll();
            //aggiungo la lista come panel al frame
            frame.getContentPane().add(lista);
            frame.revalidate();
            frame.repaint();

        }else if(e.getSource() == bottoneLista2){
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
            searchTitoloPlaylist = new JButton("Cerca per titolo");
            gbc.gridx = 1;
            gbc.gridy = 5;
            searchTitoloPlaylist.addActionListener(this);
            searchTitoloPlaylist.setForeground(new Color(255, 255, 255));
            searchTitoloPlaylist.setBackground(new Color(70, 80, 120));
            searchPanel.add(searchTitoloPlaylist, gbc);

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
        }
    }

    /**
     *Lancia la GUI
     * @param args main arguments
     * @throws SQLException Rappresenta un'eccezione generata dall'API JDBC; quando si utilizza un database tramite JDBC, possono verificarsi diversi potenziali errori come ad esempio errori di connessione al database, errori nelle istruzioni SQL o problemi con il database stesso. Quando si verifica uno di questi errori, l'API JDBC genera un SQLException per indicare che si è verificato un errore.
     */
    public static void main(String[] args) throws SQLException, RemoteException, NotBoundException {
        GUI emsong = new GUI();
    }
}