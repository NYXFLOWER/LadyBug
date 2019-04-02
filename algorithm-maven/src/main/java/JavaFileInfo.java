public class JavaFileInfo {
    private String StudentID;
    private String FileName;
    private String FullName;

    public JavaFileInfo(String studentID, String fileName, String fullName) {
        this.StudentID = studentID;
        this.FileName = fileName;
        this.FullName = fullName;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getFileName() {
        return FileName;
    }

    public String getFullName() {
        return FullName;
    }
}
