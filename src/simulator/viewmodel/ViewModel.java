package simulator.viewmodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import simulator.model.CurrentEvent;
import simulator.model.Ladybug;
import simulator.model.Territory;
import simulator.view.View;

public class ViewModel {


    private final Territory territory;
    private final CurrentEvent currentEvent;

    public ViewModel(Stage stage) throws FileNotFoundException {
        this.territory = new Territory();
        this.currentEvent = new CurrentEvent();
        View view = new View(territory, this, currentEvent, stage);
    }

    public void handleEvent(MouseEvent me) {
        int row = (int) (me.getY() / 34) - 1;
        int column = (int) (me.getX() / 34) - 1;

        switch (currentEvent.getCurrentEvent()) {
            case LADYBUG:
                territory.getLadybug().setCoordinates(row, column);
                System.out.println(territory.getLadybug().getRow() + "" + territory.getLadybug().getColumn());
                break;
            case LOG:
                territory.getPlayingField()[row][column].setState(1);
                System.out.println("Log Updated[" + row + "][" + column + "]");
                break;
            case CHERRY:
                territory.getPlayingField()[row][column].setState(2);
                System.out.println("Cherry Updated[" + row + "][" + column + "]");
                break;
            case LEAF:
                territory.getPlayingField()[row][column].setState(3);
                System.out.println("LeafUpdated[" + row + "][" + column + "]");
                break;
            case DELETE:
                territory.getPlayingField()[row][column].setState(0);
                System.out.println("Tile cleared [" + row + "][" + column + "]");
                break;
            case TURNRIGHT:
                territory.getLadybug().rightTurn();
                break;
            default:
                break;
        }
    }


    //Mit Martin Knab zusammengearbeitet
    public void safeEditor(TextArea textArea) {
        String editorContent = textArea.getText();

        String prefix =
                "public class JannickLeutbecherSimulator extends simulator.model.Ladybug {\n" +
                        "\n" +
                        "public ";
        //String prefix = "public class JannickLeutbecherSimulator extends Ladybug { \npublic ";
        String postfix = "\n}";
        String savedContent = prefix + editorContent + postfix;
        byte[] bytes = savedContent.getBytes(StandardCharsets.UTF_8);

        String savedContentUTF8 = new String(bytes, StandardCharsets.UTF_8);

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

    /*
    String editorContent = textArea.getText();
    String prefix = "public class JannickLeutbecherSimulator extends Ladybug { public";
    String postfix = "}";
    String savedContent = prefix + editorContent + postfix;
    File savedContentAsFile = new File("./resources/programme/JannickLeutbecherSimulator.java");
    sa
    vedContentAsFile.mkdirs();
     */

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

    // Mit Dana Warmbold zusammengearbeitet
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

  /* checken obs gebarucht wird...
  public String getUserProgramm() {
    String userProgramm = "";
    try (BufferedReader br = new BufferedReader(new FileReader("./resources/programme/JannickLeutbecherSimulator.java"))) {
      String line;
      while ((line = br.readLine()) != null) {
        userProgramm += line + "\n";
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found!");
    } catch (IOException e) {
      System.err.println("Something went wrong!");
    }
    return userProgramm;
  }

   */

    //https://stackabuse.com/java-check-if-string-is-a-number/
    public boolean isNumeric(String string) {
        int intValue;

        if (string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public Ladybug compileUserProgramm() {
        //String code = getUserProgramm();
        File root = new File("./resources/programme/");
        File file = new File("./resources/programme/JannickLeutbecherSimulator.java");


        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

        javac.run(null, null, null, file.getPath());
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
}
