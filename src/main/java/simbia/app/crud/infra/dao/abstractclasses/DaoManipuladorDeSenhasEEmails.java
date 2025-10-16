package simbia.app.crud.infra.dao.abstractclasses;

import org.mindrot.jbcrypt.BCrypt;
import simbia.app.crud.model.dao.Administrador;

import java.util.Optional;

/**
 * Classe abstrata que estabelesse contratos de operações especificas de manipulação de hash de senha {@code BCrypt}
 * para DAO e métodos utilitários de abstração simples para legibilidade da
 * implementação.
 *
 * @param <T> Classe entidade da tabela do banco
 */
public abstract class DaoManipuladorDeSenhasEEmails<T> extends DaoGenerica<Administrador>{
    /**
     * Método que retona um registro do banco de dados por email e senha.
     *
     * @param email Email da Entidade
     * @param senhaPura Senha da Entidade sem qualquer serializacao
     * @return Um objeto {@code Optional} contendo, caso haja, o registro do banco de dados com o email da Entidade
     */
    public abstract Optional<T> recuperarPeloEmailESenha(String email, String senhaPura);

    //métodos utilitários
    protected static String gerarHashBCrypt(String senhaPura){
        String hashSenha = null;
        if (senhaPura != null){
            hashSenha = BCrypt.hashpw(senhaPura, BCrypt.gensalt(12));
        }
        return hashSenha;
    }
}
