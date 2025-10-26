package simbia.app.crud.infra.servlet.abstractclasses;

import java.util.List;

/**
 * Classe abstrata base para algoritmos de ordenação de listas.
 * Fornece métodos auxiliares comuns e define o contrato que as subclasses
 * devem implementar para ordenar registros segundo critérios específicos.
 *
 * @param <T> Tipo do objeto a ser ordenado
 */
public abstract class OrdenarServlet<T> {

    /**
     * Ordena a lista de registros segundo o critério definido na subclasse.
     *
     * @param registros Lista a ser ordenada (modificada in-place)
     */
    public abstract void ordenar(List<T> registros);

    /**
     * Retorna o nome do critério de ordenação (ex: "id", "nome", "email").
     * Usado para identificar qual ordenação está sendo aplicada.
     */
    public abstract String nomeDoCriterio();
    protected void trocar(List<T> registros, int indicePrimeiro, int indiceSegundo) {
        T temporario = registros.get(indicePrimeiro);
        registros.set(indicePrimeiro, registros.get(indiceSegundo));
        registros.set(indiceSegundo, temporario);
    }
    public void ordenarComLog(List<T> registros) {
        System.out.println("Iniciando ordenação por: " + nomeDoCriterio());
        long inicio = System.currentTimeMillis();

        ordenar(registros);

        long duracao = System.currentTimeMillis() - inicio;
        System.out.println("Ordenação concluída em " + duracao + "ms - " + registros.size() + " registros");
    }
}