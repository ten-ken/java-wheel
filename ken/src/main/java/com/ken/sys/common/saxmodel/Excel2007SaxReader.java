/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/

package com.ken.sys.common.saxmodel;
import com.ken.sys.common.ifs.IExcelRowReader;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.*;

/**
 * <ul>
 * <li>Title: Excel2007SaxReader</li>
 * <li>Description: TODO </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author swc
 * @version 匠桥ERP系统V1.0
 * @date 2019/8/23 23:47
 */
public class Excel2007SaxReader extends DefaultHandler{
    //共享字符串表
    private SharedStringsTable sst;
    //上一次的内容
    private String lastContents;
    //判断是否是String
    private boolean nextIsString;
    //记录行数
    private int sheetIndex = -1;
    //每行结果集
    private List<Object> rowlist = new ArrayList<Object>();

    //判断是否是空单元格
    private boolean cellNull;
    //当前行
    private int curRow = 0;
    //当前列
    private int curCol = 0;
    //有效数据矩形区域,A1:Y2
    private String dimension;
    //根据dimension得出每行的数据长度
    private int longest;
    //上个有内容的单元格id，判断空单元格
    private String lastCellid;

    private boolean isTElement;

    private IExcelRowReader rowReader;

    public Excel2007SaxReader(IExcelRowReader rowReader) {
        this.rowReader = rowReader;
    }

    /**只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
     * @param inputStream
     * @param sheetId
     * @throws Exception
     */
    public void processOneSheet(InputStream inputStream,int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(inputStream);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId"+sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    /**
     * 遍历工作簿中所有的电子表格
     * @param in
     * @throws Exception
     */
    public void process(InputStream in) throws Exception {
        //获取实例对象
        OPCPackage pkg = OPCPackage.open(in);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            //查看转换的xml原始文件，方便理解后面解析时的处理,
            // 注意：如果打开注释，下面parse()就读不到流的内容了
            // this.streamOut(in);
            InputSource sheetSource = new InputSource(sheet);
            //据说当执行这个方法时  自动触发 startElement(开始的元素)    endElement(结束的元素)
            parser.parse(sheetSource);
            sheet.close();
        }
    }
//    //读取流，查看文件内容
//    public static void streamOut(InputStream in) throws Exception{
//        byte[] buf = new byte[1024];
//        int len;
//        while ((len=in.read(buf))!=-1){
//            System.out.write(buf,0,len);
//        }
//    }

    public XMLReader fetchSheetParser(SharedStringsTable sst)
            throws SAXException {
        XMLReader parser = XMLReaderFactory
                .createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }
    /*
    name  Excel转xml后的开始标签   这个方法 可以把参数都打出来看看
    */
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        if (name.equals("dimension")){
            dimension = attributes.getValue("ref");
            longest = covertRowIdtoInt(dimension.substring(dimension.indexOf(":")+1) );
        }
        // c => 表示是不是单元格  row=><row>:开始处理某一行   isTextTag(name)<v>:单元格值
        if ("c".equals(name)) {
            //当前单元格的位置
            String cellId = attributes.getValue("r");
            //空单元判断，添加空字符到list
            if (lastCellid!=null){
                int gap = covertRowIdtoInt(cellId)-covertRowIdtoInt(lastCellid);
                for(int i=0;i<gap-1;i++) {
                    rowlist.add(curCol, "");
                    curCol++;
                }
            }else{
                //第一个单元格可能不是在第一列
                if (!"A1".equals(cellId)){
                    for(int i=0;i<covertRowIdtoInt(cellId)-1;i++) {
                        rowlist.add(curCol, "");
                        curCol++;
                    }
                }
            }
            lastCellid = cellId;
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            //判断单元格的值是SST 的索引，不能直接characters方法取值
            if (attributes.getValue("t")!=null && attributes.getValue("t").equals("s")){
                nextIsString = true;
                cellNull=false;
            }else{
                nextIsString = false;
                cellNull=true;
            }
        }
        //当元素为t时
        if("t".equals(name)){
            isTElement = true;
        } else {
            isTElement = false;
        }
        // 置空
        lastContents = "";
    }
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString().trim();
            } catch (Exception e) {
            }
        }
        //t元素也包含字符串
        if(isTElement){
            String value = lastContents;
            value = value.equals("")?" ":value;
            rowlist.add(curCol, value);
            curCol++;
            isTElement = false;
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        } else if ("v".equals(name)) {
            String value = lastContents;
            cellNull=false;
            rowlist.add(curCol, value);
            curCol++;
        }
        else if("c".equals(name) && cellNull == true){
            rowlist.add(curCol, "");
            curCol++;
            cellNull = false;
        }else {
            //如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                //大于0的判断主要是筛除表头信息  这里具体看你想要的信息在哪一行开始
                if(curRow>0){
                    //判断最后一个单元格是否在最后，补齐列数
                    if(covertRowIdtoInt(lastCellid)<longest){
                        for(int i=0;i<longest- covertRowIdtoInt(lastCellid);i++){
                            rowlist.add(curCol, "");
                            curCol++;
                        }
                    }
                    rowReader.handle(curCol,curRow,rowlist);
                }
                rowlist=new ArrayList<Object>();
                curRow++;
                curCol = 0;
            }else if(name.equals("worksheet")){
                //结束标签为worksheet说明工作簿sheet读取完成
                //调用要处理的方法
                //importService.getRows(excelList);
//                getRows(excelList);
            }
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //得到单元格内容的值
        lastContents += new String(ch, start, length);
    }
    /**
     * 列号转数字   AB7-->28 第28列
     * @param cellId
     * @return
     */
    public static int covertRowIdtoInt(String cellId){
        int firstDigit = -1;
        for (int c = 0; c < cellId.length(); ++c) {
            if (Character.isDigit(cellId.charAt(c))) {
                firstDigit = c;
                break;
            }
        }
        //AB7-->AB
        //AB是列号, 7是行号
        String newRowId = cellId.substring(0,firstDigit);
        int num = 0;
        int result = 0;
        int length = newRowId.length();
        for(int i = 0; i < length; i++) {
            //先取最低位，B
            char ch = newRowId.charAt(length - i - 1);
            //B表示的十进制2，ascii码相减，以A的ascii码为基准，A表示1，B表示2
            num = (int)(ch - 'A' + 1) ;
            //列号转换相当于26进制数转10进制
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

}
