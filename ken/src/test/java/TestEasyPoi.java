///******************************************************************************
// *
// * 作者（author）：ken
// * 微信（weChat）：mlchao1992
// * 个人博客（website）：
// *
// ******************************************************************************
// * 注意：尊重原创
// *****************************************************************************/
//
//import cn.afterturn.easypoi.excel.ExcelExportUtil;
//import cn.afterturn.easypoi.excel.ExcelImportUtil;
//import cn.afterturn.easypoi.excel.entity.ExportParams;
//import cn.afterturn.easypoi.excel.entity.ImportParams;
//import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
//import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
//import cn.afterturn.easypoi.util.PoiPublicUtil;
//import org.junit.Test;
//
//import java.io.File;
//import java.util.*;
//
//public class TestEasyPoi {
//
//
//    /**
//     * 对象---直接导出(无需模板)
//     *
//     * 注:如果模型 的父类的属性也有@Excel注解，那么导出excel时，会连该模型的父类的属性也一会儿导出
//     *
//     * @author JustryDeng
//     * @date 2018/12/5 11:44
//     */
//    @Test
//    public void directExportExcelByObject() throws Exception {
//        try {
//            ImportParams params = new ImportParams();
//            params.setKeyMark("：");
//            params.setReadSingleCell(true);
//            params.setTitleRows(7);
//            params.setLastOfInvalidRow(9);
//            ExcelImportResult<Map> result = ExcelImportUtil.importExcelMore(
//                    new File(""),
//                    Map.class, params);
//            for (int i = 0; i < result.getList().size(); i++) {
//                System.out.println(result.getList().get(i));
//            }
//            System.out.println(result.getMap());
//        } catch (Exception e) {
//
//        }
//
//    }
//}
