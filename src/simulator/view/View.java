package simulator.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import simulator.model.CurrentEvent;
import simulator.model.enums.PossibleEvents;
import simulator.model.Territory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import simulator.viewmodel.ViewModel;


//Zur Hilfe wurden die Folien aus dem StudIP genommen


public class View extends Application {

  private final Image imageNew = new Image(new FileInputStream("./resources/New24.gif"));
  private final Image imageOpen = new Image(new FileInputStream("./resources/Open24.gif"));
  private final Image imageSafe = new Image(new FileInputStream("./resources/Save24.gif"));
  private final Image imageCompile = new Image(new FileInputStream("./resources/Compile24.gif"));
  private final Image imageTerrain = new Image(new FileInputStream("./resources/Terrain24.gif"));
  private final Image gifLadybugMoving = new Image(new FileInputStream("./resources/LadybugAdventure/LadybugGIF.gif"));
  private final Image gifLadybugFlying = new Image(new FileInputStream("./resources/LadybugAdventure/FlyingLadybugGIF.gif"));
  private final Image imageCherry = new Image(new FileInputStream("./resources/LadybugAdventure/Cherry.png"));
  private final Image imageLog = new Image(new FileInputStream("./resources/LadybugAdventure/Log.png"));
  private final Image imageLeaf = new Image(new FileInputStream("./resources/LadybugAdventure/Leaf.png"));
  private final Image imageDelete = new Image(new FileInputStream("./resources/Delete24.gif"));
  private final Image imageTurnRight = new Image(new FileInputStream("./resources/LadybugAdventure/RightTurnLadybug.png"));
  private final Image imageMoveForward = new Image(new FileInputStream("./resources/LadybugAdventure/MoveForwardLadybug.png"));
  private final Image imageEatFruit = new Image(new FileInputStream("./resources/LadybugAdventure/LadybugEatFruit.png"));
  private final Image imagePullLeaf = new Image(new FileInputStream("./resources/LadybugAdventure/PullLeaf.png"));

  private final Territory territory;
  private final ViewModel viewModel;
  private final CurrentEvent currentEvent;

  private TerritoryPanel territoryPanel;
  private TextArea editor;

  public View(Territory territory, ViewModel viewModel, CurrentEvent currentEvent, Stage stage) throws FileNotFoundException {
    this.viewModel = viewModel;
    this.territory = territory;
    this.currentEvent = currentEvent;
    this.editor = this.getEditor();
    this.start(stage);
  }

  @Override
  public void start(Stage primaryStage) throws FileNotFoundException {
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

  private MenuBar addMenuBar() {
    MenuBar menuBar = new MenuBar();

    Menu editingMenu = createEditingMenu();

    Menu territoryMenu = createTerritoryMenu();

    Menu ladybugMenu = createLadybugMenu();

    Menu simulationMenu = createSimulationMenu();

    menuBar.getMenus().addAll(editingMenu, territoryMenu, ladybugMenu, simulationMenu);

    return menuBar;
  }

  private Menu createEditingMenu() {
    Menu editingMenu = new Menu("_Editor");
    editingMenu.setMnemonicParsing(true);

    MenuItem openNewProjectMenuItem = new MenuItem("_Neu");
    openNewProjectMenuItem.setMnemonicParsing(true);
    openNewProjectMenuItem.setAccelerator(
        KeyCombination.keyCombination("SHORTCUT+N")
    );
    openNewProjectMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Stage stage = new Stage();
          ViewModel viewModel = new ViewModel(stage);
        } catch (IOException e) {
          System.err.println("Couldn't open new gamewindow");
        }
      }
    });

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
    quitGameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        viewModel.safeEditor(editor);
        System.exit(0);
      }
    });

    editingMenu.getItems().addAll(openNewProjectMenuItem, openExistingProjectMenuItem,
        new SeparatorMenuItem(), compileMenuItem, printMenuItem, new SeparatorMenuItem(),
        quitGameMenuItem);

    return editingMenu;
  }

  private Menu createTerritoryMenu() {
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

  private Menu createLadybugMenu() {
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

  private ToolBar addToolBar() {
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
        viewModel.safeEditor(editor);
        System.out.println(editor.getText());
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
        buildResizeWindow();
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
    ladybug.setGraphic(new ImageView(this.gifLadybugMoving));

    Button flayingLadybug = new Button();
    flayingLadybug.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.FLYINGLADYBUG);
      }
    });
    flayingLadybug.setGraphic(new ImageView(this.gifLadybugFlying));

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

    Button leaf = new Button();
    leaf.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.LEAF);
      }
    });
    leaf.setGraphic(new ImageView(this.imageLeaf));

    Button delete = new Button();
    delete.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.DELETE);
      }
    });
    delete.setGraphic(new ImageView(this.imageDelete));
    delete.setMinSize(40, 40);

    Button turnRight = new Button();
    turnRight.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        territory.getLadybug().rightTurn();
        territoryPanel.buildPlayingField(territory);
      }
    });
    turnRight.setGraphic(new ImageView(this.imageTurnRight));

    Button moveForward = new Button();
    moveForward.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        territory.getLadybug().moveForward();
        territoryPanel.buildPlayingField(territory);
      }
    });
    moveForward.setGraphic(new ImageView(this.imageMoveForward));

    Button eatFruit = new Button();
    eatFruit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        territory.getLadybug().eatFruit();
        territoryPanel.buildPlayingField(territory);
      }
    });
    eatFruit.setGraphic(new ImageView(this.imageEatFruit));

    Button pullLeaf = new Button();
    pullLeaf.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        territory.getLadybug().pullLeaf();
        territoryPanel.buildPlayingField(territory);
      }
    });
    pullLeaf.setGraphic(new ImageView(this.imagePullLeaf));

    Label fruitFuelLabel = new Label("FruitFuel: ");
    fruitFuelLabel.minHeight(40);

    // Quelle für das Binding zwischen Button und dem FruitFuel-Wert
    // https://stackoverflow.com/questions/33146167/javafx-binding-label-with-int-value
    Button fruitFuel = new Button("FruitFuel: ");
    fruitFuel.textProperty().bind(territory.getLadybug().getFruitFuel().valueProperty().asString());

    toolBar.getItems().addAll(newFile, openFile, safeFile, compileFile, resizeTerritory, ladybug, flayingLadybug, cherry,
        leaf, log, delete, turnRight, moveForward, eatFruit, pullLeaf, fruitFuelLabel, fruitFuel);

    return toolBar;
  }

  private void buildResizeWindow() {
    Stage primaryStage = new Stage();

    HBox hBoxRows = new HBox();
    hBoxRows.setAlignment(Pos.CENTER);

    Label newRowsLabel = new Label("    New Rows: ");
    TextField newRowsTextField = new TextField();
    newRowsTextField.setMaxWidth(100);

    hBoxRows.getChildren().addAll(newRowsLabel, newRowsTextField);


    HBox hBoxColumns = new HBox();
    hBoxColumns.setAlignment(Pos.CENTER);

    Label newColumnLabel = new Label("New Columns: ");
    TextField newColumnsTextField = new TextField();
    newColumnsTextField.setMaxWidth(100);

    hBoxColumns.getChildren().addAll(newColumnLabel, newColumnsTextField);


    HBox hBoxButtons = new HBox();
    hBoxButtons.setAlignment(Pos.CENTER);

    Button ok = new Button("OK");
    ok.setMinWidth(50);
    ok.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (viewModel.isNumeric(newRowsTextField.getText()) && viewModel.isNumeric(newColumnsTextField.getText())) {
          int newRows = Integer.parseInt(newRowsTextField.getText());
          int newColumns = Integer.parseInt(newColumnsTextField.getText());
          territory.resizeTerritory(newRows, newColumns);
          territoryPanel.buildPlayingField(territory);
        }
        primaryStage.close();
      }
    });

    Button abbrechen = new Button("Abbrechen");
    abbrechen.setMinWidth(50);
    abbrechen.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        primaryStage.close();
      }
    });

    hBoxButtons.setSpacing(20);
    hBoxButtons.getChildren().addAll(ok, abbrechen);

    VBox root = new VBox(10);
    root.setPadding(new Insets(20, 20, 20, 20));
    root.getChildren().addAll(hBoxRows, hBoxColumns, hBoxButtons);
    Scene scene = new Scene(root);
    primaryStage.setResizable(false);
    primaryStage.setTitle("Resize");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  private SplitPane addSplitPane() throws FileNotFoundException {
    SplitPane splitPane = new SplitPane();
    TextArea editor = new TextArea("void main() { \n \n}");
    this.editor = editor;
    TerritoryPanel territoryPanel = new TerritoryPanel(this.territory, viewModel);
    this.territoryPanel = territoryPanel;

    splitPane.getItems().addAll(editor, territoryPanel);

    return splitPane;
  }

  public TextArea getEditor() {
        return this.editor;
  }
}