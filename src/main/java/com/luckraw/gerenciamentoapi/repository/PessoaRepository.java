package com.luckraw.gerenciamentoapi.repository;

import com.luckraw.gerenciamentoapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
