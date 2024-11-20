package main.java.tsi.daw.bd;

public enum DataBaseSchema {
	
    REVISTA("revista", new String[]{"idrevista", "colecao", "num_edicao", "ano_revista", "disponibilidade", "idcaixa"}),
    CAIXA("caixa", new String[]{"idcaixa", "cor"}),
    PESSOA("pessoa", new String[]{"idpessoa", "nome", "telefone"}),
    EMPRESTIMO("emprestimo", new String[]{"idemprestimo", "idpessoa", "idrevista", "dataemprestimo", "datadevolucao"}),
    USUARIO("usuario", new String[]{"idusuario", "usuario", "senha"});

    private final String tableName;
    private final String[] columns;

    DataBaseSchema(String tableName, String[] columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public String[] getColumns() {
        return columns;
    }
}