package ra.presentation.admin;

import java.util.Scanner;

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
}
