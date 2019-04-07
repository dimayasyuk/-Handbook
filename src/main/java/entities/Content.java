package entities;

public class Content {
    private int id;
    private String text;

    public Content() {
        this.text = "";
    }

    public Content(String text) {
        this.text = text;
    }

    public Content(int id, String text) {
        this.id = id;
        this.text = text;
    }


    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
