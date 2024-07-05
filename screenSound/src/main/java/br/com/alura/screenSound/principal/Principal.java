package br.com.alura.screenSound.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.com.alura.screenSound.model.Artista;
import br.com.alura.screenSound.model.Musica;
import br.com.alura.screenSound.model.TipoArtista;
import br.com.alura.screenSound.repository.ArtistaRepository;
 

public class Principal {

    Scanner sc = new Scanner(System.in);
    private ArtistaRepository repositorio;

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }
    public void exibeMenu() {

        var opcao = -1;

        while (opcao != 9) {
           var menu = """
                    *** Screen Sound Músicas ***
                    
                     1 - Cadastrar artistas 
                     2 - Cadastrar músicas
                     3 - Listar músicas
                     4 - Buscar músicas por artistas
                     5 - Pesquisar dados sobre um artista 

                     9 - Sair 
                    """; 

        System.out.println(menu);
        opcao = sc.nextInt();
        sc.nextLine();
        switch (opcao) {
            case 1:
                cadastrarArtistas(); 
                break;
            case 2: 
                cadastrarMusicas();
                break;
            case 3:
                listarMusicas();
                break;
            case 4:
                buscarMusicasPorArtista();
                break;
            // case 5: 
            //     pesquisarDadosDoArtista();
            //     break;
            
            case 9: 
                System.out.println("Encerrando a aplicação...");
        
        }
        
        
        }
    }
    // private void pesquisarDadosDoArtista() {
    //     System.out.println("Pesquisar dados sobre qual artista? ");
    //     var nome = sc.nextLine();
    //     var resposta = ConsultaChatGPT.obterInformacao(nome);
    //     System.out.println(resposta.trim());
    // }
    private void buscarMusicasPorArtista() {
        System.out.println("Buscar músicas de qual artista? ");
        var nome = sc.nextLine();
        List<Musica> musicas = repositorio.buscarMusicasPorArtista(nome);
        musicas.forEach(System.out::println);

    }
    private void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }
    private void cadastrarMusicas() {
       
        System.out.println("Cadastrar música de qual artista? ");
        var nome = sc.nextLine();
        Optional <Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);

        if (artista.isPresent()){
            System.out.println("Informe o título da música: ");
            var nomeMusica = sc.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
        } else{
            System.out.println("Artista não encontrado! ");
        }

    }
    private void cadastrarArtistas() {

        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("s")) {
            
        

        System.out.println("Informe nome do artista: ");
        var nome = sc.nextLine();
        System.out.println("Informe o tipo de artista(solo, dupla ou banda): ");
        var tipo = sc.nextLine();

        TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
        Artista artista = new Artista (nome, tipoArtista);

        repositorio.save(artista);

        System.out.println("Cadastrar novo artista? (S/N) ");
        cadastrarNovo = sc.nextLine();
        
    
    }

    }

    
}
