package CustomerApp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/18/14
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */


public interface ChannelBo {

    void save(Channel channel);
    void update(Channel channel);
    void delete(Channel channel);
    List<Channel> find();

}