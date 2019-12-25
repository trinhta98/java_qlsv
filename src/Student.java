
public class Student {
    private String maSV;
    private String hoTen;
    private String sdt;

    public Student(){ }

    public Student(String maSV, String hoTen, String sdt) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

}
