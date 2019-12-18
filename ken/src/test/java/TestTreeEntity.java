/******************************************************************************
 *
 * 版权所有：安徽匠桥电子信息有限公司
 *
 ******************************************************************************
 * 注意：本内容仅限于安徽匠桥电子信息有限公司内部使用，禁止转发
 *****************************************************************************/

import com.ken.sys.common.entity.Area;
import com.ken.sys.common.entity.dto.TreeEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>Title: TestTreeEntity</li>
 * @date 2019/9/24 0024 上午 10:46
 */
public class TestTreeEntity {


    @Test
    public void testgetTree()throws Exception{
        List<TreeEntity> list  =new ArrayList<TreeEntity>();
        Area a1=new Area("340000","0","安徽");
        Area a2 =new Area("340100","340000","合肥");
        TreeEntity<Area> treeEntity1 =new TreeEntity<Area>();
        treeEntity1.setId("340000");
        treeEntity1.setpId("0");
        treeEntity1.setName("安徽");
        treeEntity1.setOpen(true);
        treeEntity1.setIsParent(true);
        treeEntity1.setT(a1);

        list.add(treeEntity1);

        TreeEntity<Area> treeEntity2 =new TreeEntity<Area>();
        treeEntity2.setId("340000");
        treeEntity2.setpId("0");
        treeEntity2.setName("安徽");
        treeEntity2.setOpen(true);
        treeEntity2.setIsParent(true);
        treeEntity2.setT(a2);

        list.add(treeEntity2);

        System.out.println(111);
    }
}
