package cj.netos.ec.wybank.service;

import cj.netos.ec.wybank.ICuratorPathChecker;
import cj.studio.ecm.annotation.CjService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

@CjService(name = "curatorPathChecker")
public class DefaultCuratorPathChecker implements ICuratorPathChecker {
    @Override
    public void check(CuratorFramework framework, String path) throws Exception {
        try {
            Stat stat=framework.checkExists().forPath(path);
            if (stat == null) {
                framework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
