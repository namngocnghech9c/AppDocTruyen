package namtdph08817.android.appdoctruyen.models;

public class FavoriteModel {
    private String _id, idUser;
    private TruyenModel truyenModel;

    public FavoriteModel() {
    }

    public FavoriteModel(String _id, String idUser, TruyenModel truyenModel) {
        this._id = _id;
        this.idUser = idUser;
        this.truyenModel = truyenModel;
    }

    public FavoriteModel(String idUser, TruyenModel truyenModel) {
        this.idUser = idUser;
        this.truyenModel = truyenModel;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public TruyenModel getTruyenModel() {
        return truyenModel;
    }

    public void setTruyenModel(TruyenModel truyenModel) {
        this.truyenModel = truyenModel;
    }
}
