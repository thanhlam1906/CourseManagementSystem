package ra.presentation.admin;

import ra.business.impl.CourseServiceImpl;
import ra.business.impl.EnrollmentServiceImpl;
import ra.business.impl.StudentServiceImpl;
import ra.dto.CourseStatisticsDTO;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StatisticsMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final CourseServiceImpl courseService = new CourseServiceImpl();
    private final StudentServiceImpl studentService = new StudentServiceImpl();
    private final EnrollmentServiceImpl enrollmentService = new EnrollmentServiceImpl();

    public void displayStatisticsMenu() {
        while (true) {
            System.out.println("=== THONG KE DU LIEU ===");
            System.out.println("1. Thong ke tong so luong khoa hoc va tong so hoc vien");
            System.out.println("2. Thong ke so luong hoc vien dang ky theo tung khoa hoc");
            System.out.println("3. Thong ke top 5 khoa hoc nhieu hoc vien nhat");
            System.out.println("4. Liet ke khoa hoc co tren 10 hoc vien dang ky");
            System.out.println("0. Quay lai");
            System.out.print("Lua chon chuc nang: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    showTotalCoursesAndStudents();
                    break;
                case 2:
                    showStudentCountByCourse();
                    break;
                case 3:
                    showTop5Courses();
                    break;
                case 4:
                    showCoursesMoreThan10Students();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }

    private void showTotalCoursesAndStudents() {
        int totalCourses = courseService.getAllCourses().size();
        int totalStudents = studentService.getAllStudents().size();
        System.out.println("\n--- THONG KE TONG QUAN ---");
        System.out.println("Tong so khoa hoc: " + totalCourses);
        System.out.println("Tong so hoc vien: " + totalStudents);
    }

    private void showStudentCountByCourse() {
        System.out.println("\n--- SO LUONG HOC VIEN THEO TUNG KHOA HOC ---");
        printCourseStats(buildCourseStatistics());
    }

    private void showTop5Courses() {
        System.out.println("\n--- TOP 5 KHOA HOC NHIEU HOC VIEN NHAT ---");
        List<CourseStatisticsDTO> top5 = buildCourseStatistics().stream()
                .sorted(Comparator.comparingInt((CourseStatisticsDTO dto) -> dto.getTotalStudents() == null ? 0 : dto.getTotalStudents())
                        .reversed()
                        .thenComparing(dto -> dto.getCourseId() == null ? 0 : dto.getCourseId()))
                .limit(5)
                .toList();

        printCourseStats(top5);
    }

    private void showCoursesMoreThan10Students() {
        System.out.println("\n--- KHOA HOC CO TREN 10 HOC VIEN ---");
        List<CourseStatisticsDTO> filtered = buildCourseStatistics().stream()
                .filter(dto -> (dto.getTotalStudents() == null ? 0 : dto.getTotalStudents()) > 10)
                .sorted(Comparator.comparingInt((CourseStatisticsDTO dto) -> dto.getTotalStudents() == null ? 0 : dto.getTotalStudents())
                        .reversed()
                        .thenComparing(dto -> dto.getCourseId() == null ? 0 : dto.getCourseId()))
                .toList();

        printCourseStats(filtered);
    }

    private List<CourseStatisticsDTO> buildCourseStatistics() {
        return enrollmentService.getCourseStatistics();
    }

    private void printCourseStats(List<CourseStatisticsDTO> rows) {
        if (rows == null || rows.isEmpty()) {
            System.out.println("Khong co du lieu de hien thi.");
            return;
        }

        String headerFormat = "| %-5s | %-8s | %-40s | %-15s |%n";
        String rowFormat = "| %-5d | %-8d | %-40s | %-15d |%n";
        String line = "-------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "ID", "Ten khoa hoc", "So hoc vien");
        System.out.println(line);

        for (int i = 0; i < rows.size(); i++) {
            CourseStatisticsDTO row = rows.get(i);
            int courseId = row.getCourseId() == null ? 0 : row.getCourseId();
            String courseName = row.getCourseName() == null ? "(khong co ten)" : row.getCourseName();
            int totalStudents = row.getTotalStudents() == null ? 0 : row.getTotalStudents();
            System.out.printf(rowFormat, i + 1, courseId, courseName, totalStudents);
        }

        System.out.println(line);
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Loi: Vui long nhap so: ");
            }
        }
    }
}