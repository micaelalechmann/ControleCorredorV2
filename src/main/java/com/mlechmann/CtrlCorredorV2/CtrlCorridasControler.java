package com.mlechmann.CtrlCorredorV2;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ctrlCorridas")
public class CtrlCorridasControler {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private EventoRepository eventoRepository;
    private CorredorRepository corredorRepository;

    @Autowired
    public CtrlCorridasControler(EventoRepository eventoRepository, CorredorRepository corredorRepository) {
        this.eventoRepository = eventoRepository;
        this.corredorRepository = corredorRepository;
    }

    @GetMapping("/corredor")
    @CrossOrigin(origins = "*")
    public List<Corredor> consultaCorredor() {
        return corredorRepository.corredores();
    }

    @PostMapping("/corredor")
    @CrossOrigin(origins = "*")
    public boolean cadastraCorredor(@RequestBody final Corredor corredor) {
        // Limpa a base de dados
        corredorRepository.removerCorredores();
        // Então cadastra o novo "corredor único"
        return corredorRepository.adicionar(corredor);
    }

    @GetMapping("/eventos")
    @CrossOrigin(origins = "*")
    public List<Evento> consultaEventos() {
        return eventoRepository.eventos();
    }

    @PostMapping("/eventos") // adiciona evento no único corredor
    @CrossOrigin(origins = "*")
    public boolean informaEvento(@RequestBody final Evento evento) {
        return eventoRepository.adicionar(evento);
    }

    @GetMapping("/estatisticas")
    @CrossOrigin(origins = "*")
    public EstatisticasDTO estatisticas(@RequestParam final int distancia) {
        EstatisticasDTO estatisticasDTO = new EstatisticasDTO();
        List<Evento> eventos = eventoRepository.eventos().stream().filter(e -> e.getDistancia() == distancia).collect(Collectors.toList());

        List<Double> tempos = eventos.stream().map(e -> (e.getHoras() * 60.0) + (e.getMinutos()) + (e.getSegundos() / 60.0)).collect(Collectors.toList());

        double tempoTotal = tempos.stream().reduce(0.0, (acc, t) -> t + acc);

        double media = tempoTotal / eventos.size();

        double mediana;

        if (eventos.size() % 2 == 1) {
            mediana = tempos.get((int) Math.ceil(eventos.size() / 2));
        } else {
            mediana = (tempos.get(eventos.size() / 2 - 1) + tempos.get(eventos.size() / 2)) / 2;
        }

        double desvio = tempos.stream().reduce(0.0, (acc, t) -> acc + (t - media) * (t - media));

        desvio = Math.sqrt(desvio / (tempos.size()));

        estatisticasDTO.setQntCorridas(eventos.size());
        estatisticasDTO.setMedia(df2.format(media));
        estatisticasDTO.setMediana(df2.format(mediana));
        estatisticasDTO.setDesvioPadrao(df2.format(desvio));

        return estatisticasDTO;
    }

    @GetMapping("/aumentoPerformance")
    @CrossOrigin(origins = "*")
    public PerformanceDTO aumentoPerformance(@RequestParam final int distancia, @RequestParam final int ano) {
        PerformanceDTO performanceDTO = new PerformanceDTO();
        List<Evento> eventos = eventoRepository.eventos().stream().filter(e -> e.getDistancia() == distancia && e.getAno() == ano).collect(Collectors.toList());

        if(eventos.size() == 0) {
            return performanceDTO;
        }

        if(eventos.size() < 2) {
            performanceDTO.setProvaUm(eventos.get(0).getNome());

            return performanceDTO;
        }

        Evento provaAnterior = eventos.get(0);
        Evento provaAtual = eventos.get(1);
        double melhorMelhoria = 0.0;
        double tempoAnterior = provaAnterior.getHoras() * 60.0 + provaAnterior.getMinutos() + provaAnterior.getSegundos() / 60.0;
        int i;

        for (i = 1; i < eventos.size(); i++) {
            Evento evento = eventos.get(i);
            double tempo = (evento.getHoras() * 60.0) + (evento.getMinutos()) + (evento.getSegundos() / 60.0);
            double melhoria = tempoAnterior - tempo;
            if (melhoria > melhorMelhoria) {
                melhorMelhoria = melhoria;
                provaAnterior = eventos.get(i - 1);
                provaAtual = evento;
            }
            tempoAnterior = tempo;
        }

        performanceDTO.setProvaUm(provaAnterior.getNome());
        performanceDTO.setProvaDois(provaAtual.getNome());

        return performanceDTO;
    }
}
