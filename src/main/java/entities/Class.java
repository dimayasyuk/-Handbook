package entities;

import java.sql.Date;

public class Class {
    private int id;
    private String name;
    private String description;
    private Date created;
    private Date modified;
    private int modifierId;

    public Class() {
    }

    public Class(int id) {
        this.id = id;
    }

    public Class(String name, String description, Date created, Date modified, int modifierId) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.modified = modified;
        this.modifierId = modifierId;
    }

    public Class(int id, String name, String description, Date created, Date modified, int modifierId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.modified = modified;
        this.modifierId = modifierId;
    }

    public String getName() {
        return name;
    }

    public int getModifierId() {
        return modifierId;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }


}
