package ra.presentation.admin;

import ra.business.impl.EnrollmentServiceImpl;
import ra.dto.EnrollmentDTO;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


public class EnrollmentManagementMenu {
    private static final EnrollmentServiceImpl enrollmentService = new EnrollmentServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public void displayEnrollmentMNGMenu() {
        while (true) {
            System.out.println("\n======= QUAN LI DANG KI KHOA HOC =======");
            System.out.println("1. Danh sach dang ki theo khoa hoc");
            System.out.println("2. Duyet dang ki khoa hoc");
            System.out.println("3. Xoa sinh vien khoi khoa hoc");
            System.out.println("0. Quay lai");
            System.out.print("Vui long chon chuc nang: ");
            int choice = readInt();
            switch (choice) {
                case 1:
                    listEnrollments();
                    break;
                case 2:
                    approveEnrollment();
                    break;
                case 3:
                    deleteEnrollment();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Loi: Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }

    private void deleteEnrollment() {
        System.out.println("\n--------- XOA SINH VIEN KHOI KHOA HOC ---------");
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.listAllApprovedEnrollments();
            if (enrollments == null || enrollments.isEmpty()) {
                System.out.println("Khong co sinh vien da duyet de xoa.");
                return;
            }
            displayCourseEnrollments(enrollments);

            int enrollmentId = readPositiveInt("Nhap ID phien dang ki muon xoa: ");
            if (enrollmentId <= 0) return;

            EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
            enrollmentDTO.setEnrollment_id(enrollmentId);
            boolean success = enrollmentService.deleteEnrollmentByEnrollmentId(enrollmentDTO);
            if (success) {
                System.out.println("Xoa sinh vien khoi khoa hoc thanh cong.");
            } else {
                System.out.println("Xoa that bai. Vui long kiem tra lai ID dang ki.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi he thong: " + e.getMessage());
        }
    }

    private void approveEnrollment() {
        System.out.println("\n--------- KHOA HOC CHUA DUYET ---------");
        try {
            List<EnrollmentDTO> pendingEnrollments = enrollmentService.listAllWaitingEnrollments();
            if (pendingEnrollments == null || pendingEnrollments.isEmpty()) {
                System.out.println("Khong co dang ki khoa hoc nao de duyet.");
                return;
            }
            displayPendingEnrollments(pendingEnrollments);

            while (true) {
                System.out.println("1. Duyet dang ki");
                System.out.println("2. Tu choi dang ki");
                System.out.println("0. Quay lai");
                System.out.print("Vui long chon chuc nang: ");
                int choice = readInt();
                switch (choice) {
                    case 1:
                        handleApproval(true);
                        break;
                    case 2:
                        handleApproval(false);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Loi: Lua chon khong hop le. Vui long chon lai.");
                }
            }
        } catch (Exception e) {
            System.out.println("Loi he thong: " + e.getMessage());
        }
    }

    private void handleApproval(boolean approve) {
        try {
            int enrollmentId = readPositiveInt("Nhap ID phien dang ki: ");
            if (enrollmentId <= 0) return;
            EnrollmentDTO dto = new EnrollmentDTO();
            dto.setEnrollment_id(enrollmentId);
            boolean success = approve
                    ? enrollmentService.approveEnrollment(dto)
                    : enrollmentService.denyEnrollment(dto);
            String action = approve ? "Duyet" : "Tu choi";
            if (success) {
                System.out.println(action + " dang ki thanh cong.");
            } else {
                System.out.println(action + " that bai. Vui long kiem tra lai ID dang ki.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi he thong: " + e.getMessage());
        }
    }

    private void listEnrollments() {
        System.out.println("\n--------- DANH SACH DANG KI KHOA HOC ---------");
        try {
            int courseId = readPositiveInt("Nhap ID khoa hoc de xem danh sach dang ki: ");
            if (courseId <= 0) return;
            List<EnrollmentDTO> enrollments = enrollmentService.listNameStudentRegistedCourse(courseId);
            displayCourseEnrollments(enrollments);
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Loi he thong: " + e.getMessage());
        }
    }

    private void displayCourseEnrollments(List<EnrollmentDTO> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("Khong co sinh vien nao dang ky khoa hoc nay.");
            return;
        }

        String headerFormat = "| %-5s | %-8s | %-40s | %-35s |%n";
        String rowFormat = "| %-5d | %-8d | %-40s | %-35s |%n";
        String line = "------------------------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "Ma DK", "Ten hoc sinh", "Ten khoa hoc");
        System.out.println(line);

        IntStream.range(0, enrollments.size())
                .forEach(i -> {
                    EnrollmentDTO enrollment = enrollments.get(i);
                    String studentName = enrollment.getStudent() != null ? enrollment.getStudent().getName() : "(khong co du lieu)";
                    String courseName = enrollment.getCourse() != null ? enrollment.getCourse().getName() : (enrollment.getCourseName() != null ? enrollment.getCourseName() : "(khong co du lieu)");
                    System.out.printf(
                            rowFormat,
                            i + 1,
                            enrollment.getEnrollment_id(),
                            studentName,
                            courseName
                    );
                });

        System.out.println(line);
    }

    private void displayPendingEnrollments(List<EnrollmentDTO> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("Khong co dang ky nao dang cho duyet.");
            return;
        }

        String headerFormat = "| %-5s | %-8s | %-20s | %-40s | %-10s |%n";
        String rowFormat = "| %-5d | %-8d | %-20s | %-40s | %-10s |%n";
        String line = "----------------------------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "Ma DK", "Ten hoc sinh", "Ten khoa hoc", "Trang thai");
        System.out.println(line);

        IntStream.range(0, enrollments.size())
                .forEach(i -> {
                    EnrollmentDTO enrollment = enrollments.get(i);
                    String studentName = enrollment.getStudent() != null ? enrollment.getStudent().getName() : "(khong co du lieu)";
                    String courseName = enrollment.getCourse() != null ? enrollment.getCourse().getName() : (enrollment.getCourseName() != null ? enrollment.getCourseName() : "(khong co du lieu)");
                    String status = enrollment.getStatus() != null ? enrollment.getStatus() : "waiting";
                    System.out.printf(rowFormat, i + 1, enrollment.getEnrollment_id(), studentName, courseName, status);
                });

        System.out.println(line);
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int readPositiveInt(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            if (value <= 0) {
                System.out.println("Loi: ID phai la so duong.");
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Loi: Vui long nhap so nguyen hop le.");
            return -1;
        }
    }
}
