package commands.section;

import entities.Section;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModifierService {

    public static List<Section> initSections() {
        List<Section> initialSections = new ArrayList<>();
        Section firstSection = new Section(1, "Operators",
                "The type or member can be accessed only by code in the same class or struct.");
        Section secondSection = new Section(2, "Key Words",
                "The type or member can be accessed only by code in the same class, or in a class that is derived from that class.");
        initialSections.add(firstSection);
        initialSections.add(secondSection);
        return initialSections;
    }
}
