package com.luckraw.gerenciamentoapi.controller;

import com.luckraw.gerenciamentoapi.model.Endereco;
import com.luckraw.gerenciamentoapi.model.Pessoa;
import com.luckraw.gerenciamentoapi.repository.PessoaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaRepository pessoaRepository;

    public PessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> consultarPessoa(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pessoa ciarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> editarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);
        if (pessoaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pessoa.setId(id);
        pessoa.setEnderecos(pessoaExistente.get().getEnderecos());
        pessoa = pessoaRepository.save(pessoa);

        return ResponseEntity.ok(pessoa);
    }

    @PostMapping("/{id}/enderecos")
    public ResponseEntity<Pessoa> criarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        endereco.setPessoa(pessoa.get());
        pessoa.get().getEnderecos().add(endereco);
        pessoaRepository.save(pessoa.get());

        return ResponseEntity.ok(pessoa.get());
    }

    @GetMapping("/{id}/enderecos")
    public ResponseEntity<List<Endereco>> listarEnderecos(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pessoa.get().getEnderecos());
    }

    @GetMapping("/{id}/enderecos/{endrecoId}")
    public ResponseEntity<Endereco> consultarEndereco(@PathVariable Long id, @PathVariable Long endrecoId) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isEmpty()) {

        }

        Optional<Endereco> endereco = pessoa.get().getEnderecos().stream()
                .filter(e -> e.getId().equals(endrecoId)).findFirst();

        return endereco.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/enderecos/{enderecoId}")
    public ResponseEntity<Endereco> editarEndereco(@PathVariable Long id, @PathVariable Long endrecoId, @RequestBody Endereco endereco) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Endereco> enderecoExistente = pessoa.get().getEnderecos().stream()
                .filter(e -> e.getId().equals(endrecoId)).findFirst();

        if (enderecoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        endereco.setId(endrecoId);
        endereco.setPessoa(pessoa.get());
        pessoa.get().getEnderecos().remove(enderecoExistente.get());
        pessoa.get().getEnderecos().add(endereco);
        pessoaRepository.save(pessoa.get());

        return ResponseEntity.ok(endereco);
    }

    @PutMapping("/{id}/enderecos/{enderecoId}/definirPrincipal")
    public ResponseEntity<Endereco> definirPrincipal(@PathVariable Long id, @PathVariable Long enderecoId) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Endereco> endereco = pessoa.get().getEnderecos().stream()
                .filter(e -> e.getId().equals(enderecoId))
                .findFirst();

        if (endereco.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pessoa.get().getEnderecos().forEach(e -> e.setPrincipal(false));
        endereco.get().setPrincipal(true);
        pessoaRepository.save(pessoa.get());

        return ResponseEntity.ok(endereco.get());
    }

}
