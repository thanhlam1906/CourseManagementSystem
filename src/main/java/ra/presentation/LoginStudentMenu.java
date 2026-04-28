package ra.presentation;

import ra.business.IStudentAuthService;
import ra.business.impl.StudentAuthServiceImpl;
import ra.dto.StudentLoginDTO;
import ra.model.Student;
import ra.presentation.student.StudentMenu;

import java.util.Scanner;

public class LoginStudentMenu {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int MAX_ATTEMPTS = 3;
    private final IStudentAuthService studentAuthService;

    public LoginStudentMenu() {
        this.studentAuthService = new StudentAuthServiceImpl();
    }

    public LoginStudentMenu(IStudentAuthService studentAuthService) {
        this.studentAuthService = studentAuthService;
    }

    public void displayLoginStudentMenu() {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("\n====== DANG NHAP HOC VIEN ======");
            System.out.print("Email (0 de quay lai): ");
            String email = SCANNER.nextLine().trim();
            if ("0".equals(email)) {
                return;
            }
            System.out.print("Mat khau: ");
            String password = SCANNER.nextLine().trim();

            try {
                StudentLoginDTO dto = new StudentLoginDTO(email, password);
                Student student = studentAuthService.login(dto);
                if (student != null) {
                    System.out.println("Dang nhap thanh cong. Xin chao " + student.getName() + "!");
                    new StudentMenu(student.getId()).displayStudentMenu();
                    return;
                }
                attempts++;
                System.out.println("Sai email hoac mat khau. (Lan thu " + attempts + "/" + MAX_ATTEMPTS + ")");
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
