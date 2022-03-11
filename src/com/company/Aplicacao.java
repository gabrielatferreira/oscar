package com.company;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Aplicacao {

    public static LeituraArquivo leituraArquivoFemale;
    public static LeituraArquivo leituraArquivoMale;

    public static void main(String[] args) {
        //Leitura do arquivo e mapeamento como objeto
        leituraArquivoMale = new LeituraArquivo("C:\\Users\\byzin\\IdeaProjects\\oscar\\src\\com\\company\\resources\\oscar_age_male.csv");
        leituraArquivoFemale = new LeituraArquivo("C:\\Users\\byzin\\IdeaProjects\\oscar\\src\\com\\company\\resources\\oscar_age_female.csv");

        List<OrganizaCsv> atores = leituraArquivoMale.getOrganizaCsv();
        List<OrganizaCsv> atrizes = leituraArquivoFemale.getOrganizaCsv();

        //1. Quem foi o ator mais jovem a ganhar um Oscar?
        //saída: O ator mais jovem a ganhar um oscar foi Marlee Matlin com 21 anos, no ano de 1987, pelo filme Children of a Lesser God.
        atorMaisJovemGanharOscar(atores);

        //2. Quem foi a atriz que mais vezes foi premiada?
        //saída: A atriz mais premiada foi Katharine Hepburn, com 4 premios.
        atrizQueMaisVezesFoiPremiada(atrizes);

        //3. Qual atriz entre 20 e 30 anos que mais vezes foi vencedora?
        //saída: A atriz, entre os 20 e 30 anos de idade, mais premiada foi Bette Davis, com 2 premios.
        atrizEntre20e30AnosMaisVencedora(atrizes);

        //4. Quais atores ou atrizes receberam mais de um Oscar? Elabore uma única estrutura contendo atores e atrizes.
        //saída: Os artistas que receberam mais de um Oscar foram:
        // Dustin Hoffman,  Tom Hanks,  Vivien Leigh,  Jodie Foster,  Gary Cooper,  Jane Fonda,  Olivia de Havilland,  Meryl Streep,
        //      Elizabeth Taylor,  Katharine Hepburn,  Daniel Day-Lewis,  Marlon Brando,  Glenda Jackson,  Sally Field,  Fredric March,
        //          Luise Rainer,  Hilary Swank,  Bette Davis,  Sean Penn,  Spencer Tracy,  Jack Nicholson,  Ingrid Bergman,
        List<OrganizaCsv> todosArtistas = new ArrayList<>();
        todosArtistas.addAll(atrizes);
        todosArtistas.addAll(atores);
        atoresOuAtrizesQueReceberamMaisDeUmOscar(todosArtistas);

        //5. Quando informado o nome de um ator ou atriz,
        //          dê um resumo de quantos prêmios ele/ela recebeu
        //              e liste ano, idade e nome de cada filme pelo qual foi premiado(a).
        String nomeBuscado = digitaNome();
        processaFiltroPorNome(nomeBuscado, todosArtistas);
    }

    private static void atorMaisJovemGanharOscar(List<OrganizaCsv> organizaCsv) {
        Optional<OrganizaCsv> jovem = organizaCsv.stream()
                .min(Comparator.comparing(OrganizaCsv::getAge));
        jovem.ifPresent(it -> System.out.println("O ator mais jovem a ganhar um oscar foi"
                + it.getName() + " com "
                + new DecimalFormat("0.#").format(it.getAge()) + " anos, no ano de "
                + new DecimalFormat("0.#").format(it.getYear()) + ", pelo filme"
                + it.getMovie() + "."));
    }

    private static void atrizQueMaisVezesFoiPremiada(List<OrganizaCsv> organizaCsv) {
        Map<String, Long> premios = organizaCsv.stream()
                .map(OrganizaCsv::getName)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        premios.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(p -> System.out.println("A atriz mais premiada foi" + p.getKey() + ", com " + p.getValue() + " premios."));

    }

    private static void atrizEntre20e30AnosMaisVencedora(List<OrganizaCsv> organizaCsv) {
        Map<String, Long> vencedora = organizaCsv.stream()
                .filter(v -> v.getAge() >= 20 && v.getAge() <= 30)
                .map(OrganizaCsv::getName)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        vencedora.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(p -> System.out.println("A atriz, entre os 20 e 30 anos de idade, mais premiada foi" + p.getKey() + ", com " + p.getValue() + " premios."));
    }

    private static void atoresOuAtrizesQueReceberamMaisDeUmOscar(List<OrganizaCsv> maisOscarMale) {
        Map<String, Long> famosoOscar = maisOscarMale.stream()
                .map(OrganizaCsv::getName)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println("Os artistas que receberam mais de um Oscar foram: ");
        famosoOscar.entrySet().stream().filter(f -> f.getValue() > 1)
                .forEach(a -> System.out.print(a.getKey() + ", "));
    }

    public static String digitaNome() {
        System.out.println("\n Você deseja saber mais sobre qual ator ou atriz?");
        Scanner scanner = new Scanner((System.in));
        return scanner.nextLine();
    }

    public static void processaFiltroPorNome(String nomeBusca, List<OrganizaCsv> famosos) {
        List<OrganizaCsv> organizaCsvStream = famosos.stream()
                        .filter(famoso -> famoso.getName().toUpperCase().trim()
                        .equals(nomeBusca.toUpperCase().trim())).collect(Collectors.toList());
        if(organizaCsvStream != null && organizaCsvStream.size() > 0) {
            organizaCsvStream.forEach(info -> System.out.println(info.getResume()));
        } else {
            System.out.println("Não foi possível encontrar " + nomeBusca + ".");
        }
    }
}
