package com.tcc.lucca.scoutup.model;

import java.util.List;


public class Escoteiro extends Usuario {

    private List<String> cargos;
    private String patrulha;

    public Escoteiro(String nome, String email) {

        setNome(nome);
        setEmail(email);

    }

    public Escoteiro(String nome, String email, String foto, String grupoId, String sessaoId, int idade, String id, String patrulhaId, List<String> cargos) {
        setNome(nome);
        setEmail(email);
        setFoto(foto);
        setGrupo(grupoId);
        setSessao(sessaoId);
        setIdade(idade);
        setId(id);
        setTipo(TipoUser.ESCOTEIRO);
        this.patrulha = patrulhaId;
        this.cargos = cargos;



    }

    public void adicionarCargo(Cargos cargo) {


        cargos.add(cargo.toString());


    }

    public String getPatrulha() {
        return patrulha;
    }

    public void setPatrulha(String patrulha) {
        this.patrulha = patrulha;
    }

    public List<String> getCargos() {
        return cargos;
    }

    public void setCargos(List<String> cargos) {
        this.cargos = cargos;
    }
}
