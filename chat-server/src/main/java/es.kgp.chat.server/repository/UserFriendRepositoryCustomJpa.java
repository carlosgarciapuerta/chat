package es.kgp.chat.server.repository;

import es.kgp.chat.server.model.UserFriend;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Created by kgp on 19/01/2014.
 */
@Repository
public class UserFriendRepositoryCustomJpa implements UserFriendRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    private JpaEntityInformation<UserFriend, Long> entityInformation;


    @PostConstruct
    public void init() {
        this.entityInformation = new JpaMetamodelEntityInformation<UserFriend, Long>(UserFriend.class, em.getMetamodel());
    }


    @Override
    public UserFriend findByFriendAndFriendOf(Long friendId, Long friendOfId) {
        TypedQuery<UserFriend> query = em.createQuery("select uf from UserFriend uf where uf.friend.id = :friendId and uf.friendOf.id = :friendOfId", UserFriend.class);
        query.setParameter("friendId", friendId);
        query.setParameter("friendOfId", friendOfId);
        return query.getSingleResult();
    }

    @Override
    public UserFriend save(UserFriend userFriend) {
        if (entityInformation.isNew(userFriend)) {
            em.persist(userFriend);

            UserFriend symmetricUserFriend = new UserFriend();
            symmetricUserFriend.setFriend(userFriend.getFriendOf());
            symmetricUserFriend.setFriendOf(userFriend.getFriend());
            em.persist(symmetricUserFriend);

            return userFriend;
        } else {
            throw new IllegalAccessError("UserFriend entity cannot be updated");
        }
    }

    @Override
    @Transactional
    public void delete(UserFriend entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        UserFriend symmetricRelationship = findByFriendAndFriendOf(entity.getFriendOf().getId(), entity.getFriend().getId());
        em.remove(symmetricRelationship);
    }
}
