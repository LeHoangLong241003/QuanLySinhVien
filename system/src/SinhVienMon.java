public class SinhVienMon {
    private int sinhVienId;
    private int monId;
    private double diem;

    public SinhVienMon(){
    }

    public SinhVienMon(int sinhVienId, int monId, double diem) {
        this.sinhVienId = sinhVienId;
        this.monId = monId;
        this.diem = diem;
    }

    public int getSinhVienId() {
        return sinhVienId;
    }

    public void setSinhVienId(int sinhVienId) {
        this.sinhVienId = sinhVienId;
    }

    public int getMonId() {
        return monId;
    }

    public void setMonId(int monId) {
        this.monId = monId;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }

    @Override
    public String toString() {
        return "SinhVienMon{" +
                "sinhVienId=" + sinhVienId +
                ", monId=" + monId +
                ", diem=" + diem +
                '}';
    }
}
