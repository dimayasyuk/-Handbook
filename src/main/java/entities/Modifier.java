package entities;

import java.sql.Date;

public class Modifier {
    private int id;
    private String name;
    private String description;
    private Date created;
    private Date modified;

    public Modifier() {
    }

    public Modifier(int id) {
        this.id = id;
    }

    public Modifier(String name, String description, Date created, Date modified) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.modified = modified;
    }

    public Modifier(int id, String name, String description, Date created, Date modified) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.modified = modified;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }
}
