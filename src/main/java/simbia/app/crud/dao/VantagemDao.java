package simbia.app.crud.dao;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.abstractclasses.DaoGenerica;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.model.dao.Vantagem;
import simbia.app.crud.util.ValidacoesDeDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static simbia.app.crud.util.UtilitariosException.gerarExceptionEspecializadaPorSQLException;

/**
 * Classe de {@code Data Access Object} da entidade {@link Vantagem}
 */
public class VantagemDao extends DaoGenerica<Vantagem> {
//atributos>constantes>comandos-sql
    private static final String COMANDO_INSERIR = "INSERT INTO vantagem(cnmvantagem, cdescricao) VALUES (?, ?)";
    private static final String COMANDO_ATUALIZAR = "UPDATE vantagem SET cnmvantagem=?, cdescricao=? WHERE idvantagem=?";
    private static final String COMANDO_DELETAR = "DELETE FROM vantagem WHERE idvantagem=?";
    private static final String CONSULTA_RECUPERAR_PELO_ID = "SELECT * FROM vantagem WHERE idvantagem=?";
    private static final String CONSULTA_RECUPERAR_TUDO = "SELECT * FROM vantagem";

//operacoes>gerais
    @Override
    public void inserir(Vantagem vantagem) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);
            comandoInserir.setString(1, vantagem.getNomeVantagem());
            comandoInserir.setString(2, vantagem.getDescricao());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();

                if(temProximoRegistro(retornoBanco)){
                    vantagem.setIdVantagem(retornoBanco.getLong("idvantagem"));
                    sucessoDaOperacao = true;
                }

            }

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public void atualizar(Vantagem vantagem) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);

            comandoAtualizar.setString(1, vantagem.getNomeVantagem());
            comandoAtualizar.setString(2, vantagem.getDescricao());
            comandoAtualizar.setLong(3, vantagem.getIdVantagem());

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public void deletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException{
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoDeletar = conexao.prepareStatement(COMANDO_DELETAR);
            comandoDeletar.setLong(1, id);

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoDeletar.executeUpdate());

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public List<Vantagem> recuperarTudo() throws DaoException {
        List<Vantagem> listaVantagem = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaRecuperarTudo = conexao.createStatement();
            ResultSet retornoBanco = consultaRecuperarTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(retornoBanco)) listaVantagem.add(new Vantagem(retornoBanco));
        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaVantagem;
    }

    @Override
    public Optional<Vantagem> recuperarPeloId(long id) throws DaoException {
        Vantagem vantagem = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaRecuperarPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaRecuperarPeloId.setLong(1, id);

            ResultSet retornoBanco = consultaRecuperarPeloId.executeQuery();
            if (temProximoRegistro(retornoBanco)) vantagem = new Vantagem(retornoBanco);
        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(vantagem);
    }
}
