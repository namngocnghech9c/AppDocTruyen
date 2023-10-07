package namtdph08817.android.appdoctruyen.models;

public class TheLoaiModel {
    private String _id,tenLoai;

    public TheLoaiModel() {
    }

    public TheLoaiModel(String _id, String tenLoai) {
        this._id = _id;
        this.tenLoai = tenLoai;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
