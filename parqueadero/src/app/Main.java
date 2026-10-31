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
                    registrarSalida();
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

    // PASO 4: CELDA DISPONIBLE

    static int buscarCeldaDisponible() {
        for (int i = 0; i < CAPACIDAD_TOTAL; i++) {
            if (tiposVehiculo[i] == null) {
                return i;
            }
        }
        return -1;
    }

    // PASO 5: VALIDACIÓN DE DATOS

    static int leerOpcionMenu() {
        while (true) {
            System.out.print("Seleccione una opción (1-5): ");
            String entrada = SCANNER.nextLine().trim();
            if (entrada.isEmpty()) {
                imprimir("Opción inválida. Debe ingresar un número entre 1 y 5.");
                continue;
            }
            int opcion = leerDigitos(entrada, 0, entrada.length());
            if (opcion >= 1 && opcion <= 5) {
                return opcion;
            }
            imprimir("Opción inválida. Debe ingresar un número entre 1 y 5.");
        }
    }

    static String leerTipoVehiculo() {
        while (true) {
            System.out.print("Ingrese el tipo de vehículo (CARRO / MOTO): ");
            String tipo = SCANNER.nextLine().trim().toUpperCase();
            if (tipo.equals("CARRO") || tipo.equals("MOTO")) {
                return tipo;
            }
            imprimir("Tipo de vehículo inválido. Ingrese únicamente CARRO o MOTO.");
        }
    }

    static int leerNumeroCelda() {
        while (true) {
            System.out.print("Ingrese el número de celda (1-" + CAPACIDAD_TOTAL + "): ");
            String entrada = SCANNER.nextLine().trim();
            if (entrada.isEmpty()) {
                imprimir("Celda inválida. Ingrese un número entre 1 y " + CAPACIDAD_TOTAL + ".");
                continue;
            }
            int celda = leerDigitos(entrada, 0, entrada.length());
            if (celda >= 1 && celda <= CAPACIDAD_TOTAL) {
                return celda;
            }
            imprimir("Celda inválida. Ingrese un número entre 1 y " + CAPACIDAD_TOTAL + ".");
        }
    }

    static LocalDateTime leerFechaHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine().trim();
            if (!formatoFechaHoraValido(texto)) {
                imprimir("Fecha u hora inválidas. Use yyyy-MM-dd HH:mm y el horario 08:00-22:00.");
                continue;
            }

            LocalDateTime fechaHora = LocalDateTime.parse(texto, FORMATO_FECHA_HORA);
            if (!fechaHora.format(FORMATO_FECHA_HORA).equals(texto)) {
                imprimir("La fecha ingresada no existe en el calendario. Intente nuevamente.");
                continue;
            }

            return fechaHora;
        }
    }

    static boolean formatoFechaHoraValido(String texto) {
        if (texto.length() != 16
                || texto.charAt(4) != '-'
                || texto.charAt(7) != '-'
                || texto.charAt(10) != ' '
                || texto.charAt(13) != ':') {
            return false;
        }

        int anio = leerDigitos(texto, 0, 4);
        int mes = leerDigitos(texto, 5, 2);
        int dia = leerDigitos(texto, 8, 2);
        int hora = leerDigitos(texto, 11, 2);
        int minuto = leerDigitos(texto, 14, 2);

        return anio >= 1 && mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31
                && hora >= HORA_APERTURA && hora <= HORA_CIERRE
                && (hora != HORA_CIERRE || minuto <= 0)
                && minuto >= 0 && minuto <= 59;
    }

    static int leerDigitos(String texto, int inicio, int cantidad) {
        int valor = 0;
        for (int i = inicio; i < inicio + cantidad; i++) {
            char caracter = texto.charAt(i);
            if (caracter < '0' || caracter > '9') {
                return -1;
            }
            valor = valor * 10 + caracter - '0';
        }
        return valor;
    }

    // PASO 6: REGISTRO

    static void registrarSalida() {
        if (celdasOcupadas == 0) {
            imprimir("No hay vehículos en el parqueadero actualmente.");
            return;
        }

        int numeroCelda = leerNumeroCelda();
        int indiceCelda = numeroCelda - 1;

        if (tiposVehiculo[indiceCelda] == null) {
            imprimir("La celda #" + numeroCelda + " se encuentra libre. No hay vehículo para retirar.");
            return;
        }

        String tipoVehiculo = tiposVehiculo[indiceCelda];
        LocalDateTime fechaHoraIngreso = fechasIngreso[indiceCelda];
        LocalDateTime fechaHoraSalida = leerFechaHoraSalidaValida(fechaHoraIngreso);

        long valorLiquidado = liquidarEstadia(numeroCelda, tipoVehiculo, fechaHoraIngreso, fechaHoraSalida);

        if (tipoVehiculo.equals("CARRO")) {
            recaudoCarros += valorLiquidado;
        } else {
            recaudoMotos += valorLiquidado;
        }
        recaudoTotal += valorLiquidado;

        tiposVehiculo[indiceCelda] = null;
        fechasIngreso[indiceCelda] = null;
        celdasOcupadas--;
        celdasLibres++;

        imprimir("Celda #" + numeroCelda + " liberada correctamente.");
    }

    static LocalDateTime leerFechaHoraSalidaValida(LocalDateTime fechaHoraIngreso) {
        while (true) {
            LocalDateTime fechaHoraSalida = leerFechaHora("Ingrese fecha y hora de salida (yyyy-MM-dd HH:mm): ");
            if (fechaHoraSalida.isBefore(fechaHoraIngreso)) {
                imprimir("La fecha y hora de salida no puede ser anterior a la de ingreso ("
                        + fechaHoraIngreso.format(FORMATO_FECHA_HORA) + ").");
                continue;
            }
            return fechaHoraSalida;
        }
    }

    // PASO 7: LIQUIDACIÓN Y FACTURA

    static long liquidarEstadia(int numeroCelda, String tipoVehiculo, LocalDateTime ingreso, LocalDateTime salida) {
        long diasACobrar = contarDiasCompletos(ingreso, salida);
        LocalDateTime cursor = ingreso.plusDays(diasACobrar);
        long horasACobrar = calcularHorasRestantes(cursor, salida);

        if (horasACobrar >= UMBRAL_HORAS_DIA) {
            diasACobrar++;
            horasACobrar = 0;
        }

        int tarifaDia = tipoVehiculo.equals("CARRO") ? TARIFA_DIA_CARRO : TARIFA_DIA_MOTO;
        int tarifaHora = tipoVehiculo.equals("CARRO") ? TARIFA_HORA_CARRO : TARIFA_HORA_MOTO;

        long subtotalDias = diasACobrar * tarifaDia;
        long subtotalHoras = horasACobrar * tarifaHora;
        long totalPagar = subtotalDias + subtotalHoras;

        mostrarFactura(numeroCelda, tipoVehiculo, ingreso, salida,
                diasACobrar, tarifaDia, subtotalDias,
                horasACobrar, tarifaHora, subtotalHoras, totalPagar);

        return totalPagar;
    }

    static long contarDiasCompletos(LocalDateTime ingreso, LocalDateTime salida) {
        return ChronoUnit.DAYS.between(ingreso, salida);
    }

    static long calcularHorasRestantes(LocalDateTime cursor, LocalDateTime salida) {
        long minutosRestantes = Duration.between(cursor, salida).toMinutes();
        return (minutosRestantes + 59) / 60;
    }

    static void mostrarFactura(int numeroCelda, String tipoVehiculo, LocalDateTime ingreso, LocalDateTime salida,
                               long dias, int tarifaDia, long subtotalDias,
                               long horas, int tarifaHora, long subtotalHoras, long totalPagar) {
        imprimir("\n--- FACTURA DE SALIDA ---");
        imprimir("Celda liberada: #" + numeroCelda);
        imprimir("Tipo de vehículo: " + tipoVehiculo);
        imprimir("Fecha y hora de ingreso: " + ingreso.format(FORMATO_FECHA_HORA));
        imprimir("Fecha y hora de salida:  " + salida.format(FORMATO_FECHA_HORA));
        imprimir("Desglose de cobro:");
        if (dias > 0) {
            imprimir("  - Días cobrados: " + dias + " x $" + tarifaDia + " = $" + subtotalDias);
        }
        if (horas > 0) {
            imprimir("  - Horas cobradas: " + horas + " x $" + tarifaHora + " = $" + subtotalHoras);
        }
        if (dias == 0 && horas == 0) {
            imprimir("  - Sin tiempo facturable ($0)");
        }
        imprimir("Total a pagar: $" + totalPagar);
        imprimir("-------------------------");
    }

    // MÉTODOS ADICIONALES

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