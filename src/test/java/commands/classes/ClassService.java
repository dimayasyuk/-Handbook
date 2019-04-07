package commands.classes;

import entities.Class;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class ClassService {
    public static List<Class> initClasses() {
        List<Class> initialClasses = new LinkedList<>();
        Date date = new Date(System.currentTimeMillis());
        Class firstClass = new Class(1,"Tree", "class", date, date, 1, 1);
        Class secondClass = new Class(2,"Leaf", "class", date, date, 1, 1);

        initialClasses.add(firstClass);
        initialClasses.add(secondClass);
        return initialClasses;
    }
}
