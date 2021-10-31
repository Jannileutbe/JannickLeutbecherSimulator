Das Ladybug-Adventure

1. Die Landschaft

Der Merienkäfer läuft durch das Unterholz eines Waldes.
Dort gibt es Kirschen, welche der Marienkäfer sucht.
Kirschen können auf dem Boden oder unter Blättern liegen.
Die Blätter können vom Marienkäfer gezogen werden, um die 
darunter liegende Kirsche freizulegen. Auf dem Boden liegen 
auch HolzStämme herum, welche der Merienkäfer nicht 
bewegen kann. Um diese muss herumnavigiert werden.

2. Marienkäfer

Der Marienkäfer kann sich in vier Richtungen drehen und befindet sich 
immer als einziges Lebewesen auf dem Spielfeld. Wenn der Marienkäfer 
eine Kirsche isst, erhöht sich seine Flug-Energie. Früchte lassen
sich auf dem Boden oder unter Blättern finden.

3. Befehle

rightTurn() Der Marienkäfer dreht sich nach rechts 

moveForward() Der Marienkäfer bewegt sich ein Feld nach vorne

pullLeaf() Der Marienkäfer zieht ein vor sich liegendes Blatt nach hinten
und bewegt sich selber ein Feld zurück

eatFruit() Der Marienkäfer nimmt eine Beere in seinen Mund

changeIsAirborne() Der Marienkäfer fliegt hoch oder landet wieder auf dem Boden

4. Spielablauf
Der Marienköfer läuft durch das Feld und kann Kirschen essen. Damit erhöht sich
seine Flug-Energie. Der Marienkäfer kann hochfliegen und sich in der luft bewegen,
während der Mariekäfer fliegt, kann dieser über Holzstämme drüber fliegen. Alle 
anderen Fähigkeiten, wie das Essen von Kirschen oder Ziehen von Blättern sind
nicht möglich in der Luft.
Wenn der Marienkäfer landet, geht dies nur auf einem begehbaren Feld, sonst stürzt
dieser ab und wird zurückgesetzt auf das 0/0 Feld.
Wenn der Marienkäfer zurückgesetzt wird verliert dieser außerdem all seine bisher 
gesammelte Flug-Energie. Dies passiert auch wenn der Marienkäfer gegen eine Wand läuft.
   

Was nicht funkioniert:

- Die Drag-Operation für den Akteur funktioniert nicht.

- Es kann nur ein Programm gespeichert werden.

- Bei Kompilierfehlern öffnet sich kein Alert
  (Ich habe versucht das einzubauen, aber egal welche Exceptions ich gefangen habe, es wird 
  immer auf der Konsole der Compilierfehler angezeigt, anstatt das sich ein Alert öffnet)
  
- Das PopUp-Menü taucht nicht genau auf dem Akteur auf

- Der Warnsound funktioniert nicht, da es einen Fehler mit der Coretto-JDK-1.8 gibt
    die Methode welche den Sound abspielen sollte befindet sich auskommentiert in meinem
    ViewModel. Der genaue Fehler lautet:
    Exception in thread "JavaFX Application Thread" java.lang.UnsatisfiedLinkError: Can't load library: C:\Users\Jannick\.jdks\corretto-1.8.0_292\jre\bin\glib-lite.dll
  
- Aufgabe 11 und 12 von Aufgabenblatt 6 funktionieren beide nicht.