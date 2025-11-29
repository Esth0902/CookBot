package com.development.cookbot.repository.shopping;

import com.development.cookbot.entity.ShoppingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingRepository extends JpaRepository<ShoppingEntity, Long> {
    @Transactional
    @Query(value = "SELECT se FROM ShoppingEntity se WHERE se.user.id = ?1")
    List<ShoppingEntity> findAllShoppingListByUser(Long userId);

    @Transactional
    @Query(value = "SELECT COUNT(se) FROM ShoppingEntity se WHERE se.user.id = ?1")
    Integer countNumberOfShoppingListByUser(Long userId);
}
