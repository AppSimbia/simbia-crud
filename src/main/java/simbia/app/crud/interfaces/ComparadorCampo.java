package simbia.app.crud.interfaces;

/**
 * Interface para extrair valores comparáveis de entidades.
 * @param <T> Tipo da entidade
 */
@FunctionalInterface
public interface ComparadorCampo<T> {
    /**
     * Extrai o valor a ser comparado da entidade.
     * @return Pode retornar Integer, String, etc.
     */
    Comparable extrairValor(T entidade);
}
