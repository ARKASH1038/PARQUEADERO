package app;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {


    // 1. CONSTANTES

    static final int CAPACIDAD_TOTAL = 10;
    static final int TARIFA_HORA_CARRO = 4000;
    static final int TARIFA_DIA_CARRO = 30000;
    static final int TARIFA_HORA_MOTO = 2000;
    static final int TARIFA_DIA_MOTO = 15000;
    static final int UMBRAL_HORAS_DIA = 8;
    static final int HORA_APERTURA = 8;
    static final int HORA_CIERRE = 22;

    static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    // 2. ENTRADA

    static final Scanner SCANNER = new Scanner(System.in);


    // 3. ARREGLOS

    static String[] tiposVehiculo = new String[CAPACIDAD_TOTAL];
    static LocalDateTime[] fechasIngreso = new LocalDateTime[CAPACIDAD_TOTAL];


    // 4. VARIABLES

    static int celdasOcupadas = 0;
    static int celdasLibres = CAPACIDAD_TOTAL;
    static long recaudoCarros = 0;
    static long recaudoMotos = 0;
    static long recaudoTotal = 0;

    public static void main(String[] args) {
        inicializarDatos();



        SCANNER.close();
    }


    // MÉTODOS


    static void inicializarDatos() {
        for (int i = 0; i < CAPACIDAD_TOTAL; i++) {
            tiposVehiculo[i] = null;
            fechasIngreso[i] = null;
        }
        celdasOcupadas = 0;
        celdasLibres = CAPACIDAD_TOTAL;
        recaudoCarros = 0;
        recaudoMotos = 0;
        recaudoTotal = 0;
    }

    static void imprimir(String mensaje) {
        System.out.println(mensaje);
    }
}