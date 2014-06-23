package CustomerApp;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/18/14
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

public interface ChannelDao {

    void save(Channel channel);
    void update(Channel channel);
    void delete(Channel channel);
    List<Channel> findAll();

    Channel findByStockCode(Integer channelId);

}