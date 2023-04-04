package com.lk.dao;

import com.lk.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {

    //增加一本书
    int addBook(Books books);

    //删除一本书
    int deleteBookByID(@Param("bookId") int id);
    //查询一本书
    Books queryBookByID(int id);

    //更新一本书
    int updateBook(Books books);
    //查询全部书
    List<Books> queryAllBook();
}
