public class JavaFileInfo {
    private String StudentID;
    private String FileName;

    public JavaFileInfo(String studentID, String fileName) {
        this.StudentID = studentID;
        this.FileName = fileName;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getFileName() {
        return FileName;
    }
}
