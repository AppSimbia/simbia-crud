package simbia.app.crud.dao;

import simbia.app.crud.infra.dao.abstractclasses.DaoGenerica;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.DaoException;
import simbia.app.crud.model.dao.TipoIndustria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe de {@code Data Access Object} da entidade {@link TipoIndustria}
 */
public class TipoIndustriaDao extends DaoGenerica<TipoIndustria> {
//atributos>constantes>comandos-sql
    private static final String COMANDO_INSERIR = "INSERT INTO tipoindustria(cnmtipoindustria, cdescricao) VALUES (?, ?)";
    private static final String COMANDO_ATUALIZAR = "UPDATE tipoindustria SET cnmtipoindustria=?, cdescricao=? WHERE idtipoindustria=?";
    private static final String COMANDO_DELETAR = "DELETE FROM tipoindustria WHERE idtipoindustria=?";
    private static final String CONSULTA_RECUPERAR_PELO_ID = "SELECT * FROM tipoindustria WHERE idtipoindustria=?";
    private static final String CONSULTA_RECUPERAR_TUDO = "SELECT * FROM tipoindustria";

//operacoes>gerais
    @Override
    public boolean inserir(TipoIndustria tipoIndustria) {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);
            comandoInserir.setString(1, tipoIndustria.getNomeTipoIndustria());
            comandoInserir.setString(2, tipoIndustria.getDescricao());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();

                if(temProximoRegistro(retornoBanco)){
                    tipoIndustria.setIdTipoIndustria(retornoBanco.getLong("idtipoindustria"));
                    sucessoDaOperacao = true;
                }
            }
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return sucessoDaOperacao;
    }

    @Override
    public boolean atualizar(TipoIndustria tipoIndustria) {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);
            comandoAtualizar.setString(1, tipoIndustria.getNomeTipoIndustria());
            comandoAtualizar.setString(2, tipoIndustria.getDescricao());
            comandoAtualizar.setLong(3, tipoIndustria.getIdTipoIndustria());

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return sucessoDaOperacao;
    }

    @Override
    public boolean deletar(long id) {
        boolean sucesso = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoDeletar = conexao.prepareStatement(COMANDO_DELETAR);
            comandoDeletar.setLong(1, id);

            sucesso = houveAlteracaoNoBanco(comandoDeletar.executeUpdate());
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return sucesso;
    }

    @Override
    public List<TipoIndustria> recuperarTudo() {
        List<TipoIndustria> listaTipoIndustria = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaRecuperarTudo = conexao.createStatement();
            ResultSet retornoBanco = consultaRecuperarTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(retornoBanco)) listaTipoIndustria.add(new TipoIndustria(retornoBanco));
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaTipoIndustria;
    }

    @Override
    public Optional<TipoIndustria> recuperarPeloId(long id) {
        TipoIndustria tipoIndustria = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaRecuperarPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaRecuperarPeloId.setLong(1, id);

            ResultSet retornoBanco = consultaRecuperarPeloId.executeQuery();
            if (temProximoRegistro(retornoBanco)) tipoIndustria = new TipoIndustria(retornoBanco);
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(tipoIndustria);
    }
}
