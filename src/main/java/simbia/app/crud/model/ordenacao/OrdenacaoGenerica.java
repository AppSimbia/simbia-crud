package simbia.app.crud.model.ordenacao;


import simbia.app.crud.interfaces.ComparadorCampo;
import simbia.app.crud.infra.servlet.abstractclasses.OrdenarServlet;
import java.util.List;

/**
 * Ordenação GENÉRICA usando Bubble Sort.
 * Funciona para QUALQUER entidade (Administrador, Plano, Vantagem, etc).
 */
public class OrdenacaoGenerica<T> extends OrdenarServlet<T> {

    private final ComparadorCampo<T> comparador;
    private final boolean crescente;
    private final String nomeCriterio;

    /**
     * @param comparador Função que extrai o valor a ser comparado
     * @param crescente true = crescente, false = decrescente
     * @param nomeCriterio Nome do critério (ex: "porId", "porNome")
     */
    public OrdenacaoGenerica(ComparadorCampo<T> comparador, boolean crescente, String nomeCriterio) {
        this.comparador = comparador;
        this.crescente = crescente;
        this.nomeCriterio = nomeCriterio;
    }

    @Override
    public void ordenar(List<T> registros) {
        int tamanho = registros.size();
        boolean houveTroca;

        for (int i = 0; i < tamanho - 1; i++) {
            houveTroca = false;

            for (int j = 0; j < tamanho - i - 1; j++) {
                Comparable valorAtual = comparador.extrairValor(registros.get(j));
                Comparable valorProximo = comparador.extrairValor(registros.get(j + 1));

                // Trata valores nulos
                if (valorAtual == null && valorProximo == null) continue;
                if (valorAtual == null) {
                    if (!crescente) {
                        trocar(registros, j, j + 1);
                        houveTroca = true;
                    }
                    continue;
                }
                if (valorProximo == null) {
                    if (crescente) {
                        trocar(registros, j, j + 1);
                        houveTroca = true;
                    }
                    continue;
                }

                int comparacao = valorAtual.compareTo(valorProximo);
                boolean deveTrocar = crescente ? comparacao > 0 : comparacao < 0;

                if (deveTrocar) {
                    trocar(registros, j, j + 1);
                    houveTroca = true;
                }
            }

            if (!houveTroca) break;
        }
    }

    @Override
    public String nomeDoCriterio() {
        return nomeCriterio;
    }
}
