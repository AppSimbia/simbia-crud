package simbia.app.crud.model.ordenacao;

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
    public static OrdenarServlet<Plano> criarParaPlano(String tipoOrdenacao, boolean crescente) {
        switch (tipoOrdenacao) {
            case "porId":
                return new OrdenacaoGenerica<>(
                        plano -> plano.getIdPlano(),
                        crescente,
                        "porId"
                );

            case "porNome":
                return new OrdenacaoGenerica<>(
                        plano -> plano.getNomePlano().toLowerCase(),
                        crescente,
                        "porNome"
                );
            case "porValor":
                return new OrdenacaoGenerica<>(
                        plano -> plano.getValor(),
                        crescente,
                        "porValor"
                );

            default:
                return criarParaPlano("porId", true);
        }
    }
    public static OrdenarServlet<VantagemPlano> criarParaVantagemPlano(String tipoOrdenacao, boolean crescente) {
        switch (tipoOrdenacao) {
            case "porId":
                return new OrdenacaoGenerica<>(
                        vantagemPlano -> vantagemPlano.getIdVantagemPlano(),
                        crescente,
                        "porId"
                );

            case "porIdPlano":
                return new OrdenacaoGenerica<>(
                        vantagemPlano -> vantagemPlano.getIdPlano(),
                        crescente,
                        "porIdPlano"
                );
            case "porIdVantagem":
                return new OrdenacaoGenerica<>(
                        vantagemPlano -> vantagemPlano.getIdVantagem(),
                        crescente,
                        "porIdVantagem"
                );

            default:
                return criarParaVantagemPlano("porId", true);
        }
    }
    public static OrdenarServlet<TipoIndustria> criarParaTipoIndustria(String tipoOrdenacao, boolean crescente) {
        switch (tipoOrdenacao) {
            case "porId":
                return new OrdenacaoGenerica<>(
                        tipoIndustria -> tipoIndustria.getIdTipoIndustria(),
                        crescente,
                        "porId"
                );

            case "porNome":
                return new OrdenacaoGenerica<>(
                        tipoIndustria -> tipoIndustria.getNomeTipoIndustria().toLowerCase(),
                        crescente,
                        "porNome"
                );
            default:
                return criarParaTipoIndustria("porId", true);
        }
    }
    public static OrdenarServlet<CategoriaProduto> criarParaCategoriaProduto(String tipoOrdenacao, boolean crescente) {
        switch (tipoOrdenacao) {
            case "porId":
                return new OrdenacaoGenerica<>(
                        categoriaProduto -> categoriaProduto.getIdCategoriaProduto(),
                        crescente,
                        "porId"
                );

            case "porNome":
                return new OrdenacaoGenerica<>(
                        categoriaProduto -> categoriaProduto.getNomeCategoria().toLowerCase(),
                        crescente,
                        "porNome"
                );
            default:
                return criarParaCategoriaProduto("porId", true);
        }
    }

}
