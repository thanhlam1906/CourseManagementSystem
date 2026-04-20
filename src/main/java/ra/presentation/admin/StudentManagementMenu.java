package ra.presentation.admin;

import ra.model.dto.StudentDTO;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class StudentManagementMenu {
    private Scanner scanner =  new Scanner(System.in);
    public void displayStudentMNGMenu() {
        while(true){
            System.out.println("===== QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Danh sách | 2. Thêm | 3. Sửa | 4. Xóa | 5. Tìm Kiếm | 6. Sap xếp | 0. Quay lại");
            System.out.print("Vui lòng chọn chức năng:");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice){
                case 1:
                    //listStudents();
                    break;
                case 2:
                    //addStudent();
                    break;
                case 3:
                    //updateStudent();
                    break;
                case 4:
                    //deleteStudent();
                    break;
                case 5:
                    //searchStudents();
                    break;
                case 6:
                    //sortStudents();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ. Vui lòng chọn lại.");

            }
        }
    }

    private void displayStudentList(List<StudentDTO> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("Không có học viên nào.");
            return;
        }

        String headerFormat = "| %-5s | %-5s | %-25s | %-15s | %-30s | %-10s | %-20s | %-20s |%n";
        String rowFormat = "| %-5d | %-5d | %-25s | %-15s | %-30s | %-10s | %-20s | %-20s |%n";
        String line = "--------------------------------------------------------------------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "ID", "Ten hoc vien", "Ngay sinh", "Email", "Gioi tinh", "SDT", "Ngay tao");
        System.out.println(line);

        IntStream.range(0, students.size()).forEach(i -> {
            StudentDTO student = students.get(i);
            String sexText = student.getSex() == null ? "" : (student.getSex() ? "Nam" : "Nu");
            System.out.printf(
                    rowFormat,
                    i + 1,
                    student.getId() != null ? student.getId() : 0,
                    student.getName(),
                    student.getDob() != null ? student.getDob().toString() : "",
                    student.getEmail() != null ? student.getEmail() : "",
                    sexText,
                    student.getPhone() != null ? student.getPhone() : "",
                    student.getCreateAt() != null ? student.getCreateAt().toString() : ""
            );
        });

        System.out.println(line);
    }
}
