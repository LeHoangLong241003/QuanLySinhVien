import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("ĐĂNG NHẬP");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 2));
        JLabel usernameLabel = new JLabel("Tên Đăng Nhập:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Mật Khẩu:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Đăng Nhập");
        JButton registerButton = new JButton("Đăng Ký");
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(registerButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tendn = usernameField.getText();
                String matkhau = String.valueOf(passwordField.getPassword());
                QuanLyTaiKhoan quanLyTaiKhoan = new QuanLyTaiKhoan();
                if (quanLyTaiKhoan.authenticate(tendn, matkhau)) {
                    String role = quanLyTaiKhoan.getRole(tendn);
                    if ("admin".equals(role)) {
                        new DesignAdmin();
                    } else {
                        Integer sinhvienId = quanLyTaiKhoan.getSinhVienIdByUsername(tendn);
                        if (sinhvienId != null) {
                            new DesignSinhVien(sinhvienId);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Không tìm thấy thông tin sinh viên.");
                        }
                    }
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Thông tin đăng nhập không hợp lệ, vui lòng thử lại.");
                }
            }
        });
        registerButton.addActionListener(e -> new Register());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
