package ra.presentation;

import ra.business.impl.AuthServiceImpl;
import ra.model.LoginResult;
import ra.presentation.admin.AdminMenu;

import java.util.Scanner;

public class LoginMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthServiceImpl authService = new AuthServiceImpl();

    public void login(String expectedRole) {
        while (true) {
            System.out.println("\n=== DANG NHAP " + expectedRole + " ===");
            System.out.print("Nhap email/ten dang nhap (0 de quay lai): ");
            String email = scanner.nextLine().trim();
            if ("0".equals(email)) {
                return;
            }

            System.out.print("Nhap mat khau: ");
            String password = scanner.nextLine().trim();

            try {
                LoginResult loginResult = authService.login(email, password);
                if (loginResult == null) {
                    System.out.println("Loi: Sai tai khoan hoac mat khau.");
                    continue;
                }
                if (!loginResult.hasRole(expectedRole)) {
                    System.out.println("Loi: Tai khoan nay khong thuoc vai tro " + expectedRole + ".");
                    continue;
                }

                System.out.println("Dang nhap thanh cong voi vai tro " + loginResult.getRole()
                        + " - ID: " + loginResult.getUserId());

                if ("ADMIN".equalsIgnoreCase(loginResult.getRole())) {
                    new AdminMenu().displayAdminMenu();
                }
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Loi: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Loi he thong: " + e.getMessage());
                return;
            }
        }
    }
}
