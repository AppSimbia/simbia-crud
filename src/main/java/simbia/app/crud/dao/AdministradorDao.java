package simbia.app.crud.dao;

import org.mindrot.jbcrypt.BCrypt;
import simbia.app.crud.infra.dao.abstractclasses.DaoException;
import simbia.app.crud.infra.dao.abstractclasses.DaoManipuladorDeSenhasEmails;
import simbia.app.crud.infra.dao.conection.ManipuladorConexao;
import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;
import simbia.app.crud.model.dao.Administrador;
import simbia.app.crud.util.ValidacoesDeDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static simbia.app.crud.util.UtilitariosException.gerarExceptionEspecializadaPorSQLException;

/**
 * Classe de {@code Data Access Object} da entidade {@link Administrador}
 */
public class AdministradorDao extends DaoManipuladorDeSenhasEmails<Administrador> {

//atributos>constantes>comandos-sql
    private static final String COMANDO_INSERIR = "INSERT INTO administrador(cemail, csenha, cnome) VALUES (?, ?, ?)";
    private static final String COMANDO_ATUALIZAR = "UPDATE administrador SET cemail=?, csenha=?, cnome=? WHERE idadministrador=?";
    private static final String COMANDO_ATUALIZAR_SEM_SENHA = "UPDATE administrador SET cemail=?, cnome=? WHERE idadministrador=?";
    private static final String COMANDO_DELETAR = "DELETE FROM administrador WHERE idadministrador=?";
    private static final String CONSULTA_RECUPERAR_PELO_ID = "SELECT * FROM administrador WHERE idadministrador=?";
    private static final String CONSULTA_RECUPERAR_TUDO = "SELECT * FROM administrador";
    private static final String CONSULTA_LOGIN = "SELECT * FROM administrador WHERE cemail=?";

//operacoes>gerais
    @Override
    public void inserir(Administrador administrador) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement comandoInserir = conexao.prepareStatement(COMANDO_INSERIR, Statement.RETURN_GENERATED_KEYS);

            comandoInserir.setString(1, administrador.getEmail());
            comandoInserir.setString(2, gerarHashBCrypt(administrador.getSenha()));
            comandoInserir.setString(3, administrador.getNome());

            if (houveAlteracaoNoBanco(comandoInserir.executeUpdate())){
                ResultSet retornoBanco = comandoInserir.getGeneratedKeys();

                if (temProximoRegistro(retornoBanco)){
                    administrador.setIdAdministrador(retornoBanco.getLong("idadministrador"));
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
    public void atualizar(Administrador administrador) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try {
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);
            comandoAtualizar.setString(1, administrador.getEmail());
            comandoAtualizar.setString(2, administrador.getSenha());
            comandoAtualizar.setString(3, administrador.getNome());
            comandoAtualizar.setLong(4, administrador.getIdAdministrador());

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public void atualizarSemSenha(Administrador administrador) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try {
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR_SEM_SENHA);
            comandoAtualizar.setString(1, administrador.getEmail());
            comandoAtualizar.setString(2, administrador.getNome());
            comandoAtualizar.setLong(3, administrador.getIdAdministrador());

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
    }

    @Override
    public void atualizarSerializandoSenha(Administrador administrador) {
        boolean sucessoDaOperacao = false;
        Connection conexao = ManipuladorConexao.conectar();
        try {
            PreparedStatement comandoAtualizar = conexao.prepareStatement(COMANDO_ATUALIZAR);
            comandoAtualizar.setString(1, administrador.getEmail());
            comandoAtualizar.setString(2, gerarHashBCrypt(administrador.getSenha()));
            comandoAtualizar.setString(3, administrador.getNome());
            comandoAtualizar.setLong(4, administrador.getIdAdministrador());

            sucessoDaOperacao = houveAlteracaoNoBanco(comandoAtualizar.executeUpdate());

            ValidacoesDeDados.validarSucessoDeOperacao(sucessoDaOperacao);

        } catch (SQLException causa) {
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
    public List<Administrador> recuperarTudo() throws DaoException{
        List<Administrador> listaAdministradores = new ArrayList<>();
        Connection conexao = ManipuladorConexao.conectar();
        try{
            Statement consultaTudo = conexao.createStatement();
            ResultSet retornoBanco = consultaTudo.executeQuery(CONSULTA_RECUPERAR_TUDO);

            while (temProximoRegistro(retornoBanco)){
                listaAdministradores.add(new Administrador(retornoBanco));
            }
        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return listaAdministradores;
    }

    @Override
    public Optional<Administrador> recuperarPeloId(long id) throws DaoException{
        Administrador administrador = null;
        Connection conexao = ManipuladorConexao.conectar();
        try{
            PreparedStatement consultaPeloId = conexao.prepareStatement(CONSULTA_RECUPERAR_PELO_ID);
            consultaPeloId.setLong(1, id);
            ResultSet retornoDoBanco = consultaPeloId.executeQuery();

            if (temProximoRegistro(retornoDoBanco)) administrador = new Administrador(retornoDoBanco);
        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(administrador);
    }
//operacoes>especificas
    @Override
    public Optional<Administrador> recuperarPeloEmailESenha(String email, String senhaPura) throws DaoException{
        Connection conexao = ManipuladorConexao.conectar();
        Administrador registroCorrepondente = null;
        try{
            PreparedStatement consultaLogin = conexao.prepareStatement(CONSULTA_LOGIN);
            consultaLogin.setString(1, email);
            ResultSet retornoDoBanco = consultaLogin.executeQuery();

            if (temProximoRegistro(retornoDoBanco)){
                if (BCrypt.checkpw(senhaPura, retornoDoBanco.getString("csenha"))){
                    registroCorrepondente = new Administrador(retornoDoBanco);
                }
            }
        } catch (SQLException causa) {
            throw gerarExceptionEspecializadaPorSQLException(causa);
        }finally {
            ManipuladorConexao.desconectar(conexao);
        }
        return Optional.ofNullable(registroCorrepondente);
    }
}

