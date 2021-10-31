package simulator.view;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import simulator.model.Invisible;
import simulator.model.Ladybug;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class MyContextMenu extends ContextMenu {

    //Mit Dana Warmbold zusammengearbeitet
    public MyContextMenu(Ladybug ladybug, TerritoryPanel territoryPanel) {

        //Methoden werden dem Array hinzugefügt um dann im Kontextmenü angezeigt zu werden
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : ladybug.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers()) &&
                    !Modifier.isStatic(method.getModifiers()) && method.getAnnotation(Invisible.class) == null) {
                methods.add(method);
            }
        }

        //Wenn die Superklasse von ladybug nicht die Object-Klasse ist, dann werden auch die Methoden der Superklasse im
        //Kontextmenü angezeigt
        //Dies ist dann der Fall, wenn die Klasse des Nutzers aus dem Editor kompiliert wurde
        if (ladybug.getClass().getSuperclass() != Object.class) {
            for (Method method : ladybug.getClass().getSuperclass().getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers()) &&
                        !Modifier.isStatic(method.getModifiers()) && method.getAnnotation(Invisible.class) == null) {
                    methods.add(method);
                }
            }
        }

        //Alle zuvor gesammelten Methoden werden im Kontextmenü angegeben
        for (Method method : methods) {
            MenuItem menuItem = new MenuItem(method.getName());
            menuItem.setOnAction(event -> {
                try {
                    method.invoke(ladybug);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                territoryPanel.buildPlayingField(ladybug.getTerritory());
            });
            this.getItems().add(menuItem);
        }
    }

}
