import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLySinhVien {
    public List<SinhVien> getSinhVien() {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT * FROM sinhvien";
        try (Connection conn = Connect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sinhViens.add(new SinhVien(
                        rs.getInt("id"),
                        rs.getString("ten"),
                        rs.getString("email"),
                        rs.getDate("ngaysinh"),
                        rs.getString("gioitinh"),
                        rs.getString("quequan"),
                        rs.getString("sodienthoai"),
                        rs.getString("chuyennganh"),
                        rs.getString("khoa"),
                        rs.getString("lop"),
                        rs.getString("trangthai")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        return sinhViens;
    }
    public SinhVien getSinhVienId(int id) {
        String sql = "SELECT * FROM sinhvien WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new SinhVien(
                            rs.getInt("id"),
                            rs.getString("ten"),
                            rs.getString("email"),
                            rs.getDate("ngaysinh"),
                            rs.getString("gioitinh"),
                            rs.getString("quequan"),
                            rs.getString("sodienthoai"),
                            rs.getString("chuyennganh"),
                            rs.getString("khoa"),
                            rs.getString("lop"),
                            rs.getString("trangthai")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student by ID: " + e.getMessage());
        }
        return null;
    }
    public List<SinhVien> searchStudents(String keyword) {
        List<SinhVien> results = new ArrayList<>();
        String sql = "SELECT * FROM sinhvien WHERE " +
                "ten LIKE ? OR " +
                "id = ? OR " +
                "lop LIKE ? OR " +
                "chuyennganh LIKE ? OR " +
                "khoa LIKE ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            pstmt.setString(1, likeKeyword);
            try {
                int id = Integer.parseInt(keyword);
                pstmt.setInt(2, id);
            } catch (NumberFormatException e) {
                pstmt.setInt(2, -1);
            }
            pstmt.setString(3, likeKeyword);
            pstmt.setString(4, likeKeyword);
            pstmt.setString(5, likeKeyword);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new SinhVien(
                            rs.getInt("id"),
                            rs.getString("ten"),
                            rs.getString("email"),
                            rs.getDate("ngaysinh"),
                            rs.getString("gioitinh"),
                            rs.getString("quequan"),
                            rs.getString("sodienthoai"),
                            rs.getString("chuyennganh"),
                            rs.getString("khoa"),
                            rs.getString("lop"),
                            rs.getString("trangthai")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
        return results;
    }
    public void addStudent(SinhVien student) {
        String sql = "INSERT INTO sinhvien (ten, email, ngaysinh, gioitinh, quequan, sodienthoai, chuyennganh, khoa, lop, trangthai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getTen());
            pstmt.setString(2, student.getEmail());
            pstmt.setDate(3, new java.sql.Date(student.getNgaysinh().getTime()));
            pstmt.setString(4, student.getGioitinh());
            pstmt.setString(5, student.getQuequan());
            pstmt.setString(6, student.getSodienthoai());
            pstmt.setString(7, student.getChuyennganh());
            pstmt.setString(8, student.getKhoa());
            pstmt.setString(9, student.getLop());
            pstmt.setString(10, student.getTrangthai());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }
    public void updateStudent(SinhVien student) {
        String sql = "UPDATE sinhvien SET ten = ?, email = ?, ngaysinh = ?, gioitinh = ?, quequan = ?, sodienthoai = ?, chuyennganh = ?, khoa = ?, lop = ?, trangthai = ? WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getTen());
            pstmt.setString(2, student.getEmail());
            pstmt.setDate(3, new java.sql.Date(student.getNgaysinh().getTime()));
            pstmt.setString(4, student.getGioitinh());
            pstmt.setString(5, student.getQuequan());
            pstmt.setString(6, student.getSodienthoai());
            pstmt.setString(7, student.getChuyennganh());
            pstmt.setString(8, student.getKhoa());
            pstmt.setString(9, student.getLop());
            pstmt.setString(10, student.getTrangthai());
            pstmt.setInt(11, student.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }
    public void deleteStudent(int id) {
        String sql = "DELETE FROM sinhvien WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
    public List<MonHoc> getSubjectsForStudent(int studentId) {
        List<MonHoc> subjects = new ArrayList<>();
        String sql = "SELECT m.id, m.tenmon, m.sotinchi " +
                "FROM sinhvien_mon sm JOIN mon m ON sm.mon_id = m.id " +
                "WHERE sm.sinhvien_id = ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    subjects.add(new MonHoc(
                            rs.getInt("id"),
                            rs.getString("tenmon"),
                            rs.getInt("sotinchi")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching subjects for student: " + e.getMessage());
        }
        return subjects;
    }

}