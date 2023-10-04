package namtdph08817.android.appdoctruyen.models;

public class TruyenModel {
    private String _id,nameTruyen,nameTacgia;
    private int namXuatban;
    private String anhBia,theLoai,mota;

    public TruyenModel() {
    }

    public TruyenModel(String _id, String nameTruyen, String nameTacgia, int namXuatban, String anhBia, String theLoai, String mota) {
        this._id = _id;
        this.nameTruyen = nameTruyen;
        this.nameTacgia = nameTacgia;
        this.namXuatban = namXuatban;
        this.anhBia = anhBia;
        this.theLoai = theLoai;
        this.mota = mota;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameTruyen() {
        return nameTruyen;
    }

    public void setNameTruyen(String nameTruyen) {
        this.nameTruyen = nameTruyen;
    }

    public String getNameTacgia() {
        return nameTacgia;
    }

    public void setNameTacgia(String nameTacgia) {
        this.nameTacgia = nameTacgia;
    }

    public int getNamXuatban() {
        return namXuatban;
    }

    public void setNamXuatban(int namXuatban) {
        this.namXuatban = namXuatban;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
