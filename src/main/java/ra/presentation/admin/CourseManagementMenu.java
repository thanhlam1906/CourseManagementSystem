package ra.presentation.admin;

import ra.business.ICourseService;
import ra.business.impl.CourseServiceImpl;
import ra.model.dto.CourseDTO;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CourseManagementMenu {
    private ICourseService courseService = new CourseServiceImpl();
    private Scanner scanner =  new Scanner(System.in);

    public void displayCourseMNGMenu(){
        while(true){
            System.out.println("===== QUẢN LÝ KHÓA HỌC =====");
            System.out.println("1. Danh sách | 2. Thêm | 3. Sửa | 4. Xóa | 5. Tìm Kiếm | 6. Sap xếp | 0. Quay lại");
            System.out.print("Vui lòng chọn chức năng:");
            int choice = Integer.parseInt(scanner.nextLine());
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
        System.out.println("\n--------- SẮP XẾP KHÓA HỌC THEO TÊN ---------");
        try {
          List<CourseDTO> sortedCourses = courseService.sortNameCourses();
          if(sortedCourses != null && !sortedCourses.isEmpty()){
              displayCourseList(sortedCourses);
          }else{
              System.out.println("Không có khóa học nào để sắp xếp.");
          }

        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
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
        System.out.println("\n--------- HIỂN THỊ DANH SÁCH KHÓA HỌC ---------");
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
        String line = "-------------------------------------------------------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.printf(headerFormat, "STT", "ID", "Tên khóa học", "Thời lượng", "Giảng viên", "Ngày tạo");
        System.out.println(line);

        IntStream.range(0, courses.size())
                .forEach(i -> {
                    CourseDTO course = courses.get(i);
                    String createAtStr = course.getCreateAt() != null ? course.getCreateAt().toString() : "";
                    System.out.printf(rowFormat,
                            (i + 1),               // STT = index + 1
                            course.getId() != null ? course.getId() : 0,
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
        int id = Integer.parseInt(scanner.nextLine().trim());

        // Goi course len DTO truoc khi update
        CourseDTO existingCourse = courseService.getCourseById(id);
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
            System.out.println("Vui lòng chọn thông tin cần sửa (0 để lưu): ");
            int choice  = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    System.out.print("Nhập tên khóa học mới: ");
                    String newName = scanner.nextLine().trim();
                    existingCourse.setCourseName(newName);
                    break;
                case 2:
                    System.out.print("Nhập thời lượng mới (giờ): ");
                    int newDuration = Integer.parseInt(scanner.nextLine().trim());
                    existingCourse.setDuration(newDuration);
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
}
