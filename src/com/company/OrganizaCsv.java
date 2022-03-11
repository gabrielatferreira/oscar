package com.company;

import lombok.*;
import java.text.DecimalFormat;

@Getter
@Setter
@Value
public class OrganizaCsv {

    Integer index;
    Double year;
    Double age;
    String name;
    String movie;

    OrganizaCsv(Integer index, Double year, Double age, String name, String movie) {
        this.index = index;
        this.year = year;
        this.age = age;
        this.name = name;
        this.movie = movie;
    }

    public static OrganizaCsv of (String line) {
        String[] split = line.split(";");
        return new OrganizaCsv(
                Integer.parseInt(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                split[3],
                split[4]
                );
    }

    public String getResume() {
        return this.getName() + ", ganhou o oscar em "
                + new DecimalFormat("0.#").format(this.getYear())
                + " aos " + new DecimalFormat("0.#").format(this.getAge())
                +" anos com o filme"
                + this.getMovie() + ".";
    }

    @Override
    public String toString() {
        return "OrganizaCsv {" +
                " index = " + index+
                ", year = " + year +
                ", age = " + age +
                ", name =" + name +
                ", movie =" + movie +
                '}';
    }
}
