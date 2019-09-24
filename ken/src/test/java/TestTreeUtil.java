/******************************************************************************
 *
 * 作者（author）：ken
 * 微信（weChat）：mlchao1992
 * 个人博客（website）：
 *
 ******************************************************************************
 * 注意：尊重原创
 *****************************************************************************/

import com.ken.sys.common.entity.Area;
import com.ken.sys.common.util.TreeInfoVo;
import com.ken.sys.common.util.TreeUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * <ul>
 * <li>Title: ken-TestTreeUtil</li>
 * <li>Description: 树工具测试类 </li>
 * <li>Copyright: Copyright (c) 2018</li>
 * </ul>
 *
 * @author ken
 * @version V1.0
 * @date 2019/9/20 21:56
 */
public class TestTreeUtil {

    @Test
    public void testgetTree()throws Exception{
        List<Area> list  = AreaData.list;

        //返回自身的树
        TreeInfoVo<Area> areaTreeInfoVo = TreeUtil.getTree(list, "pid", "code", "childList", "340000", "active");
        TreeInfoVo<Area> areaTreeInfoVo1 = TreeUtil.getTree(list, "pid", "code", "childList", "340000");

        //返回父节点的树
        TreeInfoVo<Area> areaTreeInfoVo2 = TreeUtil.getTreeByPid(list, "pid", "code", "childList", "0","active");
        TreeInfoVo<Area> areaTreeInfoVo3 = TreeUtil.getTreeByPid(list, "pid", "code", "childList", "340200");

        TreeInfoVo<Area> areaTreeInfoVo5 = TreeUtil.configEmptyNode(true,"name",null).getTree(list, "pid", "code", "childList", "340000", "active");
        TreeInfoVo<Area> areaTreeInfoVo6 = TreeUtil.configEmptyNode(true,"name",null).getTreeByPid(list, "pid", "code", "childList", "340000", "active");
        System.out.println(111);
    }
}
