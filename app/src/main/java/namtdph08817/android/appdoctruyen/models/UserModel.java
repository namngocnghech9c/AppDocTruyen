package namtdph08817.android.appdoctruyen.models;

public class UserModel {
    private String _id, username, passwd, email, fullname, sdt;
    private int vaitro;
    private String avatar;

    public UserModel() {
    }

    public UserModel(String _id, String username, String passwd, String email, String fullname, String sdt, int vaitro, String avatar) {
        this._id = _id;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
        this.fullname = fullname;
        this.sdt = sdt;
        this.vaitro = vaitro;
        this.avatar = avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getVaitro() {
        return vaitro;
    }

    public void setVaitro(int vaitro) {
        this.vaitro = vaitro;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
