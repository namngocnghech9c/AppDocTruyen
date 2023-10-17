package namtdph08817.android.appdoctruyen.models;

public class ChapterModel {
    private String _id, idTruyen, tenChap, noiDung;

    public ChapterModel() {
    }

    public ChapterModel(String _id, String idTruyen, String tenChap, String noiDung) {
        this._id = _id;
        this.idTruyen = idTruyen;
        this.tenChap = tenChap;
        this.noiDung = noiDung;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
