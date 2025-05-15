import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DesignSinhVien {
    private JFrame frame;
    private DefaultTableModel subjectTableModel;
    private JTable subjectTable;
    private QuanLySinhVienMon quanLySinhVienMon;
    private QuanLyMon quanLyMon;
    private int studentId;

    public DesignSinhVien(int sinhvienId) {
        this.studentId = sinhvienId;
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Thông tin sinh viên");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            QuanLySinhVien quanLySinhVien = new QuanLySinhVien();
            SinhVien sinhVien = quanLySinhVien.getSinhVienId(studentId);

            String[] studentColumnNames = {"Thông Tin", "Giá Trị"};
            DefaultTableModel studentTableModel = new DefaultTableModel(studentColumnNames, 0);
            JTable studentTable = new JTable(studentTableModel);
            studentTable.setEnabled(false);
            if (sinhVien != null) {
                studentTableModel.addRow(new Object[]{"Tên", sinhVien.getTen()});
                studentTableModel.addRow(new Object[]{"Email", sinhVien.getEmail()});
                studentTableModel.addRow(new Object[]{"Ngày Sinh", sinhVien.getNgaysinh()});
                studentTableModel.addRow(new Object[]{"Giới Tính", sinhVien.getGioitinh()});
                studentTableModel.addRow(new Object[]{"Quê Quán", sinhVien.getQuequan()});
                studentTableModel.addRow(new Object[]{"Số Điện Thoại", sinhVien.getSodienthoai()});
                studentTableModel.addRow(new Object[]{"Chuyên Ngành", sinhVien.getChuyennganh()});
                studentTableModel.addRow(new Object[]{"Khoa", sinhVien.getKhoa()});
                studentTableModel.addRow(new Object[]{"Lớp", sinhVien.getLop()});
                studentTableModel.addRow(new Object[]{"Trạng Thái", sinhVien.getTrangthai()});
            } else {
                studentTableModel.addRow(new Object[]{"Thông Báo", "Không tìm thấy sinh viên."});
            }

            String[] subjectColumnNames = {"Tên Môn", "Số Tín Chỉ", "Điểm"};
            subjectTableModel = new DefaultTableModel(subjectColumnNames, 0);
            subjectTable = new JTable(subjectTableModel);
            subjectTable.setEnabled(false);
            quanLySinhVienMon = new QuanLySinhVienMon();
            quanLyMon = new QuanLyMon();
            loadSubjects(studentId);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel titleLabel = new JLabel("Thông Tin Sinh Viên");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            titlePanel.add(titleLabel);
            mainPanel.add(titlePanel);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(new JScrollPane(studentTable));
            mainPanel.add(Box.createVerticalStrut(10));
            JLabel monHocLabel = new JLabel("Danh sách môn học và điểm số");
            monHocLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            monHocLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(monHocLabel);
            mainPanel.add(Box.createVerticalStrut(5));
            mainPanel.add(new JScrollPane(subjectTable));
            frame.add(mainPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    private void loadSubjects(int studentId) {
        subjectTableModel.setRowCount(0);
        List<SinhVienMon> dsMon = quanLySinhVienMon.getDanhSachMonTheoSinhVien(studentId);
        if (dsMon != null && !dsMon.isEmpty()) {
            for (SinhVienMon svm : dsMon) {
                MonHoc monHoc = quanLyMon.getMonById(svm.getMonId());
                if (monHoc != null) {
                    subjectTableModel.addRow(new Object[]{
                            monHoc.getTenMon(), monHoc.getSoTinChi(), svm.getDiem()
                    });
                }
            }
        } else {
            subjectTableModel.addRow(new Object[]{"Không có dữ liệu", "", ""});
        }
    }
    public void refreshSubjectList() {
        loadSubjects(studentId);
    }
    public void onAdminUpdate() {
        refreshSubjectList();
    }
}
