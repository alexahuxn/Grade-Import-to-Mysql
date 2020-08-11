import java.util.List;
import java.util.Map;

public class Students {

    private List<Map<String, Object>> studentsInfo;
    private String path;
    private String sheet;

    public Students(String newPath, String newSheet) {
        this.path = newPath;
        this.sheet = newSheet;
        this.studentsInfo = ExcelUtils.readToList(this.getPath(), this.getSheet());
    }

    public Students(List<Map<String, Object>> newInfo) {
        this.studentsInfo = newInfo;
    }

    public void setPath(String newPath) { this.path = newPath; }

    public void setSheet(String newSheet) { this.sheet = newSheet; }

    public String getPath() { return this.path; }

    public String getSheet() { return this.sheet; }

    public List<Map<String, Object>> getStudentsInfo() {
        return studentsInfo;
    }
}
