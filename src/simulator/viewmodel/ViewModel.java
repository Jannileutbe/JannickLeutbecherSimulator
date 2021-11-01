package simulator.viewmodel;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import simulator.model.CurrentEvent;
import simulator.model.Ladybug;
import simulator.model.Territory;
import simulator.model.exceptions.CompilerException;
import simulator.view.MyContextMenu;
import simulator.view.TerritoryPanel;
import simulator.view.View;

public class ViewModel {
    private final Territory territory;
    private final CurrentEvent currentEvent;

    public ViewModel(Stage stage) throws FileNotFoundException {
        this.territory = new Territory();
        this.currentEvent = new CurrentEvent();
        View view = new View(territory, this, currentEvent, stage);
    }

    //Wird aufgerufen, wenn Dinge im Spielfeld platziert werden sollen
    public void handleEventPlacingThings(MouseEvent me) {
        int clickedRow = (int) (me.getY() / 34) - 1;
        int clickedColumn = (int) (me.getX() / 34) - 1;
        if (currentEvent.getCurrentEvent()!=null) {
            switch (currentEvent.getCurrentEvent()) {
                case LADYBUG:
                    territory.getLadybug().setCoordinates(clickedRow, clickedColumn);
                    System.out.println(territory.getLadybug().getRow() + "" + territory.getLadybug().getColumn());
                    break;
                case LOG:
                    if (territory.getLadybug().getRow() == clickedRow && territory.getLadybug().getColumn() == clickedColumn) {
                        //Siehe Zeile 188 ff. oder readme.md
                        //playErrorSound();
                    } else {
                        territory.getPlayingField()[clickedRow][clickedColumn].setState(1);
                        System.out.println("Log Updated[" + clickedRow + "][" + clickedColumn + "]");
                    }
                    break;
                case CHERRY:
                    territory.getPlayingField()[clickedRow][clickedColumn].setState(2);
                    System.out.println("Cherry Updated[" + clickedRow + "][" + clickedColumn + "]");
                    break;
                case LEAF:
                    territory.getPlayingField()[clickedRow][clickedColumn].setState(3);
                    System.out.println("LeafUpdated[" + clickedRow + "][" + clickedColumn + "]");
                    break;
                case DELETE:
                    territory.getPlayingField()[clickedRow][clickedColumn].setState(0);
                    System.out.println("Tile cleared [" + clickedRow + "][" + clickedColumn + "]");
                    break;
                case TURNRIGHT:
                    territory.getLadybug().rightTurn();
                    break;
                default:
                    break;
            }
        }
    }

    //Erstellt das Menü, welches mit Rechtsklick auf dem Akteur geöffnet wird
    public MyContextMenu buildMyContextMenu(TerritoryPanel territoryPanel, MouseEvent me){
        int clickedRow = (int) (me.getY() / 34) - 1;
        int clickedColumn = (int) (me.getX() / 34) - 1;
        if (territory.getLadybug().getRow() == clickedRow && territory.getLadybug().getColumn() == clickedColumn) {
            MyContextMenu myContextMenu = new MyContextMenu(territory.getLadybug(), territoryPanel);
            return myContextMenu;
        }
        return null;
    }

    //Mit Martin Knab zusammengearbeitet
    public void safeEditor(TextArea textArea) {
        String editorContent = textArea.getText();
        String prefix =
                "public class JannickLeutbecherSimulator extends simulator.model.Ladybug {\n" +
                        "\n" +
                        "public ";

        String postfix = "\n}";
        String savedContent = prefix + editorContent + postfix;
        try {
            File usedFile = new File("./resources/programme/JannickLeutbecherSimulator.java");
            FileOutputStream fos = new FileOutputStream(usedFile);
            byte[] contentAsBytes = savedContent.getBytes();
            fos.write(contentAsBytes);
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        } catch (IOException e) {
            System.err.println("Error");
        }
    }

    // Mit Dana Warmbold zusammengearbeitet
    //https://stackoverflow.com/questions/40255039/how-to-choose-file-in-java/40255184#40255184
    //https://stackoverflow.com/questions/13516829/jfilechooser-change-default-directory-in-windows
    public String chooseFile() {
        //C:\Benutzer\Jannick Leutbecher\IdeaProjects\JannickLeutbecherSimulator\resources\programme
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("./resources/programme"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Java Files", "java");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        String userProgramm = "";
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    userProgramm += line + "\n";
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found!");
            } catch (IOException e) {
                System.err.println("Something went wrong!");
            }
        }
        int userProgrammStart = userProgramm.indexOf("void main() {");
        int userProgrammEnd = userProgramm.lastIndexOf("}");
        userProgramm = userProgramm.substring(userProgrammStart, userProgrammEnd);
        return userProgramm;
    }

    //Mit Dana Warmbold zusammengearbeitet
    public String getUserPogrammForEditor() {
        String userProgramm = "";
        try (BufferedReader br = new BufferedReader(new FileReader("./resources/programme/JannickLeutbecherSimulator.java"))) {
            String line;
            while ((line = br.readLine()) != null) {
                userProgramm += line + "\n";
            }
            int userProgrammStart = userProgramm.indexOf("void main() {");
            int userProgrammEnd = userProgramm.lastIndexOf("}");
            userProgramm = userProgramm.substring(userProgrammStart, userProgrammEnd);
        } catch (FileNotFoundException e) {
            userProgramm = "void main() {\n\n}";
            System.err.println("File not found!");
        } catch (IOException e) {
            System.err.println("Something went wrong!");
        }
        return userProgramm;
    }

    //https://stackabuse.com/java-check-if-string-is-a-number/
    public boolean isNumeric(String string) {
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            int intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    //Mit Hilfe von Dana Warmbold und mit Martin Knab zusammengearbeitet
    public Ladybug compileUserProgramm() throws CompilerException{
        File root = new File("./resources/programme/");
        File file = new File("./resources/programme/JannickLeutbecherSimulator.java");

        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();


        int compiler = javac.run(null, null, null, file.getPath());
        if (compiler != 0) {
            throw new CompilerException();
        }
        Class cls = null;
        Ladybug userLadybug = null;
        try {
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
            cls = Class.forName("JannickLeutbecherSimulator", true, classLoader);
            userLadybug = (Ladybug) cls.newInstance();
            userLadybug.setTerritory(territory);
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException");
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("ClassCastException");
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return userLadybug;
    }

    /*
    Wirft einen Fehler -> genauer erklärt in readme.md
    Deswegen auskommentiert
    private void playErrorSound() {
        System.err.println("reached the catch");
        String path = "./resources/death.mp3";
        Media soundError = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(soundError);
        mediaPlayer.play();
    }
     */

}
