package simbia.app.crud.dao;

import simbia.app.crud.infra.dao.abstractclasses.DaoGenerica;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.DaoException;
import simbia.app.crud.model.dao.Vantagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public boolean inserir(Vantagem vantagem) {
        boolean sucesso = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);
            comandoInserir.setString(1, vantagem.getNomeVantagem());
            comandoInserir.setString(2, vantagem.getDescricao());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();

                if(temProximoRegistro(retornoBanco)) vantagem.setIdVantagem(retornoBanco.getLong("idvantagem"));
                sucesso = true;
            }
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return sucesso;
    }

    @Override
    public boolean atualizar(Vantagem vantagem) {
        boolean sucesso = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);

            comandoAtualizar.setString(1, vantagem.getNomeVantagem());
            comandoAtualizar.setString(2, vantagem.getDescricao());
            comandoAtualizar.setLong(3, vantagem.getIdVantagem());

            sucesso = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return sucesso;
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
    public List<Vantagem> recuperarTudo() {
        List<Vantagem> listaVantagem = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaRecuperarTudo = conexao.createStatement();
            ResultSet retornoBanco = consultaRecuperarTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(retornoBanco)) listaVantagem.add(new Vantagem(retornoBanco));
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaVantagem;
    }

    @Override
    public Optional<Vantagem> recuperarPeloId(long id) {
        Vantagem vantagem = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaRecuperarPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaRecuperarPeloId.setLong(1, id);

            ResultSet retornoBanco = consultaRecuperarPeloId.executeQuery();
            if (temProximoRegistro(retornoBanco)) vantagem = new Vantagem(retornoBanco);
        }catch (SQLException causa) {
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(vantagem);
    }
}
