import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;

public class DesignAdmin {
    private JFrame frame;
    private JTable studentTable;
    private JTable subjectTable;
    private DefaultTableModel studentTableModel;
    private DefaultTableModel subjectTableModel;
    private QuanLySinhVien quanLySinhVien;
    private JTextField tenMonField;
    private JTextField soTinChiField;

    public DesignAdmin() {
        quanLySinhVien = new QuanLySinhVien();
        createAndShowGUI();
        loadSubjectData();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Trang Admin");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Quản Lý Sinh Viên", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm Kiếm");
        searchButton.addActionListener(e -> searchStudent(searchField.getText().trim()));
        searchPanel.add(new JLabel("Tìm Kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        frame.add(topPanel, BorderLayout.NORTH);

        String[] studentColumnNames = {"ID", "Tên", "Email", "Ngày Sinh", "Giới Tính", "Quê Quán", "Số Điện Thoại", "Chuyên Ngành", "Khoa", "Lớp", "Trạng Thái"};
        studentTableModel = new DefaultTableModel(studentColumnNames, 0);
        studentTable = new JTable(studentTableModel);
        studentTable.setFillsViewportHeight(true);
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = studentTable.rowAtPoint(e.getPoint());
                if (row == -1) {
                    studentTable.clearSelection();
                }
            }
        });
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    loadSubjectData();
                } else {
                    loadSubjectData();
                }
            }
        });
        loadStudentData();
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Sinh Viên"));
        frame.add(studentScrollPane, BorderLayout.CENTER);

        JPanel studentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton addButton = new JButton("Thêm Sinh Viên");
        JButton editButton = new JButton("Sửa Sinh Viên");
        JButton deleteButton = new JButton("Xóa Sinh Viên");

        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        studentPanel.add(addButton);
        studentPanel.add(editButton);
        studentPanel.add(deleteButton);
        frame.add(studentPanel, BorderLayout.SOUTH);

        JPanel subjectFormPanel = new JPanel();
        subjectFormPanel.setLayout(new BoxLayout(subjectFormPanel, BoxLayout.Y_AXIS));
        subjectFormPanel.setBorder(BorderFactory.createTitledBorder("Quản Lý Môn Học"));
        subjectFormPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Quản Lý Môn Học"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Tên Môn:"));
        tenMonField = new JTextField();
        inputPanel.add(tenMonField);
        inputPanel.add(new JLabel("Số Tín Chỉ:"));
        soTinChiField = new JTextField();
        inputPanel.add(soTinChiField);
        subjectFormPanel.add(inputPanel);
        subjectFormPanel.add(Box.createVerticalStrut(10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        JButton addMonButton = new JButton("Thêm Môn");
        JButton editMonButton = new JButton("Sửa Môn");
        JButton deleteMonButton = new JButton("Xóa Môn");
        buttonPanel.add(addMonButton);
        buttonPanel.add(editMonButton);
        buttonPanel.add(deleteMonButton);
        subjectFormPanel.add(buttonPanel);

        String[] subjectColumnNames = {"ID", "Tên Môn", "Số Tín Chỉ", "Điểm"};
        subjectTableModel = new DefaultTableModel(subjectColumnNames, 0);
        subjectTable = new JTable(subjectTableModel);
        subjectTable.setFillsViewportHeight(true);
        JScrollPane subjectScrollPane = new JScrollPane(subjectTable);
        subjectScrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Môn Học"));
        JPanel gradeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addGradeButton = new JButton("Thêm Điểm");
        addGradeButton.addActionListener(e -> addGrade());
        gradeButtonPanel.add(addGradeButton);
        studentPanel.add(gradeButtonPanel, BorderLayout.SOUTH);

        JPanel subjectContainer = new JPanel(new BorderLayout(10, 10));
        subjectContainer.add(subjectFormPanel, BorderLayout.NORTH);
        subjectContainer.add(subjectScrollPane, BorderLayout.CENTER);
        frame.add(subjectContainer, BorderLayout.EAST);

        editMonButton.addActionListener(e -> {
            int selectedRow = subjectTable.getSelectedRow();
            if (selectedRow != -1) {
                int monId = (int) subjectTableModel.getValueAt(selectedRow, 0);
                String tenMonCu = (String) subjectTableModel.getValueAt(selectedRow, 1);
                int soTinChiCu = (int) subjectTableModel.getValueAt(selectedRow, 2);

                JTextField tenMonMoiField = new JTextField(tenMonCu);
                JTextField soTinChiMoiField = new JTextField(String.valueOf(soTinChiCu));

                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Tên Môn:"));
                panel.add(tenMonMoiField);
                panel.add(new JLabel("Số Tín Chỉ:"));
                panel.add(soTinChiMoiField);
                int result = JOptionPane.showConfirmDialog(frame, panel, "Sửa Môn Học", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String tenMonMoi = tenMonMoiField.getText().trim();
                        int soTinChiMoi = Integer.parseInt(soTinChiMoiField.getText().trim());

                        MonHoc mon = new MonHoc(monId, tenMonMoi, soTinChiMoi);
                        new QuanLyMon().updateMon(mon);
                        loadSubjectData();
                        JOptionPane.showMessageDialog(frame, "Cập nhật môn học thành công!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Lỗi khi sửa môn học: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng chọn một môn học để sửa.");
            }
        });
        deleteMonButton.addActionListener(e -> {
            int selectedRow = subjectTable.getSelectedRow();
            if (selectedRow != -1) {
                int monId = (int) subjectTableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn xóa môn học này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        new QuanLyMon().deleteMon(monId);
                        loadSubjectData();
                        JOptionPane.showMessageDialog(frame, "Xóa môn học thành công!");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Lỗi khi xóa môn học: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng chọn một môn học để xóa.");
            }
        });
        addMonButton.addActionListener(e -> {
            try {
                MonHoc mon = new MonHoc(0, tenMonField.getText(), Integer.parseInt(soTinChiField.getText()));
                new QuanLyMon().addMon(mon);
                JOptionPane.showMessageDialog(frame, "Thêm môn thành công!");
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    int sinhVienId = (int) studentTableModel.getValueAt(selectedRow, 0);
                    addSubjectToStudent(sinhVienId, mon.getId());
                }
                loadSubjectData();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Lỗi CSDL: " + sqlEx.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Lỗi khác: " + ex.getMessage());
            }
        });
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadSubjectData();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void loadStudentData() {
        studentTableModel.setRowCount(0);
        List<SinhVien> sinhViens = quanLySinhVien.getSinhVien();
        for (SinhVien sinhVien : sinhViens) {
            Object[] row = {
                    sinhVien.getId(),
                    sinhVien.getTen(),
                    sinhVien.getEmail(),
                    sinhVien.getNgaysinh(),
                    sinhVien.getGioitinh(),
                    sinhVien.getQuequan(),
                    sinhVien.getSodienthoai(),
                    sinhVien.getChuyennganh(),
                    sinhVien.getKhoa(),
                    sinhVien.getLop(),
                    sinhVien.getTrangthai()
            };
            studentTableModel.addRow(row);
        }
    }
    private void addSubjectToStudent(int sinhVienId, int monId) {
        QuanLySinhVienMon quanLySinhVienMon = new QuanLySinhVienMon();
        try {
            quanLySinhVienMon.addSubjectForStudent(sinhVienId, monId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi liên kết môn học: " + e.getMessage());
        }
    }
    private void loadSubjectData() {
        subjectTableModel.setRowCount(0);
        int selectedRow = studentTable.getSelectedRow();
        try {
            QuanLyMon qlMon = new QuanLyMon();
            if (selectedRow == -1) {
                List<MonHoc> allSubjects = qlMon.getAllMon();
                for (MonHoc mon : allSubjects) {
                    subjectTableModel.addRow(new Object[]{
                            mon.getId(),
                            mon.getTenMon(),
                            mon.getSoTinChi(),
                            ""
                    });
                }
            } else {
                int sinhVienId = (int) studentTableModel.getValueAt(selectedRow, 0);
                QuanLySinhVienMon qlSVM = new QuanLySinhVienMon();
                List<SinhVienMon> danhSach = qlSVM.getDanhSachMonTheoSinhVien(sinhVienId);
                for (SinhVienMon svm : danhSach) {
                    MonHoc mon = qlMon.getMonById(svm.getMonId());
                    Object[] row = {
                            mon.getId(),
                            mon.getTenMon(),
                            mon.getSoTinChi(),
                            svm.getDiem()
                    };
                    subjectTableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }
    private void searchStudent(String searchTerm) {
        List<SinhVien> results = quanLySinhVien.searchStudents(searchTerm);
        studentTableModel.setRowCount(0);
        for (SinhVien sinhVien : results) {
            Object[] row = {
                    sinhVien.getId(),
                    sinhVien.getTen(),
                    sinhVien.getEmail(),
                    sinhVien.getNgaysinh(),
                    sinhVien.getGioitinh(),
                    sinhVien.getQuequan(),
                    sinhVien.getSodienthoai(),
                    sinhVien.getChuyennganh(),
                    sinhVien.getKhoa(),
                    sinhVien.getLop(),
                    sinhVien.getTrangthai()
            };
            studentTableModel.addRow(row);
        }
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Không tìm thấy sinh viên.");
        }
    }
    private void addStudent() {
        JTextField nameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JDateChooser ngaysinhField = new JDateChooser();
        String[] genders = {"Nam", "Nữ", "Khác"};
        JComboBox<String> gioitinhField = new JComboBox<>(genders);
        JTextField quequanField = new JTextField(10);
        JTextField sodienthoaiField = new JTextField(10);
        JTextField chuyennganhField = new JTextField(10);
        JTextField khoaField = new JTextField(10);
        JTextField lopField = new JTextField(10);
        String[] statuses = {"Đang Học", "Đã Tốt Nghiệp"};
        JComboBox<String> trangthaiField = new JComboBox<>(statuses);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 2));
        panel.add(new JLabel("Tên:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Ngày Sinh:"));
        panel.add(ngaysinhField);
        panel.add(new JLabel("Giới Tính:"));
        panel.add(gioitinhField);
        panel.add(new JLabel("Quê Quán:"));
        panel.add(quequanField);
        panel.add(new JLabel("Số Điện Thoại:"));
        panel.add(sodienthoaiField);
        panel.add(new JLabel("Chuyên Ngành:"));
        panel.add(chuyennganhField);
        panel.add(new JLabel("Khoa:"));
        panel.add(khoaField);
        panel.add(new JLabel("Lớp:"));
        panel.add(lopField);
        panel.add(new JLabel("Trạng Thái:"));
        panel.add(trangthaiField);
        int result = JOptionPane.showConfirmDialog(frame, panel, "Thêm Sinh Viên", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String ten = nameField.getText();
                String email = emailField.getText();
                Date ngaysinh = ngaysinhField.getDate();
                String gioitinh = (String) gioitinhField.getSelectedItem();
                String quequan = quequanField.getText();
                String sodienthoai = sodienthoaiField.getText();
                String chuyennganh = chuyennganhField.getText();
                String khoa = khoaField.getText();
                String lop = lopField.getText();
                String trangthai = (String) trangthaiField.getSelectedItem();
                SinhVien newStudent = new SinhVien(0, ten, email, ngaysinh, gioitinh, quequan, sodienthoai, chuyennganh, khoa, lop, trangthai);
                quanLySinhVien.addStudent(newStudent);
                loadStudentData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Vui lòng nhập đúng định dạng.");
            }
        }
    }
    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) studentTableModel.getValueAt(selectedRow, 0);
            SinhVien sinhVien = quanLySinhVien.getSinhVienId(id);
            if (sinhVien != null) {
                JTextField nameField = new JTextField(sinhVien.getTen(), 10);
                JTextField emailField = new JTextField(sinhVien.getEmail(), 10);
                JDateChooser ngaysinhField = new JDateChooser();
                ngaysinhField.setDate(sinhVien.getNgaysinh());
                String[] genders = {"Nam", "Nữ", "Khác"};
                JComboBox<String> gioitinhField = new JComboBox<>(genders);
                gioitinhField.setSelectedItem(sinhVien.getGioitinh());
                JTextField quequanField = new JTextField(sinhVien.getQuequan(), 10);
                JTextField sodienthoaiField = new JTextField(sinhVien.getSodienthoai(), 10);
                JTextField chuyennganhField = new JTextField(sinhVien.getChuyennganh(), 10);
                JTextField khoaField = new JTextField(sinhVien.getKhoa(), 10);
                JTextField lopField = new JTextField(sinhVien.getLop(), 10);
                String[] statuses = {"Đang Học", "Đã Tốt Nghiệp"};
                JComboBox<String> trangthaiField = new JComboBox<>(statuses);
                trangthaiField.setSelectedItem(sinhVien.getTrangthai());

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(11, 2));
                panel.add(new JLabel("Tên:"));
                panel.add(nameField);
                panel.add(new JLabel("Email:"));
                panel.add(emailField);
                panel.add(new JLabel("Ngày Sinh:"));
                panel.add(ngaysinhField);
                panel.add(new JLabel("Giới Tính:"));
                panel.add(gioitinhField);
                panel.add(new JLabel("Quê Quán:"));
                panel.add(quequanField);
                panel.add(new JLabel("Số Điện Thoại:"));
                panel.add(sodienthoaiField);
                panel.add(new JLabel("Chuyên Ngành:"));
                panel.add(chuyennganhField);
                panel.add(new JLabel("Khoa:"));
                panel.add(khoaField);
                panel.add(new JLabel("Lớp:"));
                panel.add(lopField);
                panel.add(new JLabel("Trạng Thái:"));
                panel.add(trangthaiField);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Sửa Sinh Viên", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        sinhVien.setTen(nameField.getText());
                        sinhVien.setEmail(emailField.getText());
                        sinhVien.setNgaysinh(ngaysinhField.getDate());
                        sinhVien.setGioitinh((String) gioitinhField.getSelectedItem());
                        sinhVien.setQuequan(quequanField.getText());
                        sinhVien.setSodienthoai(sodienthoaiField.getText());
                        sinhVien.setChuyennganh(chuyennganhField.getText());
                        sinhVien.setKhoa(khoaField.getText());
                        sinhVien.setLop(lopField.getText());
                        sinhVien.setTrangthai((String) trangthaiField.getSelectedItem());

                        quanLySinhVien.updateStudent(sinhVien);
                        loadStudentData();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, "Vui lòng nhập đúng định dạng.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Sinh viên không tìm thấy.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn sinh viên để sửa.");
        }
    }
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            int studentId = (int) studentTableModel.getValueAt(selectedRow, 0);
            int confirmation = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn xóa sinh viên này?", "Xóa Sinh Viên", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                quanLySinhVien.deleteStudent(studentId);
                loadStudentData();
                subjectTableModel.setRowCount(0);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn sinh viên để xóa.");
        }
    }
    private void addGrade() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Vui lòng chọn sinh viên.");
            return;
        }
        int sinhVienId = (int) studentTableModel.getValueAt(selectedRow, 0);
        JTextField monIdField = new JTextField();
        JTextField diemField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Môn ID:"));
        panel.add(monIdField);
        panel.add(new JLabel("Điểm:"));
        panel.add(diemField);
        int result = JOptionPane.showConfirmDialog(frame, panel, "Thêm Điểm", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int monId = Integer.parseInt(monIdField.getText());
                double diem = Double.parseDouble(diemField.getText());
                QuanLySinhVienMon quanLySinhVienMon = new QuanLySinhVienMon();
                if (quanLySinhVienMon.gradeExists(sinhVienId, monId)) {
                    JOptionPane.showMessageDialog(frame, "Điểm đã tồn tại cho môn này.");
                    return;
                }
                quanLySinhVienMon.addGrade(sinhVienId, monId, diem);
                JOptionPane.showMessageDialog(frame, "Thêm điểm thành công!");
                loadSubjectData();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Vui lòng nhập đúng định dạng cho Môn ID và Điểm.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Lỗi: " + e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DesignAdmin::new);

    }
}