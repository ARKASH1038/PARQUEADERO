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

    // 2. RECURSOS

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
        ejecutarMenu();
        SCANNER.close();
    }

    // FLUJO PRINCIPAL Y MENÚ

    static void ejecutarMenu() {
        boolean continuar = true;
        while (continuar) {
            imprimir("\n=== SISTEMA DE GESTIÓN DE PARQUEADERO ===");
            imprimir("1. Registrar entrada de vehículo");
            imprimir("2. Registrar salida de vehículo");
            imprimir("3. Reporte de ocupación");
            imprimir("4. Reporte financiero");
            imprimir("5. Salir");

            int opcion = leerOpcionMenu();

            switch (opcion) {
                case 1:
                    registrarEntrada();
                    break;
                case 2:
                    imprimir("Funcionalidad de salida en construcción...");
                    break;
                case 3:
                    imprimir("Reporte de ocupación en construcción...");
                    break;
                case 4:
                    imprimir("Reporte financiero en construcción...");
                    break;
                case 5:
                    continuar = false;
                    imprimir("Saliendo del sistema...");
                    break;
            }
        }
    }

    // PASO 3: REGISTRO DE ENTRADA

    static void registrarEntrada() {
        int indiceCelda = buscarCeldaDisponible();

        if (indiceCelda == -1) {
            imprimir("El parqueadero está lleno. No hay celdas disponibles.");
            return;
        }

        String tipoVehiculo = leerTipoVehiculo();
        LocalDateTime fechaHoraIngreso = leerFechaHora("Ingrese fecha y hora de entrada (yyyy-MM-dd HH:mm): ");

        tiposVehiculo[indiceCelda] = tipoVehiculo;
        fechasIngreso[indiceCelda] = fechaHoraIngreso;

        celdasOcupadas++;
        celdasLibres--;

        int numeroCelda = indiceCelda + 1;
        imprimir("Ingreso registrado exitosamente. Vehículo (" + tipoVehiculo + ") asignado a la celda #" + numeroCelda + ".");
    }

    // MÉTODOS BASE (Se completarán en los Pasos 4 y 5)
    static int buscarCeldaDisponible() {
        return 0;
    }

    static int leerOpcionMenu() {
        System.out.print("Seleccione una opción: ");
        return Integer.parseInt(SCANNER.nextLine().trim());
    }

    static String leerTipoVehiculo() {
        System.out.print("Ingrese el tipo de vehículo (CARRO / MOTO): ");
        return SCANNER.nextLine().trim().toUpperCase();
    }

    static LocalDateTime leerFechaHora(String mensaje) {
        System.out.print(mensaje);
        String texto = SCANNER.nextLine().trim();
        return LocalDateTime.parse(texto, FORMATO_FECHA_HORA);
    }

    // MÉTODOS ADICIONALES DE ORGANIZACIÓN

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