package ra.presentation;

import ra.presentation.admin.AdminMenu;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        while (true) {
            System.out.println("=== HỆ THỐNG QUẢN LÍ ĐÀO TẠO ===");
            System.out.println("1. Đăng nhập với tư cách quản trị viên");
            System.out.println("2. Đăng nhập với tư cách học sinh");
            System.out.print("Lựa chọn của bạn: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.displayAdminMenu();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }
}
