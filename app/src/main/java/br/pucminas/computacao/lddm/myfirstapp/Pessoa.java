package br.pucminas.computacao.lddm.myfirstapp;

public class Pessoa {

    private String nome, sobrenome, celular, email;

    Pessoa (String nome, String sobrenome, String celular, String email){
        this.setNome(nome);
        this.setSobrenome(sobrenome);
        this.setCelular(celular);
        this.setEmail(email);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

}
