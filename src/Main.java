import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;



//Zur Hilfe wurden die Folien aus dem StudIP genommen


public class Main extends Application {

    private final Image imageNew = new Image(new FileInputStream("./resources/New24.gif"));
    private final Image imageOpen = new Image(new FileInputStream("./resources/Open24.gif"));
    private final Image imageSafe = new Image(new FileInputStream("./resources/Save24.gif"));
    private final Image imageCompile = new Image(new FileInputStream("./resources/Compile24.gif"));
    private final Image imageTerrain = new Image(new FileInputStream("./resources/Terrain24.gif"));
    private final Image imageLadybugMoving = new Image(new FileInputStream("./resources/LadybugAdventure/LadybugGIF.gif"));
    private final Image imageCherry = new Image(new FileInputStream("./resources/LadybugAdventure/Cherry.png"));
    private final Image imageLog = new Image(new FileInputStream("./resources/LadybugAdventure/Log.png"));
    private final Image imageDelete = new Image(new FileInputStream("./resources/Delete24.gif"));

    private Territory territory;

    public CurrentEvent currentEvent;

    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        this.territory = new Territory();
        VBox gameWindow = createGameWindow();
        StackPane root = new StackPane();
        root.getChildren().add(gameWindow);
        Scene scene = new Scene(root, 1800, 1000);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setTitle("Ladybug Adventure!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createGameWindow() throws FileNotFoundException {
        VBox vBox = new VBox();

        MenuBar menuBar = addMenuBar();
        ToolBar toolBar = addToolBar();
        SplitPane splitPane = addSplitPane();
        Label label = new Label("Welcome to the Adventure!");
        vBox.getChildren().addAll(menuBar, toolBar, splitPane, label);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        return vBox;
    }

    private MenuBar addMenuBar(){
        MenuBar menuBar = new MenuBar();

        Menu editingMenu = createEditingMenu();

        Menu territoryMenu = createTerritoryMenu();

        Menu ladybugMenu = createLadybugMenu();

        Menu simulationMenu = createSimulationMenu();

        menuBar.getMenus().addAll(editingMenu, territoryMenu, ladybugMenu, simulationMenu);

        return menuBar;
    }

    private Menu createEditingMenu(){
        Menu editingMenu = new Menu("_Editor");
        editingMenu.setMnemonicParsing(true);

        MenuItem openNewProjectMenuItem = new MenuItem("_Neu");
        openNewProjectMenuItem.setMnemonicParsing(true);

        //Zum Test, ob die Mnemonics funktionieren
        openNewProjectMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Neues Projekt wird erstellt");
            }
        });
        openNewProjectMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+N")
        );

        MenuItem openExistingProjectMenuItem = new MenuItem("_Öffnen");
        openExistingProjectMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+O")
        );

        MenuItem compileMenuItem = new MenuItem("_Kompilieren");
        compileMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+K")
        );

        MenuItem printMenuItem = new MenuItem("_Drucken");
        printMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+D")
        );

        MenuItem quitGameMenuItem = new MenuItem("_Beenden");
        quitGameMenuItem.setOnAction(e -> Platform.exit());
        quitGameMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+Q")
        );

        editingMenu.getItems().addAll(openNewProjectMenuItem, openExistingProjectMenuItem,
                new SeparatorMenuItem(), compileMenuItem, printMenuItem, new SeparatorMenuItem(),
                quitGameMenuItem);

        return editingMenu;
    }

    private Menu createTerritoryMenu(){
        Menu territoryMenu = new Menu("_Territorium");
        territoryMenu.setMnemonicParsing(true);

        Menu saveSubMenu = new Menu("_Speichern");
        saveSubMenu.getItems().addAll(
                new MenuItem("XML"), new MenuItem("JAXB"), new MenuItem("Serialisieren")
        );

        Menu loadSubMenu = new Menu("_Laden");
        loadSubMenu.getItems().addAll(
                new MenuItem("Von XML"), new MenuItem("Von _JAXB")
        );

        Menu saveAsPictureSubMenu = new Menu("Als _Bild speichern");
        saveAsPictureSubMenu.getItems().addAll(
                new MenuItem("PNG"), new MenuItem("JPEG")
        );

        MenuItem printTerritoryMenuItem = new MenuItem("_Drucken");

        MenuItem changeTerritorySizeMenuItem = new MenuItem("_Größe ändern...");

        CheckMenuItem placeLadybugMenuItem = new CheckMenuItem("Marienkäfer _platzieren");

        CheckMenuItem placeBerryMenuItem = new CheckMenuItem("_Beere platzieren");

        CheckMenuItem placeWallMenuItem = new CheckMenuItem("_Holz platzieren");

        CheckMenuItem placeLeaveMenuItem = new CheckMenuItem("_Blatt platzieren");

        CheckMenuItem clearTileMenuItem = new CheckMenuItem("_Kachel löschen");

        territoryMenu.getItems().addAll(saveSubMenu, loadSubMenu, saveAsPictureSubMenu, printTerritoryMenuItem,
                changeTerritorySizeMenuItem, new SeparatorMenuItem(), placeLadybugMenuItem, placeBerryMenuItem,
                placeWallMenuItem, placeLeaveMenuItem, clearTileMenuItem);

        return territoryMenu;
    }

    private Menu createLadybugMenu(){
        Menu ladybugMenu = new Menu("_Ladybug");
        ladybugMenu.setMnemonicParsing(true);

        MenuItem berryInMouthMenuItem = new MenuItem("Beeren im _Maul...");

        MenuItem leftTurnMenuItem = new MenuItem("_linksUm");
        leftTurnMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+SHIFT+L")
        );

        MenuItem moveMenuItem = new MenuItem("_vor");
        moveMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+SHIFT+V")
        );

        MenuItem pushMenuItem = new MenuItem("_schieb");
        pushMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+SHIFT+S")
        );

        MenuItem pullMenuItem = new MenuItem("_zieh");
        pullMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+SHIFT+Z")
        );

        MenuItem takeMenuItem = new MenuItem("_nimm");
        takeMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+SHIFT+N")
        );

        MenuItem giveMenuItem = new MenuItem("_gib");
        giveMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+SHIFT+G")
        );

        ladybugMenu.getItems().addAll(berryInMouthMenuItem, leftTurnMenuItem, moveMenuItem,
                pushMenuItem, pullMenuItem, takeMenuItem, giveMenuItem);

        return ladybugMenu;
    }

    private Menu createSimulationMenu() {
        Menu simulationMenu = new Menu("_Simulation");
        simulationMenu.setMnemonicParsing(true);

        MenuItem startContinueMenuItem = new MenuItem("Start/_Fortsetzen");
        startContinueMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+F11")
        );
        MenuItem pauseMenuItem = new MenuItem("_Pause");

        MenuItem stopMenuItem = new MenuItem("Sto_pp");
        stopMenuItem.setAccelerator(
                KeyCombination.keyCombination("SHORTCUT+F12")
        );

        simulationMenu.getItems().addAll(startContinueMenuItem, pauseMenuItem, stopMenuItem);

        return simulationMenu;
    }

    private ToolBar addToolBar(){
        ToolBar toolBar = new ToolBar();

        Button newFile = new Button();
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.NEWFILE);
            }
        });
        newFile.setGraphic(new ImageView(this.imageNew));

        Button openFile = new Button();
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.OPENFILE);
            }
        });
        openFile.setGraphic(new ImageView(this.imageOpen));

        Button safeFile = new Button();
        safeFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.SAFEFILE);
            }
        });
        safeFile.setGraphic(new ImageView(this.imageSafe));

        Button compileFile = new Button();
        compileFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.COMPILEFILE);
            }
        });
        compileFile.setGraphic(new ImageView(this.imageCompile));

        Button resizeTerritory = new Button();
        resizeTerritory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.RESIZETERRITORY);
            }
        });
        resizeTerritory.setGraphic(new ImageView(this.imageTerrain));

        Button ladybug = new Button();
        ladybug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.LADYBUG);
            }
        });
        ladybug.setGraphic(new ImageView(this.imageLadybugMoving));

        Button cherry = new Button();
        cherry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.CHERRY);
            }
        });
        cherry.setGraphic(new ImageView(this.imageCherry));

        Button log = new Button();
        log.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.LOG);
            }
        });
        log.setGraphic(new ImageView(this.imageLog));

        Button delete = new Button();
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentEvent.setCurrentEvent(PossibleEvents.DELETE);
            }
        });
        delete.setGraphic(new ImageView(this.imageDelete));

        toolBar.getItems().addAll(newFile, openFile, safeFile, compileFile,
                resizeTerritory, ladybug, cherry, log, delete);

        return toolBar;
    }

    private SplitPane addSplitPane() throws FileNotFoundException {
        SplitPane splitPane = new SplitPane();

        TextArea leftControl = new TextArea();
        TerritoryPanel territoryPanel = new TerritoryPanel(this.territory, currentEvent);

        splitPane.getItems().addAll(leftControl, territoryPanel);

        return splitPane;
    }

    public CurrentEvent getCurrentEvent() {
        return currentEvent;
    }
}
