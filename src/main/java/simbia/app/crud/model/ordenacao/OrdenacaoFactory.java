package simbia.app.crud.model.ordenacao;

import simbia.app.crud.interfaces.ComparadorCampo;
import simbia.app.crud.infra.servlet.abstractclasses.OrdenarServlet;
import simbia.app.crud.model.dao.*;

public class OrdenacaoFactory {
    public static OrdenarServlet<Administrador> criarParaAdministrador(String tipoOrdenacao, boolean crescente) {
        switch (tipoOrdenacao) {
            case "porId":
                return new OrdenacaoGenerica<>(
                        admin -> admin.getIdAdministrador(),
                        crescente,
                        "porId"
                );

            case "porNome":
                return new OrdenacaoGenerica<>(
                        admin -> admin.getNome().toLowerCase(),
                        crescente,
                        "porNome"
                );

            case "porEmail":
                return new OrdenacaoGenerica<>(
                        admin -> admin.getEmail().toLowerCase(),
                        crescente,
                        "porEmail"
                );

            default:
                return criarParaAdministrador("porId", true);
        }
    }
    public static OrdenarServlet<Permissao> criarParaPermissao(String tipoOrdenacao, boolean crescente) {
        switch (tipoOrdenacao) {
            case "porId":
                return new OrdenacaoGenerica<>(
                        permisao -> permisao.getIdPermissao(),
                        crescente,
                        "porId"
                );

            case "porNome":
                return new OrdenacaoGenerica<>(
                        permissao -> permissao.getNomePermissao().toLowerCase(),
                        crescente,
                        "porNome"
                );

            default:
                return criarParaPermissao("porId", true);
        }
    }
    public static OrdenarServlet<Vantagem> criarParaVantagem(String tipoOrdenacao, boolean crescente) {
        switch (tipoOrdenacao) {
            case "porId":
                return new OrdenacaoGenerica<>(
                        vantagem -> vantagem.getIdVantagem(),
                        crescente,
                        "porId"
                );

            case "porNome":
                return new OrdenacaoGenerica<>(
                        vantagem -> vantagem.getNomeVantagem().toLowerCase(),
                        crescente,
                        "porNome"
                );

            default:
                return criarParaVantagem("porId", true);
        }
    }
    public static OrdenarServlet<Vantagem> criarParaVantagem(String tipoOrdenacao, boolean crescente) {
        switch (tipoOrdenacao) {
            case "porId":
                return new OrdenacaoGenerica<>(
                        vantagem -> vantagem.getIdVantagem(),
                        crescente,
                        "porId"
                );

            case "porNome":
                return new OrdenacaoGenerica<>(
                        vantagem -> vantagem.getNomeVantagem().toLowerCase(),
                        crescente,
                        "porNome"
                );

            default:
                return criarParaVantagem("porId", true);
        }
    }

}
