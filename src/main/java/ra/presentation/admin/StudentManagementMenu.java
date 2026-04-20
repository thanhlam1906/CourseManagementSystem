package ra.presentation.admin;

import ra.business.impl.StudentServiceImpl;
import ra.dto.StudentDTO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class StudentManagementMenu {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^0\\d{9}$";

    private final Scanner scanner = new Scanner(System.in);
    private final StudentServiceImpl studentService = new StudentServiceImpl();

    public void displayStudentMNGMenu() {
        while (true) {
            System.out.println("===== QUAN LY SINH VIEN =====");
            System.out.println("1. Danh sach | 2. Them | 3. Sua | 4. Xoa | 5. Tim kiem | 6. Sap xep | 0. Quay lai");
            System.out.print("Vui long chon chuc nang: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so tu 0 den 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    listStudents();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    searchStudents();
                    break;
                case 6:
                    sortStudents();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Loi: Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }

    private void sortStudents() {
        boolean isSorting = true;
        while (isSorting) {
            System.out.println("\n--------- SAP XEP SINH VIEN THEO TEN ---------");
            System.out.println("1. Sap xep tang dan | 2. Sap xep giam dan | 0. Quay lai");
            System.out.print("Vui long chon chuc nang: ");
            int choice;


            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                boolean isIncrease = (choice == 1);
                        List<StudentDTO> sortedAsc = studentService.sortNameStudents(isIncrease);
                        displayStudentList(sortedAsc);
                        if (choice == 0) {
                            isSorting = false;
                        } else if (choice != 1 && choice != 2) {
                            System.out.println("Loi: Lua chon khong hop le. Vui long chon lai.");
                        }

            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so tu 0 den 2.");
                continue;
            }
        }
    }

    private void searchStudents() {
        boolean isSearching = true;
        while (isSearching) {
            System.out.println("\n--------- TIM KIEM SINH VIEN ---------");
            System.out.println("1. Tim theo ID | 2. Tim theo ten | 0. Quay lai");
            System.out.print("Vui long chon chuc nang: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so tu 0 den 2.");
                continue;
            }

            switch (choice) {
                case 1:
                    searchStudentById();
                    break;
                case 2:
                    searchStudentByName();
                    break;
                case 0:
                    isSearching = false;
                    break;
                default:
                    System.out.println("Loi: Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }

    private void updateStudent() {
        System.out.println("Nhap id sinh vien can sua");
        int id = Integer.parseInt(scanner.nextLine().trim());
        StudentDTO student = studentService.getStudentById(id);
        if (student == null) {
            System.out.println("Khong tim thay sinh vien voi id: " + id);
            return;
        }
        boolean isEditing = true;
        while (isEditing) {
            System.out.println("\n--------- SUA THONG TIN SINH VIEN " + student.getName() + " ---------");
            System.out.println("1. Ten sinh vien: " + student.getName());
            System.out.println("2. Ngay sinh: " + student.getDob());
            System.out.println("3. Email: " + student.getEmail());
            System.out.println("4. Gioi tinh: " + (student.getSex() == null ? "Khong xac dinh" : (student.getSex() ? "Nam" : "Nu")));
            System.out.println("5. So dien thoai: " + student.getPhone());
            System.out.println("0. Luu va quay lai");
            System.out.print("Vui long chon thong tin can sua (0 de luu): ");
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    System.out.print("Nhap ten sinh vien moi: ");
                    String newName = scanner.nextLine().trim();
                    student.setName(newName);
                    break;
                case 2:
                    System.out.print("Nhap ngay sinh moi (yyyy-MM-dd): ");
                    String dobInput = scanner.nextLine().trim();
                    LocalDate newDob = dobInput.isEmpty() ? null : LocalDate.parse(dobInput);
                    student.setDob(newDob);
                    break;
                case 3:
                    System.out.print("Nhap email moi: ");
                    String newEmail = scanner.nextLine().trim();
                    student.setEmail(newEmail);
                    break;
                case 4:
                    System.out.println("Chon gioi tinh moi: 1. Nam | 2. Nu");
                    int genderChoice = inputInt();
                    student.setSex(genderChoice == 1);
                    break;
                case 5:
                    System.out.print("Nhap so dien thoai moi: ");
                    String newPhone = scanner.nextLine().trim();
                    student.setPhone(newPhone);
                    break;
                case 0:
                    try {
                        boolean success = studentService.updateStudent(student);
                        if (success) {
                            System.out.println("Cap nhat sinh vien thanh cong!");
                        } else {
                            System.out.println("Cap nhat sinh vien that bai.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Loi: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Loi he thong: " + e.getMessage());
                    }
                    isEditing = false;
                    break;
                default:
                    System.out.println("Loi: Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }

    private void deleteStudent() {
        System.out.println("\n--------- XOA SINH VIEN ---------");
        try {
            System.out.print("Nhap ID sinh vien can xoa: ");
            String id = scanner.nextLine().trim();
            boolean success = studentService.deleteStudent(id);
            if (success) {
                System.out.println("Xoa sinh vien thanh cong!");
            } else {
                System.out.println("Xoa sinh vien that bai! Vui long kiem tra lai ID.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi he thong: " + e.getMessage());
        }
    }

    private void addStudent() {
        System.out.println("\n--------- THEM MOI SINH VIEN ---------");
        while (true) {
            try {
                String name = readRequiredInput("Nhap ten sinh vien: ", "Ten sinh vien khong duoc de trong.");
                LocalDate dob = readBirthDate();
                String email = readEmailInput();
                Boolean sex = readSexInput();
                String phone = readPhoneInput();
                String password = readRequiredInput("Nhap mat khau: ", "Mat khau khong duoc de trong.");
                LocalDate createAt = readOptionalDate("Nhap ngay tao (yyyy-MM-dd, bo trong de lay ngay hien tai): ");

                boolean success = studentService.addStudent(
                        new StudentDTO(name, dob, email, sex, phone, password, createAt)
                );

                if (success) {
                    System.out.println("Them sinh vien thanh cong!");
                } else {
                    System.out.println("Them sinh vien that bai!");
                }
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Loi: " + e.getMessage());
                System.out.println("Vui long nhap lai thong tin.");
            } catch (Exception e) {
                System.out.println("Loi he thong: " + e.getMessage());
                return;
            }
        }
    }

    private void listStudents() {
        System.out.println("===== DANH SACH SINH VIEN =====");
        try {
            List<StudentDTO> students = studentService.getAllStudents();
            displayStudentList(students);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void displayStudentList(List<StudentDTO> students) {
        if (students == null || students.isEmpty()) {
            System.out.println("Khong co sinh vien nao de hien thi.");
            return;
        }

        String headerFormat = "| %-5s | %-5s | %-25s | %-15s | %-30s | %-10s | %-20s | %-20s |%n";
        String rowFormat = "| %-5d | %-5d | %-25s | %-15s | %-30s | %-10s | %-20s | %-20s |%n";
        String line = "-----------------------------------------------------------------------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "ID", "Ten hoc vien", "Ngay sinh", "Email", "Gioi tinh", "SDT", "Ngay tao");
        System.out.println(line);

        IntStream.range(0, students.size())
                .forEach(i -> {
                    StudentDTO student = students.get(i);
                    String sexStr = student.getSex() == null ? "Khong xac dinh" : (student.getSex() ? "Nam" : "Nu");
                    System.out.printf(rowFormat,
                            i + 1,
                            student.getId(),
                            student.getName(),
                            student.getDob(),
                            student.getEmail(),
                            sexStr,
                            student.getPhone(),
                            student.getCreateAt());
                });
        System.out.println(line);
    }

    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Loi: Vui long nhap so: ");
            }
        }
    }

    private void searchStudentById() {
        while (true) {
            System.out.print("Nhap ID sinh vien can tim: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Loi: ID khong duoc de trong.");
                continue;
            }

            try {
                int id = Integer.parseInt(input);
                StudentDTO student = studentService.getStudentById(id);
                displayStudentList(List.of(student));
                return;
            } catch (NumberFormatException e) {
                System.out.println("Loi: ID phai la so nguyen.");
            } catch (IllegalArgumentException e) {
                System.out.println("Loi: " + e.getMessage());
                return;
            } catch (Exception e) {
                System.out.println("Loi he thong: " + e.getMessage());
                return;
            }
        }
    }

    private void searchStudentByName() {
        String keyword = readRequiredInput("Nhap ten sinh vien can tim: ", "Ten sinh vien khong duoc de trong.");

        try {
            List<StudentDTO> matchedStudents = studentService.getAllStudents().stream()
                    .filter(student -> student.getName() != null
                            && student.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();

            if (matchedStudents.isEmpty()) {
                System.out.println("Khong tim thay sinh vien voi ten: " + keyword);
                return;
            }

            displayStudentList(matchedStudents);
        } catch (Exception e) {
            System.out.println("Loi he thong: " + e.getMessage());
        }
    }

    private String readRequiredInput(String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Loi: " + errorMessage);
        }
    }

    private LocalDate readBirthDate() {
        while (true) {
            System.out.print("Nhap ngay sinh (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Loi: Ngay sinh khong duoc de trong.");
                continue;
            }
            try {
                LocalDate dob = LocalDate.parse(input);
                if (dob.isAfter(LocalDate.now())) {
                    System.out.println("Loi: Ngay sinh khong hop le.");
                    continue;
                }
                return dob;
            } catch (DateTimeParseException e) {
                System.out.println("Loi: Ngay sinh phai dung dinh dang yyyy-MM-dd.");
            }
        }
    }

    private LocalDate readOptionalDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Loi: Ngay phai dung dinh dang yyyy-MM-dd.");
            }
        }
    }

    private String readEmailInput() {
        while (true) {
            String email = readRequiredInput("Nhap email: ", "Email khong duoc de trong.");
            if (!email.matches(EMAIL_REGEX)) {
                System.out.println("Loi: Email khong dung dinh dang.");
                continue;
            }
            boolean emailExists = studentService.getAllStudents().stream()
                    .anyMatch(student -> email.equalsIgnoreCase(student.getEmail()));
            if (emailExists) {
                System.out.println("Loi: Email da ton tai.");
                continue;
            }
            return email;
        }
    }

    private Boolean readSexInput() {
        while (true) {
            System.out.print("Nhap gioi tinh (1. Nam | 2. Nu): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if ("1".equals(input) || "nam".equals(input)) {
                return true;
            }
            if ("2".equals(input) || "nu".equals(input)) {
                return false;
            }
            System.out.println("Loi: Gioi tinh chi duoc nhap 1, 2, Nam hoac Nu.");
        }
    }

    private String readPhoneInput() {
        while (true) {
            String phone = readRequiredInput("Nhap so dien thoai: ", "So dien thoai khong duoc de trong.");
            if (!phone.matches(PHONE_REGEX)) {
                System.out.println("Loi: So dien thoai phai gom 10 chu so va bat dau bang 0.");
                continue;
            }
            return phone;
        }
    }
}
