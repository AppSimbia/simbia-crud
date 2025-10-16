package simbia.app.crud.dao;

import simbia.app.crud.infra.dao.abstractclasses.DaoGenerica;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.DaoException;
import simbia.app.crud.model.dao.Plano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe de {@code Data Access Object} da entidade {@link Plano}
 */
public class PlanoDao extends DaoGenerica<Plano> {

//atributos>constantes>comandos-sql
    private static final String COMANDO_INSERIR = "INSERT INTO plano(nvalor, nativo, cnmplano) VALUES (?, ?, ?)";
    private static final String COMANDO_ATUALIZAR = "UPDATE plano SET nvalor=?, nativo=?, cnmplano=? WHERE idplano=?";
    private static final String COMANDO_DELETAR = "DELETE FROM plano WHERE idplano=?";
    private static final String CONSULTA_RECUPERAR_PELO_ID = "SELECT * FROM plano WHERE idplano=?";
    private static final String CONSULTA_RECUPERAR_TUDO = "SELECT * FROM plano";

//operacoes>gerais
    @Override
    public boolean inserir(Plano plano) {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);
            comandoInserir.setBigDecimal(1, plano.getValor());
            comandoInserir.setInt(2, plano.isAtivo() ? 1 : 0);
            comandoInserir.setString(3, plano.getNomePlano());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();
                if(temProximoRegistro(retornoBanco)) plano.setIdPlano(retornoBanco.getLong("idplano"));
                sucessoDaOperacao = true;
            }
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally{
            ManipuladorConexao.desconectar(conexao);
        }
        return sucessoDaOperacao;
    }

    @Override
    public boolean atualizar(Plano plano) {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);
            comandoAtualizar.setBigDecimal(1, plano.getValor());
            comandoAtualizar.setInt(2, plano.isAtivo() ? 1 : 0);
            comandoAtualizar.setString(3, plano.getNomePlano());
            comandoAtualizar.setLong(4, plano.getIdPlano());

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
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoDeletar = conexao.prepareStatement(COMANDO_DELETAR);
            comandoDeletar.setLong(1, id);

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoDeletar.executeUpdate());
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return sucessoDaOperacao;
    }

    @Override
    public List<Plano> recuperarTudo() {
        List<Plano> listaPlano = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaTudo = conexao.createStatement();
            ResultSet resultadoDaConsulta = consultaTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(resultadoDaConsulta)) listaPlano.add(new Plano(resultadoDaConsulta));
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaPlano;
    }

    @Override
    public Optional<Plano> recuperarPeloId(long id) {
        Plano plano = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaPeloId.setLong(1, id);
            ResultSet retornoDaConsulta = consultaPeloId.executeQuery();

            if (temProximoRegistro(retornoDaConsulta)) plano = new Plano(retornoDaConsulta);
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(plano);
    }
}