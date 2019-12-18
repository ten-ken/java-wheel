
import com.ken.sys.common.entity.EchartPo;
import com.ken.sys.common.util.EChartsUtils;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.*;

public class TestEchartsUtil {


    private Map<String, List<Object>> setCommonOptions(String type) {
        List<EchartPo> fold1 =new ArrayList<EchartPo>();
        EchartPo p1 =new EchartPo();
        p1.setName("周一");
        p1.setRedictCount(120L);
        p1.setEmailCount(250L);
        p1.setVideoCount(8500L);
        fold1.add(p1);

        EchartPo p2 =new EchartPo();
        p2.setName("周二");
        p2.setRedictCount(50L);
        p2.setEmailCount(900L);
        p2.setVideoCount(0L);
        fold1.add(p2);

        EchartPo p3 =new EchartPo();
        p3.setName("周三");
        p3.setRedictCount(339L);
        p3.setEmailCount(80L);
        p3.setVideoCount(17L);
        fold1.add(p3);
//        数据结束区

        Object[] legends ={"直接访问","邮件访问","视频访问"};
        Object[] fields ={"redictCount","emailCount","videoCount"};

        List<Long> redictList =new ArrayList<Long>();
        List<Long> emailList =new ArrayList<Long>();
        List<Long> videoList =new ArrayList<Long>();

        return EChartsUtils.getECharOptionsToFoldLine(type,fold1, "name",legends,fields,redictList,emailList,videoList);
    }


    //条形图
    @Test
    public void bar(){
        Map<String, List<Object>> barChart = setCommonOptions("bar");
        System.out.println(barChart);
    }


    //折线图
    @Test
    public void line(){
        Map<String, List<Object>> barChart = setCommonOptions("line");
        System.out.println(barChart);
    }

    //饼图 --圆形图
    @Test
    public void pie() throws Exception{
 //        数据开始区
        List<EchartPo> fold1 =new ArrayList<EchartPo>();
        EchartPo p1 =new EchartPo();
        p1.setName("直接访问");
        p1.setValue(new BigDecimal("1863"));
        fold1.add(p1);

        EchartPo p2 =new EchartPo();
        p2.setName("邮件访问");
        p2.setValue(new BigDecimal("3900"));
        fold1.add(p2);

        EchartPo p3 =new EchartPo();
        p3.setName("视频访问");
        p3.setValue(new BigDecimal("82527"));
        fold1.add(p3);
//        数据结束区

        Object[] legends ={"直接访问","邮件访问","视频访问"};
        Map<String, List<Object>> barChart = EChartsUtils.getECharOptionsToCircle(fold1, "name","value");
        System.out.println(barChart);

    }


}
