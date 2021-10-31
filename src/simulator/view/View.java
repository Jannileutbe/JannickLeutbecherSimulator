package simulator.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import simulator.model.Ladybug;
import simulator.model.enums.PossibleEvents;
import simulator.model.Territory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import simulator.model.exceptions.LandedOnLogException;
import simulator.model.exceptions.NotEnoughFuelToFlyException;
import simulator.model.exceptions.RanAgainstWallException;
import simulator.model.exceptions.RanOutsideFieldException;
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
  private final Image imagePlay = new Image(new FileInputStream("./resources/Play24.gif"));
  private final Image imagePause = new Image(new FileInputStream("./resources/Pause24.gif"));
  private final Image imageStop = new Image(new FileInputStream("./resources/Stop24.gif"));

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
    openExistingProjectMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        String userProgramm = viewModel.chooseFile();
        editor.setText(userProgramm);
      }
    });

    MenuItem compileMenuItem = new MenuItem("_Kompilieren");
    compileMenuItem.setAccelerator(
        KeyCombination.keyCombination("SHORTCUT+K")
    );
    compileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        viewModel.safeEditor(editor);
        currentEvent.setCurrentEvent(PossibleEvents.COMPILEFILE);
        Ladybug userLadybug = viewModel.compileUserProgramm();
        territory.setLadybug(userLadybug);
        territoryPanel.buildPlayingField(territory);
      }
    });

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
        new SeparatorMenuItem(), compileMenuItem, new SeparatorMenuItem(),
        quitGameMenuItem);

    return editingMenu;
  }

  private Menu createTerritoryMenu() {
    Menu territoryMenu = new Menu("_Territorium");
    territoryMenu.setMnemonicParsing(true);

    MenuItem saveMenu = new MenuItem("_Speichern");
    saveMenu.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.SAFEFILE);
        viewModel.safeEditor(editor);
        System.out.println(editor.getText());
      }
    });

    MenuItem loadMenu = new MenuItem("_Laden");
    loadMenu.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        String userProgramm = viewModel.chooseFile();
        editor.setText(userProgramm);
      }
    });

    MenuItem changeTerritorySizeMenuItem = new MenuItem("_Größe ändern...");
    changeTerritorySizeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        buildResizeWindow();
      }
    });

    CheckMenuItem placeLadybugMenuItem = new CheckMenuItem("Marienkäfer _platzieren");
    placeLadybugMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.LADYBUG);
      }
    });

    CheckMenuItem placeCherryMenuItem = new CheckMenuItem("_Beere platzieren");
    placeCherryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.CHERRY);
      }
    });

    CheckMenuItem placeLogMenuItem = new CheckMenuItem("_Holz platzieren");
    placeLogMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.LOG);
      }
    });

    CheckMenuItem placeLeafMenuItem = new CheckMenuItem("_Blatt platzieren");
    placeLeafMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.LEAF);
      }
    });

    CheckMenuItem clearTileMenuItem = new CheckMenuItem("_Kachel löschen");
    clearTileMenuItem.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.DELETE);
      }
    });

    territoryMenu.getItems().addAll(saveMenu, loadMenu,
        changeTerritorySizeMenuItem, new SeparatorMenuItem(), placeLadybugMenuItem, placeCherryMenuItem,
        placeLogMenuItem, placeLeafMenuItem, clearTileMenuItem);

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
    newFile.setGraphic(new ImageView(this.imageNew));
    newFile.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.NEWFILE);
      }
    });

    Button openFile = new Button();
    openFile.setGraphic(new ImageView(this.imageOpen));
    openFile.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        String userProgramm = viewModel.chooseFile();
        editor.setText(userProgramm);
      }
    });

    Button safeFile = new Button();
    safeFile.setGraphic(new ImageView(this.imageSafe));
    safeFile.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.SAFEFILE);
        viewModel.safeEditor(editor);
        System.out.println(editor.getText());
      }
    });

    Button compileFile = new Button();
    compileFile.setGraphic(new ImageView(this.imageCompile));
    compileFile.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        viewModel.safeEditor(editor);
        currentEvent.setCurrentEvent(PossibleEvents.COMPILEFILE);
        Ladybug userLadybug = viewModel.compileUserProgramm();
        territory.setLadybug(userLadybug);
        territoryPanel.buildPlayingField(territory);
      }
    });

    Button resizeTerritory = new Button();
    resizeTerritory.setGraphic(new ImageView(this.imageTerrain));
    resizeTerritory.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        buildResizeWindow();
      }
    });

    Button ladybug = new Button();
    ladybug.setGraphic(new ImageView(this.gifLadybugMoving));
    ladybug.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.LADYBUG);
      }
    });

    Button flyingLadybug = new Button();
    flyingLadybug.setGraphic(new ImageView(this.gifLadybugFlying));
    flyingLadybug.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.FLYINGLADYBUG);
        try {
          territory.getLadybug().setAirborne(true);
        } catch(NotEnoughFuelToFlyException e){
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Du hast nicht genug Flug-Energie um jetzt loszufliegen!");
          alert.show();
        }

        territoryPanel.buildPlayingField(territory);
      }
    });

    Button cherry = new Button();
    cherry.setGraphic(new ImageView(this.imageCherry));
    cherry.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.CHERRY);
      }
    });

    Button log = new Button();
    log.setGraphic(new ImageView(this.imageLog));
    log.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.LOG);
      }
    });

    Button leaf = new Button();
    leaf.setGraphic(new ImageView(this.imageLeaf));
    leaf.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.LEAF);
      }
    });

    Button delete = new Button();
    delete.setGraphic(new ImageView(this.imageDelete));
    delete.setMinSize(40, 40);
    delete.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentEvent.setCurrentEvent(PossibleEvents.DELETE);
      }
    });

    Button turnRight = new Button();
    turnRight.setGraphic(new ImageView(this.imageTurnRight));
    turnRight.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        territory.getLadybug().rightTurn();
        territoryPanel.buildPlayingField(territory);
      }
    });

    Button moveForward = new Button();
    moveForward.setGraphic(new ImageView(this.imageMoveForward));
    moveForward.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          territory.getLadybug().moveForward();
        } catch (RanOutsideFieldException e) {
          System.err.println("OutsideField, kurz statt lang");
        } catch (RanAgainstWallException e) {
          System.err.println("AgainstWall, kurz statt lang");
        } catch (LandedOnLogException e) {
          territory.getLadybug().setFruitFuel(0);
          territory.getLadybug().setCoordinates(0, 0);
          territory.getLadybug().setDirection(1);
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Du bist auf einem Baumstumpf gelandet und abgestürzt!");
          alert.show();
        }
        territoryPanel.buildPlayingField(territory);
      }
    });

    Button eatFruit = new Button();
    eatFruit.setGraphic(new ImageView(this.imageEatFruit));
    eatFruit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        territory.getLadybug().eatFruit();
        territoryPanel.buildPlayingField(territory);
      }
    });

    Button pullLeaf = new Button();
    pullLeaf.setGraphic(new ImageView(this.imagePullLeaf));
    pullLeaf.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        territory.getLadybug().pullLeaf();
        territoryPanel.buildPlayingField(territory);
      }
    });

    Label fruitFuelLabel = new Label("FruitFuel: ");
    fruitFuelLabel.minHeight(40);

    // Quelle für das Binding zwischen Button und dem FruitFuel-Wert
    // https://stackoverflow.com/questions/33146167/javafx-binding-label-with-int-value
    Button fruitFuel = new Button("FruitFuel: ");
    fruitFuel.textProperty().bind(territory.getLadybug().getFruitFuel().valueProperty().asString());

    Button start = new Button();
    start.setGraphic(new ImageView(this.imagePlay));
    start.setMinSize(40, 40);
    start.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        territory.getLadybug().main();
        territoryPanel.buildPlayingField(territory);
      }
    });

    Button pause = new Button();
    pause.setGraphic(new ImageView(this.imagePause));
    pause.setMinSize(40, 40);

    Button stop = new Button();
    stop.setGraphic(new ImageView(this.imageStop));
    stop.setMinSize(40, 40);

    Separator separator1 = new Separator();
    Separator separator2 = new Separator();
    Separator separator3 = new Separator();
    Separator separator4 = new Separator();


    toolBar.getItems().addAll(newFile, openFile, safeFile, compileFile, resizeTerritory, separator1, ladybug, flyingLadybug, cherry,
        leaf, log, delete, separator2, turnRight, moveForward, eatFruit, pullLeaf, separator3, fruitFuelLabel, fruitFuel, separator4, start, pause, stop);

    return toolBar;
  }


  //Mit Dana Warmbold zusammengearbeitet
  private void buildResizeWindow() {
    Stage primaryStage = new Stage();

    Label notification = new Label("Gib die neuen Reihen und Zeilen ein!");

    HBox hBoxRows = new HBox();
    hBoxRows.setAlignment(Pos.CENTER);

    Label newRowsLabel = new Label("     New Rows: ");
    TextField newRowsTextField = new TextField("");
    newRowsTextField.setMaxWidth(100);

    hBoxRows.getChildren().addAll(newRowsLabel, newRowsTextField);


    HBox hBoxColumns = new HBox();
    hBoxColumns.setAlignment(Pos.CENTER);

    Label newColumnLabel = new Label("New Columns: ");
    TextField newColumnsTextField = new TextField("");
    newColumnsTextField.setMaxWidth(100);

    hBoxColumns.getChildren().addAll(newColumnLabel, newColumnsTextField);


    HBox hBoxButtons = new HBox();
    hBoxButtons.setAlignment(Pos.CENTER);

    Button ok = new Button("OK");
    ok.setMinWidth(50);
    ok.setDisable(true);
    setButtonListener(notification, newRowsTextField, newColumnsTextField, ok);
    setButtonListener(notification, newColumnsTextField, newRowsTextField, ok);
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
    root.getChildren().addAll(notification, hBoxRows, hBoxColumns, hBoxButtons);
    Scene scene = new Scene(root);
    primaryStage.setResizable(false);
    primaryStage.setTitle("Resize");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  private void setButtonListener(Label notification, TextField newRowsTextField, TextField newColumnsTextField, Button ok) {
    newRowsTextField.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (viewModel.isNumeric(newRowsTextField.getText()) && viewModel.isNumeric(newColumnsTextField.getText())) {
          if (Integer.parseInt(newRowsTextField.getText()) < 1 || Integer.parseInt(newColumnsTextField.getText()) < 1) {
            notification.setText("Keine Nullen oder negative Zahlen!");
            ok.setDisable(true);
          } else {
            notification.setText("Gib die neuen Reihen und Zeilen ein!");
            ok.setDisable(false);
          }
        } else {
          if (!newColumnsTextField.getText().equals("")) {
            notification.setText("Keine Buchstaben, nur Zahlen!");
          }
          ok.setDisable(true);
        }
      }
    });
  }

  private SplitPane addSplitPane() throws FileNotFoundException {
    SplitPane splitPane = new SplitPane();
    TextArea editor = new TextArea(viewModel.getUserPogrammForEditor());
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