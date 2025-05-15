import java.util.Date;

public class SinhVien {
    private int id;
    private String ten;
    private String email;
    private Date ngaysinh;
    private String gioitinh;
    private String quequan;
    private String sodienthoai;
    private String chuyennganh;
    private String khoa;
    private String lop;
    private String trangthai;

    public SinhVien(int id, String ten, String email, Date ngaysinh, String gioitinh, String quequan, String sodienthoai, String chuyennganh, String khoa, String lop, String trangthai) {
        this.id = id;
        this.ten = ten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.quequan = quequan;
        this.sodienthoai = sodienthoai;
        this.chuyennganh = chuyennganh;
        this.khoa = khoa;
        this.lop = lop;
        this.trangthai = trangthai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getChuyennganh() {
        return chuyennganh;
    }

    public void setChuyennganh(String chuyennganh) {
        this.chuyennganh = chuyennganh;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", email='" + email + '\'' +
                ", ngaysinh=" + ngaysinh +
                ", gioitinh='" + gioitinh + '\'' +
                ", quequan='" + quequan + '\'' +
                ", sodienthoai='" + sodienthoai + '\'' +
                ", chuyennganh='" + chuyennganh + '\'' +
                ", khoa='" + khoa + '\'' +
                ", lop='" + lop + '\'' +
                ", trangthai='" + trangthai + '\'' +
                '}';
    }
}
