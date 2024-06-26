package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Cliente, Long> {
//    @Query("SELECT u FROM Cliente u JOIN FETCH u.enderecos WHERE u.id = :usuarioId")
//    Cliente findUsuarioConcretoByIdWithEnderecos(@Param("usuarioId") Long usuarioId);

    @Query("SELECT u FROM Cliente u JOIN FETCH u.cartoes WHERE u.id = :usuarioId")
    Cliente findUsuarioConcretoByIdWithCartoes(@Param("usuarioId") Long usuarioId);

    @Query("SELECT u FROM Cliente u JOIN FETCH u.pedidos WHERE u.id = :usuarioId")
    Cliente findUsuarioConcretoByIdWithPedidos(@Param("usuarioId") Long usuarioId);

    @Query("SELECT u FROM Cliente u JOIN FETCH u.movimentacoesEstoque WHERE u.id = :usuarioId")
    Cliente findUsuarioConcretoByIdWithMovimentacoesEstoque(@Param("usuarioId") Long usuarioId);

//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.enderecos JOIN FETCH u.cartoes JOIN FETCH u.pedidos JOIN FETCH u.movimentacoesEstoque WHERE u.id = :usuarioId")
//    Cliente findUsuarioConcretoByIdWithAllAssociations(@Param("usuarioId") Long usuarioId);

//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.enderecos")
//    List<Cliente> findAllUsuarioConcretoWithEnderecos();

    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.cartoes")
    List<Cliente> findAllUsuarioConcretoWithCartoes();

    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.pedidos")
    List<Cliente> findAllUsuarioConcretoWithPedidos();

    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.movimentacoesEstoque")
    List<Cliente> findAllUsuarioConcretoWithMovimentacoesEstoque();

//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.enderecos JOIN FETCH u.cartoes JOIN FETCH u.pedidos JOIN FETCH u.movimentacoesEstoque")
//    List<Cliente> findAllUsuarioConcretoWithAllAssociations();

    Cliente findUsuarioConcretoByEmail(String email);

    UserDetails findByEmail(String email);


}
