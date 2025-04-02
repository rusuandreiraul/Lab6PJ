package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;

import java.io.IOException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Math.min;


public class Main {

    public static void scriere(List<Angajat> lista){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            File f = new File("src/main/resources/angajati.json");
            mapper.writeValue(f, lista);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static List<Angajat> citire(){
        try{
            File f=new File("src/main/resources/angajati.json");
            ObjectMapper mapper=new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // nu poate citi LocalDate
            List<Angajat> angajati=mapper.readValue(f, new TypeReference<List<Angajat>>(){});
            return angajati;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {

        List<Angajat> angajati=citire();

       int opt;
       Scanner s=new Scanner(System.in);
       do{
           System.out.println("1. afisare angajati\n2. aifdsare angajati cu peste 2500 ron\n3.lista angajati din aprilie cu functie conducere\n4.angajati fara functie de conducere\n5.extragerea din lista doar a numelui fiecarui angajat in majuscule\n6.saalrii mai mici de 3000 ron doar salarii\n7.afisare primul angajat al firmei\n8.salariu mediu, saalariu minim si maxim\n9.aifsare daca exista un Ion\n10.aifsare numar persoane angajate vara trecuta");
           opt=s.nextInt();
           switch(opt){
               case 1: afisare(angajati); break;
               case 2: afisarePeste2500(angajati); break;
               case 3: {
                   int an;
                   System.out.println("selecteaza anul curent: ");
                   an=s.nextInt();
                   List<Angajat> a = listaAngajatiAprilie(angajati,an);
                   if(a==null){
                       System.out.println("Nu exista angajatii din luna aprilie anul trecut care sa fie sefi");
                   }
                   a.forEach(System.out::println);
                   break;
               }
               case 4: {
                   angajatiFaraConducere(angajati); break;
               }
               case 5:
                   numeAngajati(angajati);
                   break;
               case 6:
                   afisareSub3000(angajati);
                   break;
               case 7:
                   break;
               case 8:
                   statisticaSalarii(angajati);
                   break;
               case 9:
                   existaIon(angajati);
                   break;
               case 10:
                   angajatiVaraTrecuta(angajati);
           }

       }while (opt!=11);
    }

    public static void afisare(List<Angajat>lista){
        lista.forEach(System.out::println);
    }
    public static void afisarePeste2500(List<Angajat>lista){
        lista
                .stream() //transform lista intrun stream
                .filter((a)->a.getSalariul()>2500) //compar si iau angajatii cu salariu de peste 2500
                .forEach(System.out::println); //afisez toti angajatii care respecta expresia lambda
    }
    public static List<Angajat> listaAngajatiAprilie(List<Angajat>lista, int an){
        List<Angajat>angajati=lista
                .stream()
                .filter((a)->a.getData_angajarii().getMonthValue()==4 && a.getData_angajarii().getYear()==an && (a.getPost().equalsIgnoreCase("sef") || a.getPost().equalsIgnoreCase("director")))
                .collect(Collectors.toList());

                return angajati;
    }
    public static void angajatiFaraConducere(List<Angajat>lista){
        lista
                .stream()
                .filter((a)-> !a.getPost().equalsIgnoreCase("sef") && !a.getPost().equalsIgnoreCase("director"))
                .sorted((a1, a2)-> a1.compareTo(a2))
                .forEach(System.out::println);
    }
    public static void numeAngajati(List<Angajat>lista){
        List<String>nume=
                lista
                        .stream()
                        .map((a)->a.getNume().toUpperCase())
                        .collect(Collectors.toList());

        System.out.println("numele tututor angajatilor este: ");
        nume.forEach(System.out::println);

    }
    public static void afisareSub3000(List<Angajat>lista){
        List<Float>salarii=
                lista
                        .stream()
                        .map((a)->a.getSalariul())
                        .filter((s)->s<3000)
                        .collect(Collectors.toList());
        System.out.println("salarile mai mici de 3000 RON sunt: ");
        salarii.forEach(System.out::println);
    }

    public static void primulAngajat(List<Angajat>lista){
        lista
                .stream()
                .min(Comparator.comparing(Angajat::getData_angajarii))
                .orElse(null).toString();
    }

    public static void statisticaSalarii(List<Angajat>lista){
        DoubleSummaryStatistics statistica=
                lista
                        .stream()
                        .collect(Collectors.summarizingDouble((a)->(double)a.getSalariul()));

        System.out.println("minimul este: " + statistica.getMin());
        System.out.println("maximul este: " + statistica.getMax());
        System.out.println("salariul mediu este: " + statistica.getAverage());
    }
    public static void existaIon(List<Angajat>lista){
        lista
                .stream()
                .filter((a)->a.getNume().equalsIgnoreCase("Ion"))
                .findAny()
                .ifPresentOrElse(a-> System.out.println("Firma are cel putin un Ion"),()-> System.out.println("Firma NU are nici un Ion"));
    }
    public static void angajatiVaraTrecuta(List<Angajat>lista){
        long numarAngajati=lista
                .stream()
                .filter((a)->(LocalDate.now().getYear()-a.getData_angajarii().getYear()==1) && (a.getData_angajarii().getMonthValue()>=6 && a.getData_angajarii().getMonthValue()<=8))
                .count();

        System.out.println("numar de angajati de vara trecuta sunt: " + numarAngajati);
    }

}