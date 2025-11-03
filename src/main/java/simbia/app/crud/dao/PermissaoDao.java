package simbia.app.crud.dao;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.abstractclasses.DaoGenerica;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.model.dao.Permissao;
import simbia.app.crud.util.ValidacoesDeDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static simbia.app.crud.util.UtilitariosException.gerarExceptionEspecializadaPorSQLException;

/**
 * Classe de {@code Data Access Object} da entidade {@link Permissao}
 */
public class PermissaoDao extends DaoGenerica<Permissao> {

//atributos>constantes>comandos-sql
    private static final String COMANDO_INSERIR = "INSERT INTO permissao(cnmpermissao, cdescricao) VALUES (?, ?)";
    private static final String COMANDO_ATUALIZAR = "UPDATE permissao SET cnmpermissao=?, cdescricao=? WHERE idpermissao=?";
    private static final String COMANDO_DELETAR = "DELETE FROM permissao WHERE idpermissao=?";
    private static final String CONSULTA_RECUPERAR_PELO_ID = "SELECT * FROM permissao WHERE idpermissao=?";
    private static final String CONSULTA_RECUPERAR_TUDO = "SELECT * FROM permissao";

//operacoes>gerais
    @Override
    public void inserir(Permissao permissao) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);
            comandoInserir.setString(1, permissao.getNomePermissao());
            comandoInserir.setString(2, permissao.getDescricao());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();

                if(temProximoRegistro(retornoBanco)) {
                    permissao.setIdPermissao(retornoBanco.getLong("idpermissao"));
                    sucessoDaOperacao = true;
                }

            }

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally{
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public void atualizar(Permissao permissao) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);
            comandoAtualizar.setString(1, permissao.getNomePermissao());
            comandoAtualizar.setString(2, permissao.getDescricao());
            comandoAtualizar.setLong(3, permissao.getIdPermissao());

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public void deletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
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
    public List<Permissao> recuperarTudo() throws DaoException {
        List<Permissao> listaPermissao = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaTudo = conexao.createStatement();
            ResultSet resultadoDaConsulta = consultaTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(resultadoDaConsulta)) listaPermissao.add(new Permissao(resultadoDaConsulta));
        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaPermissao;
    }

    @Override
    public Optional<Permissao> recuperarPeloId(long id) throws DaoException {
        Permissao permissao = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaPeloId.setLong(1, id);
            ResultSet retornoDaConsulta = consultaPeloId.executeQuery();

            if (temProximoRegistro(retornoDaConsulta)) permissao = new Permissao(retornoDaConsulta);
        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(permissao);
    }
}