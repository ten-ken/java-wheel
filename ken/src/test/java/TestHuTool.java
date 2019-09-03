/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.extra.tokenizer.Word;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.ken.sys.common.entity.TAppUserInfoPO;
import com.ken.sys.common.excel.saxmodel.RowExcelHandler;
import org.junit.Assert;
import org.junit.Test;
import java.io.FileInputStream;
import java.util.*;

public class TestHuTool {


    //   测试正则部分
    @Test
    public void testReg(){
//      匹配部分根据模板样式 相接
        String content1 ="haabbbccc中文12aa34";
        String resultExtractMulti = ReUtil.extractMulti("(\\w)aa(\\w)", content1, "$1-$2");
        Assert.assertEquals("h-b", resultExtractMulti);

        //删除第一个匹配的字符串
        String content2 ="ZZaabbbccc中文1234";
        String resultDelFirst = ReUtil.delFirst("(\\w)aa(\\w)", content2);

        //将字符串按匹配模式切割   没有匹配部分过滤掉
        String content3 ="ZZaabbbccc中文1234";
        List<String> resultFindAll = ReUtil.findAll("\\w{3}", content3, 0, new ArrayList<String>());
        //组成新的集合 非List代理模式
//        ArrayList<String> expected =CollectionUtil.newArrayList("ZZ", "Za", "aa", "bb", "bc", "cc", "12", "34");

        //此处把1234替换为 ->1234<-
        String content4 ="ZZaabbbccc中文1234";
        String replaceAll = ReUtil.replaceAll(content4, "(\\d+)", "->$1<-");
        Assert.assertEquals("ZZZaaabbbccc中文->1234<-", replaceAll);
    }

//    这个没有分词库的jar 暂不测试
    @Test
    public void testChineseWordSegmentation(){
        //自动根据用户引入的分词库的jar来自动选择使用的引擎
        TokenizerEngine engine = TokenizerUtil.createEngine();
        //解析文本
        String text = "这两个方法的区别在于返回值";
        Result result = engine.parse(text);
        //输出：这 两个 方法 的 区别 在于 返回 值
        String resultStr = CollUtil.join((Iterator<Word>)result, " ");
    }

    @Test
    public void testExcelEventModel() throws Exception{
        long bg = System.currentTimeMillis();
        HashMap<String,Object> hashMap =new HashMap<String,Object>();
        hashMap.put("excel-index",3L);
        hashMap.put("specified_field","memberLevelId");
        hashMap.put("三星",30L);
        hashMap.put("四星",40L);
        hashMap.put("五星",50L);
        hashMap.put("普通用户",10L);
        RowExcelHandler<TAppUserInfoPO> tAppUserInfoPORowExcelHandler = new RowExcelHandler<>(new TAppUserInfoPO(), 1,hashMap);
        ExcelUtil.read07BySax(new FileInputStream("D:\\20.xlsx"), 0, tAppUserInfoPORowExcelHandler);
        Map<String, Object> handData = tAppUserInfoPORowExcelHandler.getHandData();

        long end = System.currentTimeMillis();
        System.out.println((end-bg));
    }

    @Test
    public void testExcelExport1(){
        List<TAppUserInfoPO> list =new ArrayList<TAppUserInfoPO>() ;
        TAppUserInfoPO tAppUserInfoPO =new TAppUserInfoPO();
        tAppUserInfoPO.setName("jjjz");
        list.add(tAppUserInfoPO);
        BigExcelWriter writer= ExcelUtil.getBigWriter("e:/kk.xlsx");
        //合并单元格后的标题行，使用默认标题样式
        writer.merge(4 - 1, "测试标题");
        //一次性写出内容，强制输出标题
        writer.write(list, true);
        // 关闭writer，释放内存
        writer.close();
    }
    @Test
    public void testExcelExport2(){
        ExcelReader reader = ExcelUtil.getReader("e:/kk.xlsx");
        List<TAppUserInfoPO> all = reader.readAll(TAppUserInfoPO.class);
    }

}
