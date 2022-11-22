package net.thumbtack.school.concert.db.mysql.daoimpl;

import net.thumbtack.school.concert.db.mysql.data.MyBatisUtils;
import net.thumbtack.school.concert.db.mysql.mapper.DataMapper;
import net.thumbtack.school.concert.db.mysql.mapper.SongMapper;
import net.thumbtack.school.concert.db.mysql.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

public class DaoImplBase {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected DataMapper getDataMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DataMapper.class);
    }

    protected SongMapper getSongMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SongMapper.class);
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

}
