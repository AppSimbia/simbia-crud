package simbia.app.crud.dao;

import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.abstractclasses.DaoGenerica;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.model.dao.VantagemPlano;
import simbia.app.crud.util.ValidacoesDeDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static simbia.app.crud.util.UtilitariosException.gerarExceptionEspecializadaPorSQLException;

/**
 * Classe de {@code Data Access Object} da entidade {@link VantagemPlano}
 */
public class VantagemPlanoDao extends DaoGenerica<VantagemPlano> {

//atributos>constantes>comandos-sql
    private static final String COMANDO_INSERIR = "INSERT INTO vantagemplano(idplano, idvantagem) VALUES (?, ?)";
    private static final String COMANDO_ATUALIZAR = "UPDATE vantagemplano SET idplano=?, idvantagem=? WHERE idvantagemplano=?";
    private static final String COMANDO_DELETAR = "DELETE FROM vantagemplano WHERE idvantagemplano=?";
    private static final String CONSULTA_RECUPERAR_PELO_ID = "SELECT * FROM vantagemplano WHERE idvantagemplano=?";
    private static final String CONSULTA_RECUPERAR_TUDO = "SELECT * FROM vantagemplano";

//operacoes>gerais
    @Override
    public void inserir(VantagemPlano vantagemPlano) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);
            comandoInserir.setLong(1, vantagemPlano.getIdPlano());
            comandoInserir.setLong(2, vantagemPlano.getIdVantagem());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();

                if(temProximoRegistro(retornoBanco)) {
                    vantagemPlano.setIdVantagemPlano(retornoBanco.getLong("idvantagemplano"));
                    sucessoDaOperacao = true;
                }

            }

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        }catch (SQLException causa){
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public void atualizar(VantagemPlano vantagemPlano) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException{
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);
            comandoAtualizar.setLong(1, vantagemPlano.getIdPlano());
            comandoAtualizar.setLong(2, vantagemPlano.getIdVantagem());
            comandoAtualizar.setLong(3, vantagemPlano.getIdVantagemPlano());

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        }catch (SQLException causa){
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

        }catch (SQLException causa){
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public List<VantagemPlano> recuperarTudo() throws DaoException {
        List<VantagemPlano> listaVantagemPlano = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaRecuperarTudo = conexao.createStatement();
            ResultSet retornoBanco = consultaRecuperarTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(retornoBanco)) listaVantagemPlano.add(new VantagemPlano(retornoBanco));
        }catch (SQLException causa){
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaVantagemPlano;
    }

    @Override
    public Optional<VantagemPlano> recuperarPeloId(long id) throws DaoException {
        VantagemPlano vantagemPlano = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaRecuperarPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaRecuperarPeloId.setLong(1, id);

            ResultSet retornoBanco = consultaRecuperarPeloId.executeQuery();
            if (temProximoRegistro(retornoBanco)) vantagemPlano = new VantagemPlano(retornoBanco);
        }catch (SQLException causa){
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(vantagemPlano);
    }
}
