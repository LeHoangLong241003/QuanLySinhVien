# QuanLySinhVien
 # Student Management System

Student management application built in pure Java using Swing GUI framework. Developed in IntelliJ IDEA and connected to MySQL database via JDBC.
The system supports student, subject and grade management, with full CRUD operations, search functionality and user authentication with role-based access control for administrators and students.

## 🚀 Key Features
- Register and login for users
- Role-based access: Admin and Student
- Add, edit, delete student records
- Manage subjects and credit information
- Enter and update student grades
- Search by name, student ID, or subject code
- User-friendly interface using Java Swing
- Connects to MySQL database via JDBC

## 🛠️ Technologies Used
- Java (JDK 24)
- Java Swing (Graphical User Interface)
- MySQL (Database)
- JDBC (Java Database Connectivity)
- IntelliJ IDEA (IDE)

## 🗄️ Database Structure
The MySQL database includes the following main tables:
- `nguoidung` (id, tendn, matkhau, role) – for login/register and role control
- `sinhvien` (id, nguoidung_id, ten, ngaysinh, gioitinh, quequan, email, sodienthoai, chuyennganh, khoa, lop, trangthai)
- `mon` (id, tenmon, sotinchi)
- `sinhvien_mon` (sinhvien_id, mon_id, diem)
> A sample SQL (`quanlysinhvien.sql`) is included to create the tables and insert sample data.

## ⚙️ How to Run the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/LeHoangLong241003/QuanLySinhVien.git
