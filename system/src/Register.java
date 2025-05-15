import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {
    public Register() {
        JFrame frame = new JFrame("ĐĂNG KÝ");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2, 5, 5));
        JLabel usernameLabel = new JLabel("Tên Đăng Ký:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Mật Khẩu:");
        JPasswordField passwordField = new JPasswordField();
        JLabel roleLabel = new JLabel("Vai Trò (admin/sinhvien):");
        JTextField roleField = new JTextField();
        JLabel idSinhVienLabel = new JLabel("ID Sinh Viên:");
        JTextField idSinhVienField = new JTextField();
        JButton registerButton = new JButton("Đăng Ký");
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(roleLabel);
        frame.add(roleField);
        frame.add(idSinhVienLabel);
        frame.add(idSinhVienField);
        frame.add(new JLabel());
        frame.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = String.valueOf(passwordField.getPassword()).trim();
                String role = roleField.getText().trim().toLowerCase();
                String idSinhVienText = idSinhVienField.getText().trim();
                if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng điền đầy đủ thông tin.");
                    return;
                }
                if (!role.equals("admin") && !role.equals("sinhvien")) {
                    JOptionPane.showMessageDialog(frame, "Vai trò không hợp lệ. Chỉ chấp nhận 'admin' hoặc 'sinhvien'.");
                    return;
                }
                Integer idSinhVien = null;
                if (role.equals("sinhvien")) {
                    try {
                        idSinhVien = Integer.parseInt(idSinhVienText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "ID sinh viên không hợp lệ.");
                        return;
                    }
                }
                QuanLyTaiKhoan quanLyTaiKhoan = new QuanLyTaiKhoan();
                boolean result = quanLyTaiKhoan.registerUserWithLink(username, password, role, idSinhVien);
                if (result) {
                    JOptionPane.showMessageDialog(frame, "Đăng ký thành công!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Đăng ký thất bại!");
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
