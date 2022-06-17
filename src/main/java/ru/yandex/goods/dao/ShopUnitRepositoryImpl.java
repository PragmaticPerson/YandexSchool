package ru.yandex.goods.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.yandex.goods.models.ShopUnitDB;
import ru.yandex.goods.models.ShopUnitPrice;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public class ShopUnitRepositoryImpl implements ShopUnitRepositoryCustom {

    private EntityManager em;

    @Autowired
    public ShopUnitRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public ShopUnitDB getOneWithLastPrice(UUID uuid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ShopUnitDB> cq = cb.createQuery(ShopUnitDB.class);

        Root<ShopUnitDB> root = cq.from(ShopUnitDB.class);
        Join<ShopUnitDB, ShopUnitPrice> join = root.join("prices");

        Predicate selectUUID = cb.equal(root.get("id"), uuid);
        Predicate joinOn = cb.equal(join.get("unitId"), root.get("id"));
        cq.where(selectUUID, joinOn);
        cq.orderBy(cb.asc(root.get("id")), cb.desc(join.get("date")));

        TypedQuery<ShopUnitDB> query = em.createQuery(cq);
        return query.getSingleResult();
    }

    @Override
    public Set<ShopUnitDB> getChildrenByParentId(UUID uuid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(ShopUnitDB.class);

        Root<ShopUnitDB> root = cq.from(ShopUnitDB.class);
        Join<ShopUnitDB, ShopUnitPrice> join = root.join("prices");

        Predicate parentUUID = cb.equal(root.get("parentId"), uuid);
        Predicate joinOn = cb.equal(join.get("unitId"), root.get("id"));
        cq.where(parentUUID, joinOn);
        cq.orderBy(cb.asc(root.get("id")), cb.desc(join.get("date")));

        TypedQuery<ShopUnitDB> query = em.createQuery(cq);
        return new HashSet<>(query.getResultList());
    }
}
