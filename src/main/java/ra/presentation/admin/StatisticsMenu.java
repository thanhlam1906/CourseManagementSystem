package ra.presentation.admin;

import ra.business.impl.CourseServiceImpl;
import ra.business.impl.EnrollmentServiceImpl;
import ra.business.impl.StudentServiceImpl;
import ra.dto.CourseDTO;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        List<CourseDTO> courses = courseService.getAllCourses();
        Map<Integer, String> courseNames = buildCourseNameMap(courses);
        Map<Integer, Integer> courseCounts = buildCourseCountMap(courses);
        List<Integer> orderedCourseIds = courses.stream().map(CourseDTO::getId).toList();
        printCourseStats(orderedCourseIds, courseNames, courseCounts);
    }

    private void showTop5Courses() {
        System.out.println("\n--- TOP 5 KHOA HOC NHIEU HOC VIEN NHAT ---");
        List<CourseDTO> courses = courseService.getAllCourses();
        Map<Integer, String> courseNames = buildCourseNameMap(courses);
        Map<Integer, Integer> courseCounts = buildCourseCountMap(courses);

        List<Integer> top5 = courseCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .map(Map.Entry::getKey)
                .limit(5)
                .toList();

        printCourseStats(top5, courseNames, courseCounts);
    }

    private void showCoursesMoreThan10Students() {
        System.out.println("\n--- KHOA HOC CO TREN 10 HOC VIEN ---");
        List<CourseDTO> courses = courseService.getAllCourses();
        Map<Integer, String> courseNames = buildCourseNameMap(courses);
        Map<Integer, Integer> courseCounts = buildCourseCountMap(courses);

        List<Integer> filteredCourseIds = courseCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 10)
                .sorted(Map.Entry.<Integer, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .map(Map.Entry::getKey)
                .toList();

        printCourseStats(filteredCourseIds, courseNames, courseCounts);
    }

    private Map<Integer, String> buildCourseNameMap(List<CourseDTO> courses) {
        return courses.stream().collect(Collectors.toMap(
                CourseDTO::getId,
                course -> course.getCourseName() == null ? "(khong co ten)" : course.getCourseName(),
                (oldValue, newValue) -> oldValue
        ));
    }

    private Map<Integer, Integer> buildCourseCountMap(List<CourseDTO> courses) {
        return courses.stream().collect(Collectors.toMap(
                CourseDTO::getId,
                course -> enrollmentService.listNameStudentRegistedCourse(course.getId()).size(),
                (oldValue, newValue) -> oldValue
        ));
    }

    private void printCourseStats(List<Integer> courseIds, Map<Integer, String> courseNames, Map<Integer, Integer> courseCounts) {
        if (courseIds == null || courseIds.isEmpty()) {
            System.out.println("Khong co du lieu de hien thi.");
            return;
        }

        String headerFormat = "| %-5s | %-8s | %-40s | %-15s |%n";
        String rowFormat = "| %-5d | %-8d | %-40s | %-15d |%n";
        String line = "-------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "ID", "Ten khoa hoc", "So hoc vien");
        System.out.println(line);

        for (int i = 0; i < courseIds.size(); i++) {
            Integer courseId = courseIds.get(i);
            String courseName = courseNames.getOrDefault(courseId, "(khong co ten)");
            int totalStudents = courseCounts.getOrDefault(courseId, 0);
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