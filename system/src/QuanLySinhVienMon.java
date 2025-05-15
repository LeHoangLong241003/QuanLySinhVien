import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLySinhVienMon {
    private Connection conn;
    public QuanLySinhVienMon() {
        try {
            this.conn = Connect.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database: " + e.getMessage());
        }
    }
    public void addSubjectForStudent(int sinhVienId, int monId) throws SQLException {
        String query = "INSERT INTO sinhvien_mon (sinhvien_id, mon_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sinhVienId);
            stmt.setInt(2, monId);
            stmt.executeUpdate();
        }
    }
    public void addGrade(int sinhVienId, int monId, double diem) throws SQLException {
        String query = "INSERT INTO sinhvien_mon (sinhvien_id, mon_id, diem) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sinhVienId);
            stmt.setInt(2, monId);
            stmt.setDouble(3, diem);
            stmt.executeUpdate();
        }
    }
    public List<SinhVienMon> getDanhSachMonTheoSinhVien(int sinhVienId) {
        List<SinhVienMon> danhSach = new ArrayList<>();
        String sql = "SELECT mon_id, diem FROM sinhvien_mon WHERE sinhvien_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sinhVienId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SinhVienMon svm = new SinhVienMon();
                    svm.setSinhVienId(sinhVienId);
                    svm.setMonId(rs.getInt("mon_id"));
                    svm.setDiem(rs.getDouble("diem"));
                    danhSach.add(svm);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching subjects for student: " + e.getMessage());
        }

        return danhSach;
    }
    public boolean gradeExists(int sinhVienId, int monId) throws SQLException {
        String query = "SELECT COUNT(*) FROM sinhvien_mon WHERE sinhvien_id = ? AND mon_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, sinhVienId);
            stmt.setInt(2, monId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}