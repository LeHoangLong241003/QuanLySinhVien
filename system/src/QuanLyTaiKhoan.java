import java.sql.*;

public class QuanLyTaiKhoan {
    public boolean authenticate(String tendn, String matkhau) {
        String sql = "SELECT * FROM nguoidung WHERE tendn = ? AND matkhau = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tendn);
            pstmt.setString(2, matkhau);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String getRole(String tendn) {
        String sql = "SELECT role FROM nguoidung WHERE tendn = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tendn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Integer getSinhVienIdByUsername(String tendn) {
        String sql = "SELECT sv.id FROM sinhvien sv " +
                "JOIN nguoidung nd ON sv.nguoidung_id = nd.id " +
                "WHERE nd.tendn = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tendn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean registerUserWithLink(String tendn, String matkhau, String role, Integer sinhvienId) {
        String insertUserSQL = "INSERT INTO nguoidung (tendn, matkhau, role) VALUES (?, ?, ?)";
        String checkSinhVienSQL = "SELECT * FROM sinhvien WHERE id = ? AND nguoidung_id IS NULL";
        String updateSinhVienSQL = "UPDATE sinhvien SET nguoidung_id = ? WHERE id = ?";

        try (Connection conn = Connect.getConnection()) {
            conn.setAutoCommit(false);
            if ("sinhvien".equalsIgnoreCase(role)) {
                try (PreparedStatement check = conn.prepareStatement(checkSinhVienSQL)) {
                    check.setInt(1, sinhvienId);
                    ResultSet rs = check.executeQuery();
                    if (!rs.next()) {
                        return false;
                    }
                }
            }
            int userId;
            try (PreparedStatement insertUser = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
                insertUser.setString(1, tendn);
                insertUser.setString(2, matkhau);
                insertUser.setString(3, role);
                insertUser.executeUpdate();
                ResultSet keys = insertUser.getGeneratedKeys();
                if (keys.next()) {
                    userId = keys.getInt(1);
                } else {
                    conn.rollback();
                    return false;
                }
            }
            if ("sinhvien".equalsIgnoreCase(role)) {
                try (PreparedStatement updateSV = conn.prepareStatement(updateSinhVienSQL)) {
                    updateSV.setInt(1, userId);
                    updateSV.setInt(2, sinhvienId);
                    int updated = updateSV.executeUpdate();
                    if (updated == 0) {
                        conn.rollback();
                        return false;
                    }
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
