package namtdph08817.android.appdoctruyen.models;

public class ChapterID {
    private int id;
    private String idChap;

    public ChapterID() {
    }

    public ChapterID(int id, String idChap) {
        this.id = id;
        this.idChap = idChap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdChap() {
        return idChap;
    }

    public void setIdChap(String idChap) {
        this.idChap = idChap;
    }
}
