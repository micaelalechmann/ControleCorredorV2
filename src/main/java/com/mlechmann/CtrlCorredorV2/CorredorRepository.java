package com.mlechmann.CtrlCorredorV2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CorredorRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CorredorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.jdbcTemplate.execute("DROP TABLE corredores IF EXISTS");
        this.jdbcTemplate.execute("CREATE TABLE corredores("
                + "cpf VARCHAR(255), nome VARCHAR(255), genero VARCHAR(255), diaDn int, mesDn int, anoDn int, PRIMARY KEY(cpf))");

        adicionar(new Corredor("11111111111", "Felipe Martins", 30, 11, 1992, "Masculino"));

    }

    public List<Corredor> corredores() {
        List<Corredor> resp = this.jdbcTemplate.query("SELECT * from corredores",
                (rs, rowNum) -> new Corredor(rs.getString("cpf"), rs.getString("nome"), rs.getInt("diaDn"),
                        rs.getInt("mesDn"), rs.getInt("anoDn"), rs.getString("genero")));
        return resp;
    }

    public void removerCorredores() {
        this.jdbcTemplate.batchUpdate(("DELETE from corredores"));
    }

    public boolean adicionar(Corredor corredor) {
        this.jdbcTemplate.update("INSERT INTO corredores(cpf,nome,diaDn,mesDn,anoDn,genero) VALUES (?,?,?,?,?,?)",
                corredor.getCpf(), corredor.getNome(), corredor.getDiaDn(), corredor.getMesDn(), corredor.getAnoDn(),
                corredor.getGenero());
        return true;
    }
}
