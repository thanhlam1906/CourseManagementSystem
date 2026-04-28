package ra.presentation.admin;

import java.util.Scanner;

public class AdminMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void displayAdminMenu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Quan li Khoa Hoc");
            System.out.println("2. Quan li Sinh Vien");
            System.out.println("3. Quan li Dang ki Khoa Hoc");
            System.out.println("4. Thong ke du lieu");
            System.out.println("5. Dang xuat");
            System.out.print("Lua chon chuc nang: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so tu 1 den 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    new CourseManagementMenu().displayCourseMNGMenu();
                    break;
                case 2:
                    new StudentManagementMenu().displayStudentMNGMenu();
                    break;
                case 3:
                    new EnrollmentManagementMenu().displayEnrollmentMNGMenu();
                    break;
                case 4:
                    new StatisticsMenu().displayStatisticsMenu();
                    break; // FIX: thieu break gay roi xuong case 5 (dang xuat)
                case 5:
                    System.out.println("Dang xuat...");
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Hay thu lai.");
            }

        }
    }
}
