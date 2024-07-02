package com.ufrn.imd.ecommerce.repositories;

import com.ufrn.imd.ecommerce.models.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
//    @Query("SELECT u FROM Cliente u JOIN FETCH u.enderecos WHERE u.id = :usuarioId")
//    Cliente findUsuarioConcretoByIdWithEnderecos(@Param("usuarioId") Long usuarioId);

//    @Query("SELECT u FROM Usuario u JOIN FETCH u.cartoes WHERE u.id = :usuarioId")
//    Usuario findUsuarioByIdWithCartoes(@Param("usuarioId") Long usuarioId);
//
//    @Query("SELECT u FROM Usuarios u JOIN FETCH u WHERE u.id = :usuarioId")
//    Usuario findUsuarioByIdWithPedidos(@Param("usuarioId") Long usuarioId);
//
//    @Query("SELECT u FROM Cliente u JOIN FETCH u.movimentacoesEstoque WHERE u.id = :usuarioId")
//    Usuario findUsuarioByIdWithMovimentacoesEstoque(@Param("usuarioId") Long usuarioId);

//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.enderecos JOIN FETCH u.cartoes JOIN FETCH u.pedidos JOIN FETCH u.movimentacoesEstoque WHERE u.id = :usuarioId")
//    Cliente findUsuarioByIdWithAllAssociations(@Param("usuarioId") Long usuarioId);

//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.enderecos")
//    List<Cliente> findAllUsuarioWithEnderecos();

//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.cartoes")
//    List<Usuario> findAllUsuarioConcretoWithCartoes();
//
//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.pedidos")
//    List<Usuario> findAllUsuarioConcretoWithPedidos();
//
//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.movimentacoesEstoque")
//    List<Usuario> findAllUsuarioWithMovimentacoesEstoque();

//    @Query("SELECT DISTINCT u FROM Cliente u JOIN FETCH u.enderecos JOIN FETCH u.cartoes JOIN FETCH u.pedidos JOIN FETCH u.movimentacoesEstoque")
//    List<Cliente> findAllUsuarioWithAllAssociations();

    Usuario findUsuarioByEmail(String email);

    UserDetails findByEmail(String email);


}
