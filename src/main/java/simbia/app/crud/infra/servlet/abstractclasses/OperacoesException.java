package simbia.app.crud.infra.servlet.abstractclasses;

import static simbia.app.crud.util.UtilitariosException.recuperarDataEHoraFormatada;

/**
 * Classe abstrata de excessoes relacionadas a entrada de dados
 */
public abstract class OperacoesException extends RuntimeException{
    //atributos
    private final String mensagem;
    private final String dataHora;

    //contrutor
    public OperacoesException(){
        this.dataHora = recuperarDataEHoraFormatada();
        this.mensagem = gerarMensagem();
    }

    //toString
    public String toString(){
        return dataHora + mensagem;
    }

    //getter
    public String getMensagem() {
        return mensagem;
    }

    public String getDataHora() {
        return dataHora;
    }

    //outros
    public abstract String gerarMensagem();
}
