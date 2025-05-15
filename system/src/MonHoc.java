public class MonHoc {
    private int id;
    private String tenMon;
    private int soTinChi;
    public MonHoc() {
    }
    public MonHoc(int id, String tenMon, int soTinChi) {
        this.id = id;
        this.tenMon = tenMon;
        this.soTinChi = soTinChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    @Override
    public String toString() {
        return "MonHoc{" +
                "id=" + id +
                ", tenMon='" + tenMon + '\'' +
                ", soTinChi=" + soTinChi +
                '}';
    }
}
