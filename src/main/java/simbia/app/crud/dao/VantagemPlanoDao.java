package simbia.app.crud.dao;

import simbia.app.crud.infra.dao.abstractclasses.DaoGenerica;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.DaoException;
import simbia.app.crud.model.dao.VantagemPlano;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

//oparacoes>gerais
    @Override
    public boolean inserir(VantagemPlano vantagemPlano) {
        boolean sucesso = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);
            comandoInserir.setLong(1, vantagemPlano.getIdPlano());
            comandoInserir.setLong(2, vantagemPlano.getIdVantagem());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();

                if(temProximoRegistro(retornoBanco)) vantagemPlano.setIdVantagemPlano(retornoBanco.getLong("idvantagemplano"));
                sucesso = true;
            }
        }catch (SQLException causa){
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return sucesso;
    }

    @Override
    public boolean atualizar(VantagemPlano vantagemPlano) {
        boolean sucesso = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);
            comandoAtualizar.setLong(1, vantagemPlano.getIdPlano());
            comandoAtualizar.setLong(2, vantagemPlano.getIdVantagem());
            comandoAtualizar.setLong(3, vantagemPlano.getIdVantagemPlano());

            sucesso = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());
        }catch (SQLException causa){
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
        }catch (SQLException causa){
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return sucesso;
    }

    @Override
    public List<VantagemPlano> recuperarTudo() {
        List<VantagemPlano> listaVantagemPlano = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaRecuperarTudo = conexao.createStatement();
            ResultSet retornoBanco = consultaRecuperarTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(retornoBanco)) listaVantagemPlano.add(new VantagemPlano(retornoBanco));
        }catch (SQLException causa){
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaVantagemPlano;
    }

    @Override
    public Optional<VantagemPlano> recuperarPeloId(long id) {
        VantagemPlano vantagemPlano = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaRecuperarPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaRecuperarPeloId.setLong(1, id);

            ResultSet retornoBanco = consultaRecuperarPeloId.executeQuery();
            if (temProximoRegistro(retornoBanco)) vantagemPlano = new VantagemPlano(retornoBanco);
        }catch (SQLException causa){
            throw new DaoException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(vantagemPlano);
    }
}
