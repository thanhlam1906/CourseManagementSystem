package ra.presentation.student;

import ra.business.impl.CourseServiceImpl;
import ra.business.impl.EnrollmentServiceImpl;
import ra.business.impl.StudentServiceImpl;
import ra.dto.CourseDTO;
import ra.dto.EnrollmentDTO;
import ra.dto.StudentDTO;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StudentMenu {
    private static final CourseServiceImpl courseService = new CourseServiceImpl();
    private static final EnrollmentServiceImpl enrollmentService = new EnrollmentServiceImpl();
    private static final StudentServiceImpl studentService = new StudentServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);
    private final int studentId;

    public StudentMenu(int studentId) {
        this.studentId = studentId;
    }


    public void displayStudentMenu() {
        while (true) {
            System.out.println("=== STUDENT MENU ===");
            System.out.println("1. Xem danh sach khoa hoc");
            System.out.println("2. Dang ki khoa hoc");
            System.out.println("3. Xem khoa hoc da dang ki");
            System.out.println("4. Huy dang ki khoa hoc");
            System.out.println("5. Cap nhat mat khau");
            System.out.println("6. Dang xuat");

            int choice = readInt("Lua chon chuc nang: ");
            switch (choice) {
                case 1:
                    listAllCourse();
                    break;
                case 2:
                    registerCourse();
                    break;
                case 3:
                    listRegistedCourse();
                    break;
                case 4:
                    cancelRegistedCourse();
                    break;
                case 5:
                    changePassword();
                    break;
                case 6:
                    System.out.println("Dang xuat...");
                    return;
                default:
                    System.out.println("Lua chon khong hop le, hay thu lai.");
            }
        }
    }

    private void changePassword() {
        System.out.println("\n--------- DOI MAT KHAU ---------");
        System.out.print("Nhap mat khau hien tai: ");
        String currentPassword = scanner.nextLine().trim();
        System.out.print("Nhap email hoac so dien thoai: ");
        String emailOrPhone = scanner.nextLine().trim();
        System.out.print("Nhap mat khau moi: ");
        String newPassword = scanner.nextLine().trim();

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentId);
        studentDTO.setPassword(currentPassword);
        studentDTO.setPassword(newPassword);
        studentDTO.setEmail(emailOrPhone);
        studentDTO.setPhone(emailOrPhone);
        boolean isSuccess = studentService.changePassword(studentDTO);
        if (isSuccess) {
            System.out.println("Doi mat khau thanh cong.");
        } else {
            System.out.println("Doi mat khau that bai. Vui long kiem tra lai thong tin va thu lai.");
        }

    }

    private void cancelRegistedCourse() {
        int courseId = readInt("Nhap ID khoa hoc muon huy dang ki: ");
        if (courseId <= 0) {
            System.out.println("ID khoa hoc khong hop le.");
            return;
        }

        try {
            EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
            enrollmentDTO.setStudent_id(studentId);
            enrollmentDTO.setCourse_id(courseId);
            boolean isSuccess = enrollmentService.cancelCourse(enrollmentDTO);
            if (isSuccess) {
                System.out.println("Huy dang ki khoa hoc thanh cong.");
            } else {
                System.out.println("Huy dang ki that bai. Vui long kiem tra trang thai dang ky truoc do.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi he thong: " + e.getMessage());
        }
    }

    private void listRegistedCourse() {
        System.out.println("\n--------- DANH SACH KHOA HOC DA DANG KI ---------");
        try {
            List<EnrollmentDTO> registeredCourses = enrollmentService.listCourseRegistedById(studentId);
            if (registeredCourses == null || registeredCourses.isEmpty()) {
                System.out.println("Ban chua dang ki khoa hoc nao.");
                return;
            }

            while (true) {
                displayRegisterCourseList(registeredCourses);
                System.out.println("1. Theo ten (tang dan)");
                System.out.println("2. Theo ten (giam dan)");
                System.out.println("3. Theo ngay dang ky (tang dan)");
                System.out.println("4. Theo ngay dang ky (giam dan)");
                System.out.println("0. Quay lai");

                int choice = readInt("Lua chon cua ban: ");
                switch (choice) {
                    case 1:
                        registeredCourses = enrollmentService.listCourseRegistedById(studentId, "name", true);
                        break;
                    case 2:
                        registeredCourses = enrollmentService.listCourseRegistedById(studentId, "name", false);
                        break;
                    case 3:
                        registeredCourses = enrollmentService.listCourseRegistedById(studentId, "date", true);
                        break;
                    case 4:
                        registeredCourses = enrollmentService.listCourseRegistedById(studentId, "date", false);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lua chon khong hop le, vui long thu lai.");
                }
            }
        } catch (Exception e) {
            System.err.println("Loi he thong: " + e.getMessage());
        }
    }

    private void registerCourse() {
        int courseId = readInt("Nhap ID khoa hoc muon dang ky: ");
        if (courseId <= 0) {
            System.out.println("ID khoa hoc khong hop le.");
            return;
        }

        try {
            EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
            enrollmentDTO.setStudent_id(studentId);
            enrollmentDTO.setCourse_id(courseId);

            boolean isSuccess = enrollmentService.registerCourse(enrollmentDTO);
            if (isSuccess) {
                System.out.println("Dang ky khoa hoc thanh cong, vui long cho duyet.");
            } else {
                System.out.println("Dang ky that bai. Vui long kiem tra trang thai dang ky truoc do.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi he thong: " + e.getMessage());
        }
    }

    private void listAllCourse() {
        System.out.println("\n--------- DANH SACH KHOA HOC ---------");

        try {
            while (true) {
                List<CourseDTO> courses = courseService.getAllCourses();
                displayCourseList(courses);

                System.out.println("1. Tim kiem khoa hoc trong danh sach");
                System.out.println("0. Quay lai");
                int choice = readInt("Lua chon cua ban: ");

                switch (choice) {
                    case 1:
                        searchCourseInList(courses);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lua chon khong hop le, vui long thu lai.");
                }
            }
        } catch (Exception e) {
            System.err.println("Loi he thong: " + e.getMessage());
        }
    }

    private void searchCourseInList(List<CourseDTO> courses) {
        System.out.print("Nhap ten khoa hoc can tim: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("Tu khoa tim kiem khong duoc de trong.");
            return;
        }

        List<CourseDTO> matchedCourses = courses.stream()
                .filter(course -> course.getCourseName() != null
                        && course.getCourseName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\n--------- KET QUA TIM KIEM ---------");
        if (matchedCourses.isEmpty()) {
            System.out.println("Chua tim thay khoa hoc phu hop.");
            System.out.println("Phan nay hien tai chi o muc co ban de tranh loi giao dien.");
            return;
        }

        displayCourseList(matchedCourses);
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so hop le.");
            }
        }
    }

    private void displayCourseList(List<CourseDTO> courses) {
        if (courses == null || courses.isEmpty()) {
            System.out.println("Khong co khoa hoc nao.");
            return;
        }

        String headerFormat = "| %-5s | %-5s | %-40s | %-15s |%n";
        String rowFormat = "| %-5d | %-5d | %-40s | %-15d |%n";
        String line = "------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "ID", "Ten khoa hoc", "Thoi luong");
        System.out.println(line);

        IntStream.range(0, courses.size())
                .forEach(i -> {
                    CourseDTO course = courses.get(i);
                    System.out.printf(
                            rowFormat,
                            i + 1,
                            course.getId(),
                            course.getCourseName(),
                            course.getDuration()
                    );
                });

        System.out.println(line);
    }
    private void displayRegisterCourseList(List<EnrollmentDTO> courses) {
        if (courses == null || courses.isEmpty()) {
            System.out.println("Khong co khoa hoc nao.");
            return;
        }

        String headerFormat = "| %-5s | %-40s| %-10s | %-15s |%n";
        String rowFormat = "| %-5d | %-40s| %-10s  | %-15s |%n";
        String line = "-----------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT","Ten khoa hoc", "Trang thai" , "Thoi luong");
        System.out.println(line);

        IntStream.range(0, courses.size())
                .forEach(i -> {
                    EnrollmentDTO course = courses.get(i);
                    System.out.printf(
                            rowFormat,
                            i + 1,
                            course.getCourseName(),
                            course.getStatus(),
                            course.getCreateAt()
                    );
                });

        System.out.println(line);
    }
}
