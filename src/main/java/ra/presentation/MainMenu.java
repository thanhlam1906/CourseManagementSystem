package ra.presentation;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        LoginMenu loginMenu = new LoginMenu();

        while (true) {
            System.out.println("=== HE THONG QUAN LY DAO TAO ===");
            System.out.println("1. Dang nhap voi tu cach quan tri vien");
            System.out.println("2. Dang nhap voi tu cach hoc vien");
            System.out.println("0. Thoat");
            System.out.print("Lua chon cua ban: ");

            int choice;
            try {
                choice = Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so tu 0 den 2.");
                continue;
            }

            switch (choice) {
                case 1:
                    loginMenu.login("ADMIN");
                    break;
                case 2:
                    loginMenu.login("STUDENT");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Loi: Lua chon khong hop le.");
            }
        }
    }
}
