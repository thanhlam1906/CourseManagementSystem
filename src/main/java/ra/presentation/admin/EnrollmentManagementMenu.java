package ra.presentation.admin;

import ra.business.impl.EnrollmentServiceImpl;
import ra.dto.EnrollmentDTO;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


public class EnrollmentManagementMenu {
    private static final EnrollmentServiceImpl enrollmentService = new EnrollmentServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);
    public void displayEnrollmentMNGMenu(){
        while(true){
            System.out.println("======= QUẢN LÍ ĐĂNG KÍ KHÓA HỌC =======");
            System.out.println("1. Danh sách đăng kí | 2. Duyệt đăng kí khóa học | 3. Xóa sinh viên khỏi khóa học  | 0. Quay lại");
            System.out.println("Vui lòng chọn chức năng:");
             int choice = readInt();
                switch (choice){
                    case 1:
                        listEnrollments();
                        break;
                    case 2:
                        approveEnrollment();
                        break;
                    case 3:
                        deleteEnrollment();
                        break;
                    case 4:
                        StatisticsMenu statisticsMenu = new StatisticsMenu();
                        statisticsMenu.displayStatisticsMenu();
                    case 0:
                        return;
                    default:
                        System.out.println("Lỗi: Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
        }
    }

    private void deleteEnrollment() {
        System.out.println("\n--------- XÓA SINH VIÊN KHỎI KHÓA HỌC ---------");
        try{
            List<EnrollmentDTO> enrollments = enrollmentService.listAllApprovedEnrollments();
            displayCourseEnrollments(enrollments);
            if(enrollments == null || enrollments.isEmpty()){
                System.out.println("Khong co sinh vien nao dang ky khoa hoc nay.");
                return;
            }
            System.out.print("Nhap ID phien  đăng kí muon xoa:");
            int enrollmentId = Integer.parseInt(scanner.nextLine());
            EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
            enrollmentDTO.setEnrollment_id(enrollmentId);
            boolean success = enrollmentService.deleteEnrollmentByEnrollmentId(enrollmentDTO);
            if(success){
                System.out.println("Xoa sinh vien khoi khoa hoc thanh cong.");
            }else{
                System.out.println("Xoa sinh vien khoi khoa hoc that bai. Vui long kiem tra lai ID dang ki.");
            }

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }


    private void approveEnrollment() {
        System.out.println("\n--------- KHÓA HỌC CHƯA DUYỆT ---------");
        try{
            System.out.println("Hien thi danh sach dang ki khoa hoc chua duyet:");
            List<EnrollmentDTO> pendingEnrollments = enrollmentService.listAllWaitingEnrollments();
                if(pendingEnrollments == null || pendingEnrollments.isEmpty()){
                    System.out.println("Khong co dang ki khoa hoc nao de duyet.");
                    return;
                }
                displayPendingEnrollments(pendingEnrollments);
                while (true){
                    System.out.println("1.duyet dang ki");
                    System.out.println("2.tu choi dang ki");
                    System.out.println("0. quay lai");
                    System.out.println("Vui long chon chuc nang:");
                    int choice = readInt();
                    switch (choice){
                        case 1:
                            System.out.print("Nhap ID phien  đăng kí:");
                            int enrollmentId = Integer.parseInt(scanner.nextLine());
                            EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
                                enrollmentDTO.setEnrollment_id(enrollmentId);
                                boolean sucess = enrollmentService.approveEnrollment(enrollmentDTO);

                            if(sucess){
                                System.out.println("Duyet dang ki khoa hoc thanh cong.");
                            }else{
                                System.out.println("Duyet dang ki khoa hoc that bai. Vui long kiem tra lai ID dang ki.");
                            }
                            break;
                        case 2:
                            System.out.print("Nhap ID phien  đăng kí:");
                            int denyEnrollmentId = Integer.parseInt(scanner.nextLine());
                            EnrollmentDTO denyEnrollmentDTO = new EnrollmentDTO();
                            denyEnrollmentDTO.setEnrollment_id(denyEnrollmentId);
                            boolean denySuccess = enrollmentService.denyEnrollment(denyEnrollmentDTO);

                            if(denySuccess){
                                System.out.println("Tu choi dang ki khoa hoc thanh cong.");
                            }else{
                                System.out.println("Tu choi dang ki khoa hoc that bai. Vui long kiem tra lai ID dang ki.");
                            }
                            break;
                        case 0:
                            return;
                        default:
                            System.out.println("Lỗi: Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    }
                }
        }catch (NumberFormatException e){
            throw new RuntimeException(e);
        }
    }

    private void listEnrollments() {
        System.out.println("\n--------- DANH SÁCH ĐĂNG KÍ KHÓA HỌC ---------");
        try{
            System.out.println("Nhap ID khóa học để xem danh sách đăng kí:");
            int courseId = Integer.parseInt(scanner.nextLine());
            List<EnrollmentDTO> enrollments = enrollmentService.listNameStudentRegistedCourse(courseId);
            displayCourseEnrollments(enrollments);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
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
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
