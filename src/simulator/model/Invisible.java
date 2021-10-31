package simulator.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//Markierung, um bestimmte Mathoden zu markieren, damit sie nicht im Kontextmenü angezeigt werden
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invisible {}
