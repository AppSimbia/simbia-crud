package simbia.app.crud.dao;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.abstractclasses.DaoGenerica;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.model.dao.CategoriaProduto;
import simbia.app.crud.util.ValidacoesDeDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static simbia.app.crud.util.UtilitariosException.gerarExceptionEspecializadaPorSQLException;

/**
 * Classe de {@code Data Access Object} da entidade {@link CategoriaProduto}
 */
public class CategoriaProdutoDao extends DaoGenerica<CategoriaProduto> {

//atributos>constantes>comandos-sql
    private static final String COMANDO_INSERIR = "INSERT INTO categoriaproduto(cnmcategoria, cdescricao) VALUES (?, ?)";
    private static final String COMANDO_ATUALIZAR = "UPDATE categoriaproduto SET cnmcategoria=?, cdescricao=? WHERE idcategoriaproduto=?";
    private static final String COMANDO_DELETAR = "DELETE FROM categoriaproduto WHERE idcategoriaproduto=?";
    private static final String CONSULTA_RECUPERAR_PELO_ID = "SELECT * FROM categoriaproduto WHERE idcategoriaproduto=?";
    private static final String CONSULTA_RECUPERAR_TUDO = "SELECT * FROM categoriaproduto";

//operacoes>gerais
    @Override
    public void inserir(CategoriaProduto categoriaProduto) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);
            comandoInserir.setString(1, categoriaProduto.getNomeCategoria());
            comandoInserir.setString(2, categoriaProduto.getDescricao());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();

                if(temProximoRegistro(retornoBanco)){
                    categoriaProduto.setIdCategoriaProduto(retornoBanco.getLong("idcategoriaproduto"));
                    sucessoDaOperacao = true;
                }

            }

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally{
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public void atualizar(CategoriaProduto categoriaProduto) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);
            comandoAtualizar.setString(1, categoriaProduto.getNomeCategoria());
            comandoAtualizar.setString(2, categoriaProduto.getDescricao());
            comandoAtualizar.setLong(3, categoriaProduto.getIdCategoriaProduto());

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
    public List<CategoriaProduto> recuperarTudo() throws DaoException {
        List<CategoriaProduto> listaCategoriaProduto = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaTudo = conexao.createStatement();
            ResultSet resultadoDaConsulta = consultaTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(resultadoDaConsulta)) listaCategoriaProduto.add(new CategoriaProduto(resultadoDaConsulta));
        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaCategoriaProduto;
    }

    @Override
    public Optional<CategoriaProduto> recuperarPeloId(long id) throws DaoException {
        CategoriaProduto categoriaProduto = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaPeloId.setLong(1, id);
            ResultSet retornoDaConsulta = consultaPeloId.executeQuery();

            if (temProximoRegistro(retornoDaConsulta)) categoriaProduto = new CategoriaProduto(retornoDaConsulta);
        }catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(categoriaProduto);
    }
}