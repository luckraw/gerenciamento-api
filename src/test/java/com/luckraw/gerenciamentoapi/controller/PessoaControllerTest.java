package com.luckraw.gerenciamentoapi.controller;

import com.luckraw.gerenciamentoapi.model.Endereco;
import com.luckraw.gerenciamentoapi.model.Pessoa;
import com.luckraw.gerenciamentoapi.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PessoaControllerTest {

    @Test
    public void listarPessoas_ReturnsListOfPessoas() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa());
        pessoas.add(new Pessoa());

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        List<Pessoa> result = pessoaController.listarPessoas();

        assertEquals(pessoas.size(), result.size());
        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    public void consultarPessoa_ExistingId_ReturnsPessoa() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Pessoa pessoa = new Pessoa();

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Pessoa> result = pessoaController.consultarPessoa(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pessoa, result.getBody());
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    public void consultarPessoa_NonExistingId_ReturnsNotFound() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;

        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Pessoa> result = pessoaController.consultarPessoa(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    public void criarPessoa_ReturnsSavedPessoa() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Pessoa pessoa = new Pessoa();

        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        Pessoa result = pessoaController.ciarPessoa(pessoa);

        assertEquals(pessoa, result);
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    public void editarPessoa_ExistingId_ReturnsUpdatedPessoa() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        Pessoa pessoaExistente = new Pessoa();

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoaExistente));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Pessoa> result = pessoaController.editarPessoa(id, pessoa);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pessoa, result.getBody());
        verify(pessoaRepository, times(1)).findById(id);
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    public void editarPessoa_NonExistingId_ReturnsNotFound() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Pessoa pessoa = new Pessoa();

        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Pessoa> result = pessoaController.editarPessoa(id, pessoa);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(pessoaRepository, times(1)).findById(id);
        verify(pessoaRepository, never()).save(pessoa);
    }

    @Test
    public void criarEndereco_ExistingPessoaId_ReturnsUpdatedPessoa() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        Endereco endereco = new Endereco();

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Pessoa> result = pessoaController.criarEndereco(id, endereco);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pessoa, result.getBody());
        verify(pessoaRepository, times(1)).findById(id);
        verify(pessoaRepository, times(1)).save(pessoa);
    }

    @Test
    public void criarEndereco_NonExistingPessoaId_ReturnsNotFound() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Endereco endereco = new Endereco();

        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Pessoa> result = pessoaController.criarEndereco(id, endereco);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(pessoaRepository, times(1)).findById(id);
        verify(pessoaRepository, never()).save(new Pessoa());
    }

    @Test
    public void listarEnderecos_ExistingPessoaId_ReturnsListOfEnderecos() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco());
        enderecos.add(new Endereco());

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<List<Endereco>> result = pessoaController.listarEnderecos(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(enderecos, result.getBody());
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    public void listarEnderecos_NonExistingPessoaId_ReturnsNotFound() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;

        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<List<Endereco>> result = pessoaController.listarEnderecos(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    public void consultarEndereco_ExistingPessoaIdAndEnderecoId_ReturnsEndereco() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Long enderecoId = 1L;
        Pessoa pessoa = new Pessoa();
        Endereco endereco = new Endereco();

        pessoa.getEnderecos().add(endereco);

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Endereco> result = pessoaController.consultarEndereco(id, enderecoId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(endereco, result.getBody());
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    public void consultarEndereco_NonExistingPessoaId_ReturnsNotFound() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Long enderecoId = 1L;

        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Endereco> result = pessoaController.consultarEndereco(id, enderecoId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    public void consultarEndereco_ExistingPessoaIdAndNonExistingEnderecoId_ReturnsNotFound() {

        PessoaRepository pessoaRepository = mock(PessoaRepository.class);
        Long id = 1L;
        Long enderecoId = 1L;
        Pessoa pessoa = new Pessoa();

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        PessoaController pessoaController = new PessoaController(pessoaRepository);

        ResponseEntity<Endereco> result = pessoaController.consultarEndereco(id, enderecoId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(pessoaRepository, times(1)).findById(id);

    }
}