package ra.presentation.admin;

public class AdminMenu {

    public void displayAdminMenu(){
        while (true){
            System.out.println("=== ADMIN MENU ===");
            System.out.println("1. Quản lí Khoá Học");
            System.out.println("2. Quản lí Sinh Viên");
            System.out.println("3. Quán lí Giảng Viên");
            System.out.println("4. Đăng xuất");
            System.out.print("Please select an option: ");
            int choice = new java.util.Scanner(System.in).nextInt();
            switch (choice) {
                case 1:
                    CourseManagementMenu courseMenu = new CourseManagementMenu();
                    courseMenu.displayCourseMNGMenu();
                    break;
                case 2:
                    // Call method to manage students
                    break;
                case 3:
                    // Call method to manage instructors
                    break;
                case 4:
                    System.out.println("Đăng xuất...");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Hãy thử lại ");
            }

        }
    }
}
