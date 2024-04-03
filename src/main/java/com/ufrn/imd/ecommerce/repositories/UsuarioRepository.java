package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.heranca.UsuarioAbstrato;
import com.ufrn.imd.ecommerce.models.heranca.UsuarioConcreto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioConcreto, Long> {
}
