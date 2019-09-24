import com.ken.sys.common.entity.Area;

import java.util.ArrayList;
import java.util.List;

public class AreaData {
    public static List<Area> list =new ArrayList<Area>();
    static {
        Area a1=new Area("340000","0","安徽");
        Area a2 =new Area("340100","340000","合肥");
        Area a3 =new Area("340200","340000","安庆");
        Area a4 =new Area("340300","340000","阜阳");
        Area a5 =new Area("340101","340100","经开区");
        Area a6 =new Area("340201","340200","太湖县");
        Area a7 =new Area("340202","340200","宿松县");
        Area a8 =new Area("340303","340300","界南县");
        Area a9 =new Area("340318","340000","滁州");
        a9.setActive("0");
        Area a10 =new Area("340305","340300","颍上县");
        a10.setActive("0");
        Area a11=new Area("340305001","340305","西三十铺镇");
        Area a12 =new Area("340305002","340305","十八里铺镇");
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(a7);
        list.add(a8);
        list.add(a9);
        list.add(a10);
        list.add(a11);
        list.add(a12);
    }
}
