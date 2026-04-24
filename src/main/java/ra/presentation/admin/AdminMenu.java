package ra.presentation.admin;

import java.util.Scanner;

public class AdminMenu {

    public void displayAdminMenu(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("=== ADMIN MENU ===");
            System.out.println("1. Quản lí Khoá Học");
            System.out.println("2. Quản lí Sinh Viên");
            System.out.println("3. Quán lí Đăng kí Khóa Học");
            System.out.println("4. Thống kê dữ liệu ");
            System.out.println("5. Đăng xuất");
            System.out.print("Lưa chọn chức năng: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    CourseManagementMenu courseMenu = new CourseManagementMenu();
                    courseMenu.displayCourseMNGMenu();
                    break;
                case 2:
                    StudentManagementMenu studentMenu = new StudentManagementMenu();
                    studentMenu.displayStudentMNGMenu();
                    break;
                case 3:
                    EnrollmentManagementMenu enrollmentMenu = new EnrollmentManagementMenu();
                    enrollmentMenu.displayEnrollmentMNGMenu();
                    break;
                case 4:
                    StatisticsMenu statisticsMenu = new StatisticsMenu();
                    statisticsMenu.displayStatisticsMenu();
                case 5:
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Hãy thử lại ");
            }

        }
    }
}
