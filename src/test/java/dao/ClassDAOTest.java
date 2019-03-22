package dao;

import de.saxsys.javafx.test.JfxRunner;
import entities.Class;
import entities.Modifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JfxRunner.class)
public class ClassDAOTest {
    private ClassDAO classDAO;
    private ModifierDAO modifierDAO;
    private String jdbcURL = "jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC";
    private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private String jdbcUsername = "root";
    private String jdbcPassword = "12345";


    @Before
    public void setServerSocket() {
        classDAO = new ClassDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        modifierDAO = new ModifierDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Before
    @After
    public void cleanDatabase() throws IOException, SQLException {
        classDAO.cleanDatabase();
        modifierDAO.cleanDatabase();
    }

    @Test
    public void insertClass() throws IOException, SQLException {
        Modifier modifier = new Modifier("private",
                "The type or member can be accessed only by code in the same class or struct.",
                new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        assertTrue(modifierDAO.insertModifier(modifier));

        int modifierId = modifierDAO.listAllModifiers().get(0).getId();

        Class cls = new Class("Tree", "class", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), modifierId);
        assertTrue(classDAO.insertClass(cls));
    }

    @Test
    public void deleteClass() throws IOException, SQLException {
        insertClass();
        Class cls = classDAO.listAllClasses().get(0);
        assertNotNull(cls);
        assertTrue(classDAO.deleteClass(cls));
    }

    @Test
    public void updateClass() throws IOException, SQLException {
        insertClass();
        Class cls = classDAO.listAllClasses().get(0);
        assertNotNull(cls);
        Class newClass = new Class(cls.getId(), "Leaf", "class", cls.getCreated(), new Date(System.currentTimeMillis()),
                cls.getModifierId());
        assertTrue(classDAO.updateClass(newClass));
    }

    @Test
    public void getClassById() throws IOException, SQLException {
        insertClass();
        Class cls = classDAO.listAllClasses().get(0);
        assertNotNull(cls);
        Class newClass = classDAO.getClassById(cls.getId());
        assertNotNull(newClass);
        assertEquals(cls.getId(), newClass.getId());
    }

    @Test
    public void listAllClasses() throws IOException, SQLException {
        insertClass();
        Class cls = classDAO.listAllClasses().get(0);

        Class newClass = new Class("Leaf", "class", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),
                cls.getModifierId());
        Class aClass = new Class("Leaf", "class", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()),
                cls.getModifierId());

        classDAO.insertClass(newClass);
        classDAO.insertClass(aClass);

        List<Class> classList = classDAO.listAllClasses();
        assertNotNull(classList);
        assertEquals(classList.size(), 3);

    }
}