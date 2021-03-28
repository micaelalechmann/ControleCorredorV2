package com.mlechmann.CtrlCorredorV2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventoRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EventoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.jdbcTemplate.execute("DROP TABLE eventos IF EXISTS");
        this.jdbcTemplate.execute("CREATE TABLE eventos("
                + "id int, nome VARCHAR(255), dia int, mes int, ano int, distancia int, horas int, minutos int, segundos int,PRIMARY KEY(id))");

        adicionar(new Evento(1, "Maratona de Porto Alegre", 13, 02, 2019, 42, 3, 10, 22));
        adicionar(new Evento(2, "Maratona de Berlin", 20, 10, 2019, 42, 3, 20, 10));
        adicionar(new Evento(3, "Meia Maratona de Florianopolis", 20, 9, 2019, 21, 1, 45, 19));
        adicionar(new Evento(4, "Maratona de Chicago", 20, 9, 2020, 42, 3, 01, 19));
        adicionar(new Evento(5, "Meia Maratona de Maceio", 30, 12, 2020, 21, 2, 01, 10));
        adicionar(new Evento(6, "Meia Maratona de São Paulo", 28, 02, 2020, 21, 2, 12, 45));


    }

    public List<Evento> eventos() {
        List<Evento> resp = this.jdbcTemplate.query("SELECT * from eventos",
                (rs, rowNum) -> new Evento(rs.getInt("id"), rs.getString("nome"), rs.getInt("dia"), rs.getInt("mes"),
                        rs.getInt("ano"), rs.getInt("distancia"), rs.getInt("horas"), rs.getInt("minutos"),
                        rs.getInt("segundos")));
        return resp;
    }

    public boolean adicionar(Evento evento) {
        this.jdbcTemplate.update(
                "INSERT INTO eventos(id,nome,dia,mes,ano,distancia,horas,minutos,segundos) VALUES (?,?,?,?,?,?,?,?,?)",
                evento.getId(), evento.getNome(), evento.getDia(), evento.getMes(), evento.getAno(),
                evento.getDistancia(), evento.getHoras(), evento.getMinutos(), evento.getSegundos());
        return true;
    }
}
