package commands.modifier;

import entities.Modifier;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class ModifierService {

    public static List<Modifier> initModifiers() {
        List<Modifier> initialModifiers = new LinkedList<>();
        Date date = new Date(System.currentTimeMillis());
        Modifier firstModifier = new Modifier(1, "private",
                "The type or member can be accessed only by code in the same class or struct.", date,
                date);
        Modifier secondModifier = new Modifier(2, "protected",
                "The type or member can be accessed only by code in the same class, or in a class that is derived from that class.",
                date, date);
        initialModifiers.add(firstModifier);
        initialModifiers.add(secondModifier);
        return initialModifiers;
    }
}
