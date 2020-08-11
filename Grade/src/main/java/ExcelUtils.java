import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    /**
     * 读取Excel文件
     * @param path
     * @param sheet
     * @return
     */
    public static List<Map<String, Object>> readToList(String path,String sheet) {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(path), sheet);
        List<Map<String, Object>> readAll = reader.readAll();
        reader.close();
        return readAll;
    }
    /**
     * 读取Excel文件
     * @param bookStream
     * @param sheet
     * @return
     *
     */
    public static List<Map<String, Object>> readToList(InputStream bookStream, String sheet) {
        ExcelReader reader = ExcelUtil.getReader(bookStream, sheet);
        List<Map<String, Object>> readAll = reader.readAll();
        reader.close();
        return readAll;
    }
}
