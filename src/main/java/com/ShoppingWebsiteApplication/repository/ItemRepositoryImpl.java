package com.ShoppingWebsiteApplication.repository;

import com.ShoppingWebsiteApplication.model.Item;
import com.ShoppingWebsiteApplication.repository.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public  class ItemRepositoryImpl implements ItemRepository {

    private static final String ITEM_TABLE_NAME = "item";

    @Autowired
    JdbcTemplate jdbcTemplate;



    @Override
    public Long createItem(Item item) {
        String sql = "INSERT INTO " + ITEM_TABLE_NAME + " (title, price,in_stock,quantity , picture_url , liked , cart) VALUES ( ?,? ,?, ?,? , ? , ?)";
        jdbcTemplate.update(sql, item.getTitle() , item.getPrice(), item.getInStock(), item.getQuantity(),item.getPictureUrl(), item.getLiked() , item.getCart());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", Long.class);
    }


    @Override
    public Item getItemById(Long itemId) {
        String sql = "SELECT * FROM " + ITEM_TABLE_NAME + " WHERE id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new ItemMapper(), itemId);
        } catch (EmptyResultDataAccessException error){
            return null;
        }
    }


    @Override
    public void deleteItemById(Long itemId) {
        String sql = "DELETE FROM " + ITEM_TABLE_NAME + " WHERE id=?";
         jdbcTemplate.update(sql,itemId);
    }



//    @Override
//    public void updateItem(Item[] itemsarr ) {
//        for (int i = 0; i < itemsarr.length; i++) {
//            String sql = "UPDATE " + ITEM_TABLE_NAME + " SET title=?, price=? , in_stock=? , quantity=? ,   picture_url=? , liked=? , cart=? " +
//                    " WHERE id=?";
//            jdbcTemplate.update(sql, itemsarr[i].getTitle(), itemsarr[i].getPrice(), itemsarr[i].getInStock(), itemsarr[i].getQuantity(), itemsarr[i].getPictureUrl(), itemsarr[i].getLiked(), itemsarr[i].getCart(), itemsarr[i].getId());
//        }
//    }

    @Override
    public void updateItem(Item item, Long itemId) {
           String sql = "UPDATE " + ITEM_TABLE_NAME + " SET title=?, price=? , in_stock=? , quantity=? ,   picture_url=? , liked=? , cart=? " +
                " WHERE id=?";
        jdbcTemplate.update(sql,item.getTitle() ,  item.getPrice(), item.getInStock(), item.getQuantity(), item.getPictureUrl(), item.getLiked(), item.getCart(), item.getId());
    }





    @Override
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM " + ITEM_TABLE_NAME ;
        try {
            return jdbcTemplate.query(sql, new ItemMapper());
        } catch (EmptyResultDataAccessException error){
            return null;
        }
    }


}
