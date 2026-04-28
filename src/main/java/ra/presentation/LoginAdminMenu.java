package ra.presentation;

import ra.business.IAdminAuthService;
import ra.business.impl.AdminAuthServiceImpl;
import ra.dto.AdminLoginDTO;
import ra.model.Admin;
import ra.presentation.admin.AdminMenu;

import java.util.Scanner;

public class LoginAdminMenu {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int MAX_ATTEMPTS = 3;
    private final IAdminAuthService adminAuthService;

    public LoginAdminMenu() {
        this.adminAuthService = new AdminAuthServiceImpl();
    }

    public LoginAdminMenu(IAdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    public void displayLoginAdminMenu() {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("\n====== DANG NHAP ADMIN ======");
            System.out.print("Ten dang nhap (0 de quay lai): ");
            String username = SCANNER.nextLine().trim();
            if ("0".equals(username)) {
                return;
            }
            System.out.print("Mat khau: ");
            String password = SCANNER.nextLine().trim();

            try {
                AdminLoginDTO dto = new AdminLoginDTO(username, password);
                Admin admin = adminAuthService.login(dto);
                if (admin != null) {
                    System.out.println("Dang nhap thanh cong. Xin chao " + admin.getUsername() + "!");
                    new AdminMenu().displayAdminMenu();
                    return;
                }
                attempts++;
                System.out.println("Sai ten dang nhap hoac mat khau. (Lan thu " + attempts + "/" + MAX_ATTEMPTS + ")");
            } catch (IllegalArgumentException e) {
                System.out.println("Loi: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Loi he thong: " + e.getMessage());
                return;
            }
        }
        System.out.println("Ban da nhap sai qua so lan cho phep. Quay ve menu chinh.");
    }
}
