package dao;

import de.saxsys.javafx.test.JfxRunner;
import entities.Modifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class ModifierDAOTest {
    private ModifierDAO modifierDAO;
    private String jdbcURL = "jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC";
    private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private String jdbcUsername = "root";
    private String jdbcPassword = "12345";

    @Before
    @After
    public void cleanDatabase() throws IOException, SQLException {
        modifierDAO.cleanDatabase();
    }

    @Before
    public void init() {
        modifierDAO = new ModifierDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Test
    public void insertModifier() throws IOException, SQLException {
        Modifier modifier = new Modifier("private",
                "The type or member can be accessed only by code in the same class or struct.",
                new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        assertTrue(modifierDAO.insertModifier(modifier));
    }

    @Test
    public void listAllModifiers() throws IOException, SQLException {
        Modifier modifier = new Modifier("private", "The type or member can be accessed only by code in the same class or struct.",
                new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        Modifier modifier1 = new Modifier("protected", "The type or member can be accessed only by code in the same class, or in a class that is derived from that class.",
                new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));

        modifierDAO.insertModifier(modifier);
        modifierDAO.insertModifier(modifier1);

        List<Modifier> responseModifiers = modifierDAO.listAllModifiers();

        assertNotNull(responseModifiers);
        assertEquals(responseModifiers.size(), 2);
    }


    @Test
    public void deleteModifier() throws IOException, SQLException {
        insertModifier();
        Modifier modifier = modifierDAO.listAllModifiers().get(0);

        assertTrue(modifierDAO.deleteModifier(modifier));
    }

    @Test
    public void updateModifier() throws IOException, SQLException {
        insertModifier();
        Modifier modifier = modifierDAO.listAllModifiers().get(0);
        Modifier newModifier = new Modifier(modifier.getId(), "protected",
                "The type or member can be accessed only by code in the same class, or in a class that is derived from that class.",
                modifier.getCreated(), new Date(System.currentTimeMillis()));
        assertTrue(modifierDAO.updateModifier(newModifier));
    }

    @Test
    public void getModifier() throws IOException, SQLException {
        insertModifier();
        Modifier modifier = modifierDAO.listAllModifiers().get(0);
        Modifier newModifier = modifierDAO.getModifier(modifier.getId());

        assertNotNull(newModifier);
        assertEquals(modifier.getId(), newModifier.getId());
    }
}