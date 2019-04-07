package entities;

public class Section {
    private int id;
    private String name;
    private String description;

    public Section() {
    }

    public Section(int id) {
        this.id = id;
    }

    public Section(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Section(String name, String description) {
        this.name = name;
        this.description = description;
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
}
