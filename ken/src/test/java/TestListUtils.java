
import com.ken.sys.common.entity.Area;
import com.ken.sys.common.util.ListUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>Title: TestListUtils</li>
 * <li>Description: TODO </li>
 * @date 2019/9/24 0024 上午 11:03
 */
public class TestListUtils {
    @Test
    public void testgetTree()throws Exception{
        List<Area> list  = AreaData.list;

        //父节点找 所有子节点
        List<Area> childNodes = ListUtils.findChildNodes(new ArrayList<Area>(), list, "340000", "pid", "code");
        //子节点 找所有 父节点
        List<Area> parentNodes = ListUtils.findParentNodes(new ArrayList<Area>(), list, "340300", "pid", "code",false);
        //找兄弟节点
        List<Area> SiblingNodes = ListUtils.findSiblingNodes(list, "340300", "pid", "code",false);
        System.out.println(111);
    }
}
