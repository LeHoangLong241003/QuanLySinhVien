import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuanLyMon {
    public void addMon(MonHoc mon) throws SQLException {
        String sql = "INSERT INTO mon (tenmon, sotinchi) VALUES (?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mon.getTenMon());
            ps.setInt(2, mon.getSoTinChi());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding subject: " + e.getMessage());
            throw e;
        }
    }
    public void updateMon(MonHoc mon) throws SQLException {
        String sql = "UPDATE mon SET tenmon = ?, sotinchi = ? WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mon.getTenMon());
            ps.setInt(2, mon.getSoTinChi());
            ps.setInt(3, mon.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating subject: " + e.getMessage());
            throw e;
        }
    }
    public void deleteMon(int id) throws SQLException {
        String sql = "DELETE FROM mon WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting subject: " + e.getMessage());
            throw e;
        }
    }
    public MonHoc getMonById(int id) {
        String sql = "SELECT * FROM mon WHERE id = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MonHoc(
                            rs.getInt("id"),
                            rs.getString("tenmon"),
                            rs.getInt("sotinchi")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching subject by ID: " + e.getMessage());
        }
        return null;
    }
    public List<MonHoc> getAllMon() {
        List<MonHoc> monList = new ArrayList<>();
        String sql = "SELECT * FROM mon";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                monList.add(new MonHoc(
                        rs.getInt("id"),
                        rs.getString("tenmon"),
                        rs.getInt("sotinchi")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching subjects: " + e.getMessage());
        }
        return monList;
    }
}