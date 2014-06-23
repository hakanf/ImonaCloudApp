package CustomerApp;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/18/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

public class ChannelBoImpl implements ChannelBo {

    ChannelDao channelDao;

    public void setchannelDao(ChannelDao channelDao) {
        this.channelDao = channelDao;
    }

    public void save(Channel channel){
        channelDao.save(channel);
    }

    public void update(Channel channel){
        channelDao.update(channel);
    }

    public void delete(Channel channel){
        channelDao.delete(channel);
    }

    public List<Channel> find(){
        return channelDao.findAll();
    }

    public Channel findByStockCode(Integer channelId){
        return channelDao.findByStockCode(channelId);
    }
}