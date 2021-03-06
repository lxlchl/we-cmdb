package com.webank.cmdb.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webank.cmdb.domain.AdmRole;
import com.webank.cmdb.domain.AdmUser;

public interface UserRepository extends JpaRepository<AdmUser, String> {
    AdmUser findByName(String name);

    @Cacheable("user-roles")
    @Query("SELECT DISTINCT role FROM AdmUser user JOIN user.admRoleUsers ru JOIN ru.admRole role WHERE user.code = :username")
    List<AdmRole> findRolesByUserName(String username);
    Boolean existsByCode(String code);
    
    @Modifying  
    @Query(value = "update adm_user set encrypted_password = :encryptedPassword where id_adm_user = :idAdmUser and code  = :code",nativeQuery = true)  
    void updateEncryptedPasswordByIdAdmUserAndCode(@Param ("encryptedPassword") String encryptedPassword, @Param("idAdmUser") Integer idAdmUser, @Param("code") String code);
    
}
