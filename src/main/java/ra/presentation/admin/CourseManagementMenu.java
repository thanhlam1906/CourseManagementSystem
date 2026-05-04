package ra.presentation.admin;

import ra.business.ICourseService;
import ra.business.impl.CourseServiceImpl;
import ra.dto.CourseDTO;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CourseManagementMenu {
    private ICourseService courseService = new CourseServiceImpl();
    private Scanner scanner =  new Scanner(System.in);

    public void  displayCourseMNGMenu(){
        while(true){
            System.out.println("===== QUẢN LÝ KHÓA HỌC =====");
            System.out.println("1. Danh sách | 2. Thêm | 3. Sửa | 4. Xóa | 5. Tìm Kiếm | 6. Sap xếp | 0. Quay lại");
            System.out.print("Vui lòng chọn chức năng:");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Lựa chọn phải là số. Vui lòng chọn lại.");
                continue;
            }
            switch (choice){
                case 1:
                    listCourses();
                    break;
                case 2:
                    addCourse();
                    break;
                case 3:
                    updateCourse();
                   break;
               case 4:
                    deleteCourse();
                    break;
               case 5:
                    searchCourses();
                    break;
               case 6:
                    sortCourses();
                     break;
               case 0:
                    return;
               default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ. Vui lòng chọn lại.");

            }
        }
    }

    private void sortCourses() {
        System.out.println("\n--------- SẮP XẾP KHÓA HỌC ---------");

        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            if (courses == null || courses.isEmpty()) {
                System.out.println("Không có khóa học nào để sắp xếp.");
                return;
            }
            while (true){
                displayCourseList(courses);
                System.out.println("1. Sắp xếp theo tên khóa học (A-Z)");
                System.out.println("2. Sắp xếp theo tên khóa học (Z-A)");
                System.out.println("0. Quay lại");

                int choice = readInt("Vui lòng chọn cách sắp xếp: ");
                switch (choice){
                    case 1:
                        courses = courseService.sortNameCourses( true);
                        break;
                    case 2:
                        courses = courseService.sortNameCourses( false);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lỗi: Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Vui lòng nhập số.");
            return;
        }

    }

    private void searchCourses() {
        System.out.println("\n--------- TÌM KIẾM KHÓA HỌC ---------");
        System.out.print("Nhập tên khóa học cần tìm: ");
        String name = scanner.nextLine().trim();
        try {
            CourseDTO course = courseService.getCourseByName(name);
            if (course == null) {
                System.out.println("Không tìm thấy khóa học với tên: " + name);
            } else {
                System.out.println("Khóa học tìm thấy:");
                System.out.println("Tên khóa học: " + course.getCourseName());
                System.out.println("Thời lượng: " + course.getDuration() + " giờ");
                System.out.println("Giảng viên: " + course.getInstructor());
                if (course.getCreateAt() != null) {
                    System.out.println("Ngày tạo: " + course.getCreateAt());
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void listCourses() {
        System.out.println("\n---------  DANH SÁCH KHÓA HỌC ---------");
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            displayCourseList(courses);
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void displayCourseList(List<CourseDTO> courses) {
        if (courses == null || courses.isEmpty()) {
            System.out.println("Không có khóa học nào.");
            return;
        }

        String headerFormat = "| %-5s | %-5s | %-40s | %-15s | %-25s | %-15s |\n";
        String rowFormat    = "| %-5d | %-5d | %-40s | %-15d | %-25s | %-15s |\n";
        String line = "-----------------------------------------------------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");
        System.out.println(line);

        IntStream.range(0, courses.size())
                .forEach(i -> {
                    CourseDTO course = courses.get(i);
                    String createAtStr = course.getCreateAt() != null ? course.getCreateAt().toString() : "";
                    System.out.printf(rowFormat,
                            (i + 1),
                            course.getId(),
                            course.getCourseName(),
                            course.getDuration(),
                            course.getInstructor(),
                            createAtStr);
                });

        System.out.println(line);
    }

    public void addCourse(){
        System.out.println("\n--------- THÊM KHÓA HỌC MỚI ---------");
        try {
            System.out.print("Tên khóa học: ");
            String name = scanner.nextLine().trim();

            System.out.print("Thời lượng (giờ): ");
            int duration = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Giảng viên: ");
            String instructor = scanner.nextLine().trim();
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseName(name);
            courseDTO.setDuration(duration);
            courseDTO.setInstructor(instructor);

            boolean success = courseService.addCourse(courseDTO);
            if (success) {
                System.out.println("Thêm khóa học thành công!");
            } else {
                System.out.println("Thêm khóa học thất bại.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }
    }
    public void updateCourse(){
        System.out.print("\nNhập ID khóa học cần sửa: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: ID phải là số nguyên.");
            return;
        }

        CourseDTO existingCourse;
        try {
            existingCourse = courseService.getCourseById(id);
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
            return;
        }
        if (existingCourse == null) {
            System.out.println("Không tìm thấy khóa học với ID: " + id);
            return;
        }
        boolean isEditing = true;
        while (isEditing) {
            System.out.println("\n--------- SỬA THÔNG TIN KHÓA HỌC " + existingCourse.getCourseName() + " ---------");
            System.out.println("1. Tên khóa học: " + existingCourse.getCourseName());
            System.out.println("2. Thời lượng: " + existingCourse.getDuration());
            System.out.println("3. Giảng viên: " + existingCourse.getInstructor());
            System.out.println("0. Lưu và quay lại");
            System.out.print("Vui lòng chọn thông tin cần sửa (0 để lưu): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập số.");
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.print("Nhập tên khóa học mới: ");
                    String newName = scanner.nextLine().trim();
                    existingCourse.setCourseName(newName);
                    break;
                case 2:
                    System.out.print("Nhập thời lượng mới (giờ): ");
                    try {
                        existingCourse.setDuration(Integer.parseInt(scanner.nextLine().trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Thời lượng phải là số nguyên.");
                    }
                    break;
                case 3:
                    System.out.print("Nhập tên giảng viên mới: ");
                    String newInstructor = scanner.nextLine().trim();
                    existingCourse.setInstructor(newInstructor);
                    break;
                case 0:
                    try {
                        boolean success = courseService.updateCourse(existingCourse);
                        if (success) {
                            System.out.println("Cập nhật khóa học thành công!");
                        } else {
                            System.out.println("Cập nhật khóa học thất bại.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Lỗi: " + e.getMessage());
                    } catch (Exception e) {
                        System.err.println("Lỗi hệ thống: " + e.getMessage());
                    }
                    isEditing = false;
                    break;
                default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ. Vui lòng chọn lại.");

            }
        }
    }
    public void deleteCourse(){
        System.out.println("\n Xóa khóa học");
        System.out.print("Nhập ID khóa học cần xóa: ");
        String id = scanner.nextLine().trim();
        try {
            boolean success = courseService.deleteCourse(id);
            if (success) {
                System.out.println("Xóa khóa học thành công!");
            } else {
                System.out.println("Xóa khóa học thất bại. Có thể khóa học không tồn tại.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }
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
}
