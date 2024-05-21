package com.codeuz.repository;


import com.codeuz.dto.FilterResponseDTO;
import com.codeuz.dto.ProfileFilterDTO;
import com.codeuz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public FilterResponseDTO <ProfileEntity> filter(ProfileFilterDTO filter, int page, int size) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder();

        if (filter.getName() != null){
            query.append(" and s.name=:name ");
            params.put("name", filter.getName());
        }
        if (filter.getUsername() != null){
            query.append(" and s.username=:username ");
            params.put("surname", filter.getUsername());
        }
        if (filter.getPhone() != null){
            query.append(" and s.phone=:phone ");
            params.put("phone", filter.getPhone());
        }
        if (filter.getRole() != null){
            query.append(" and s.role=:role ");
            params.put("role", filter.getRole());
        }
        if (filter.getCreateDateTo() != null){
            query.append(" and s.create_date>=:createDateTo ");
            params.put("createDateTo", filter.getCreateDateTo());
        }
        if (filter.getCreateDateFrom() != null){
            query.append(" and s.create_date<=:createDateFrom ");
            params.put("createDateFrom", filter.getCreateDateFrom());
        }

        StringBuilder selectSql = new StringBuilder(" from ProfileEntity s where s.visible = true");
        StringBuilder countSql = new StringBuilder(" select count(s) from ProfileEntity s where s.visible = true");
        selectSql.append(query);
        countSql.append(query);

        //select
        Query selectQuery = entityManager.createQuery(selectSql.toString());
        Query countQuery = entityManager.createQuery(countSql.toString());
        for (Map.Entry<String, Object> entry: params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setFirstResult(page * size); // offset
        selectQuery.setMaxResults(size); // limit

        List<ProfileEntity> profileEntityList = selectQuery.getResultList();

        // count
        Long totalCount = (Long) countQuery.getSingleResult();
        return new FilterResponseDTO<ProfileEntity>(profileEntityList, totalCount);
    }





}
