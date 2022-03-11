package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeituraArquivo {

    public List<OrganizaCsv> organizaCsv;

    public LeituraArquivo(String fileName) {
        this.organizaCsv = lerArquivoCsv(fileName);
    }

    private List<OrganizaCsv> lerArquivoCsv(String filepath) {
        try (Stream<String> fileLines = Files.lines(Path.of(filepath))) {
            return fileLines.skip(1)
                    .map(OrganizaCsv::of)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<OrganizaCsv> getOrganizaCsv() {
        return organizaCsv;
    }
}
