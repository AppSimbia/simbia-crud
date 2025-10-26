package simbia.app.crud.infra.dao.abstractclasses;

import simbia.app.crud.infra.dao.exception.errosDeOperacao.NaoHouveAlteracaoNoBancoDeDadosException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Classe abstrata que estabelesse contratos de operações genéricas de DAO e
 * métodos utilitários de abstração simples para legibilidade da implementação.
 *
 * @param <T> Classe entidade da tabela do banco
 */
public abstract class DaoGenerica<T> {

    /**
     * Persiste uma nova entidade no banco de dados e recupera a {@code primary key} gerada para
     * adicona-lá a entidade.

     * @param t representa a entidade que deve estar com os campos preenchidos,
     *          mas sem a {@code primary key}.
     * @return um booleano que é {@code true} caso tenha dado certo, ou {@code false} caso tenha dado errado.
     */
    public abstract void inserir(T t) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException;

    /**
     * Atualiza uma entidade existente no banco de dados.
     *
     * @param t a entidade {@code T} com os dados a serem atualizados.
     *          A {@code primary key} deve estar preenchida para identificar o registro.
     * @return um booleano que é {@code true} caso tenha dado certo, ou {@code false} caso tenha dado errado.
     */
    public abstract void atualizar(T t) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException;

    /**
     * Deleta um registro da entidade {@code T} no banco de dados com base no seu ID.
     *
     * @param id o ID do registro a ser deletado.
     *
     * @return um booleano que é {@code true} caso tenha dado certo, ou {@code false} caso tenha dado errado.
     */
    public abstract void deletar(long id) throws NaoHouveAlteracaoNoBancoDeDadosException, DaoException;

    /**
     * Recupera todos os registros da entidade {@code T} do banco de dados.
     *
     * @return uma {@link List} contendo todas as entidades encontradas.
     *         A lista pode estar vazia caso não haja registros.
     */
    public abstract List<T> recuperarTudo() throws DaoException;

    /**
     * Busca e retorna uma entidade {@code T} específica com base no seu ID.
     *
     * @param id o ID da entidade a ser buscada.
     * @return um {@link Optional} contendo a entidade se ela foi encontrada,
     *         ou {@link Optional#empty()} caso o contrário.
     */
    public abstract Optional<T> recuperarPeloId(long id) throws DaoException;

    //métodos utilitários
    protected boolean houveAlteracaoNoBanco(int linhasAfetadas){
        return linhasAfetadas > 0;
    }

    protected boolean temProximoRegistro(ResultSet retornoBanco) throws SQLException {
        return retornoBanco.next();
    }
}
