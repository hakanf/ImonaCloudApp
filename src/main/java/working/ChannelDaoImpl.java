package working;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hakan
 * Date: 6/18/14
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ChannelDaoImpl extends HibernateDaoSupport implements ChannelDao {

    public void save(Channel channel){
        getHibernateTemplate().save(channel);
    }

    public void update(Channel channel){
        getHibernateTemplate().update(channel);
    }

    public void delete(Channel channel){
        getHibernateTemplate().delete(channel);
    }

    public List<Channel> findAll(){
        List <Channel> channels = getHibernateTemplate().find("from Channel ");
        System.out.println("in findall");
        return channels;
    }

    public Channel findByStockCode(Integer channelId){
        List list = getHibernateTemplate().find(
                "from Channel where stockCode=?",channelId
        );
        return (Channel)list.get(0);
    }

}