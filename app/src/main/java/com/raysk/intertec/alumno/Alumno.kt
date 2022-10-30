package com.raysk.intertec.alumno;

import android.graphics.Color;
import android.util.Log;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Alumno {
    public String control;
    public String password;
    public String passwordToken;
    public DatosGenerales datosGenerales;
    public DatosPersonales datosPersonales;
    public DatosAcademicos datosAcademicos;
    public ArrayList<HorarioEvent> horario;
    public Kardex kardex;
    public ArrayList<Calificaciones> calificaciones;
    static private Alumno alumno = null;

    private Alumno(String control, String password) {
        this.control = control;
        this.password = password;
        this.datosGenerales = new DatosGenerales();
        this.datosPersonales = new DatosPersonales();
        this.datosAcademicos = new DatosAcademicos();
        this.horario = new ArrayList<HorarioEvent>();
        this.kardex = new Kardex();
        this.calificaciones = new ArrayList<Calificaciones>();
    }

    public static Alumno getAlumno() {
        return alumno;
    }


    public static Alumno getAlumno(String control, String password) {
        if (alumno == null) {
            alumno = new Alumno(control, password);
        }
        return alumno;
    }

    public static void setAlumno(Alumno alumno){
        Alumno.alumno = alumno;
    }

    public boolean ValidarInicioDeSesion() throws IOException, JSONException {
        String url = "http://201.164.155.162/cgi-bin/sie.pl?Opc=MAIN&Control=" +
                this.control + "&password=" + this.password + "&aceptar=ACEPTAR";


        Document document = Jsoup.connect(url).get();
        String title = document.title();

        if (Objects.equals(title, "SIE Estudiantes")) {
            return false;
        }

        String pass = document.selectFirst("frame").attr("src");
        pass = pass.substring(pass.indexOf("Password") + 9);
        this.passwordToken = pass.substring(0, pass.indexOf("&"));
        ObtenerDatosDeAlumno();
       // ObtenerKardex();
        ObtenerHorario();
        ObtenerCalificaciones();
        setAlumno(this);
        return true;
    }

    //Obtiene los datos Personales del alumno
    private void ObtenerDatosDeAlumno() throws IOException {
        String url = "http://201.164.155.162/cgi-bin/sie.pl?Opc=DATOSALU&Control=" +
                this.control + "&password=" + this.passwordToken + "&aceptar=ACEPTAR";

        Document document = Jsoup.connect(url).get();
        Elements datos = document.select("strong");

        //Obteniendo los datos Generales
        this.datosGenerales.setNombre(datos.get(3).text().trim());
        this.datosGenerales.setCurp(datos.get(4).text().trim());
        this.datosGenerales.setCarrera(datos.get(7).text().replaceAll("\\(\\d+\\)", "")
                .trim());
        this.datosGenerales.setPlanDeEstudios(datos.get(8).text().replaceAll("\\(\\d+\\)", ""));
        this.datosGenerales.setEspecialidad(datos.get(9).text().replaceAll("\\(\\d+\\)", "")
                .trim());

        //Obteniendo los datos Personales
        datos = document.select("p");
        this.datosPersonales.setCalle(datos.get(0).text().trim());
        this.datosPersonales.setNoCalle(datos.get(1).text().trim());
        this.datosPersonales.setColonia(datos.get(2).text().trim());
        this.datosPersonales.setCiudad(datos.get(3).text().trim());
        this.datosPersonales.setCp(datos.get(4).text().trim());
        this.datosPersonales.setTelefono(datos.get(5).text().trim());
        this.datosPersonales.setCorreoPersonal(datos.get(6).text().trim());
        this.datosPersonales.setCorreoInstitucional(control + "@eldorado.tecnm.mx");
        this.datosPersonales.setFechaDeNacimiento(datos.get(8).text().trim());

        //Obteniendo los datos Academicos
        this.datosAcademicos.setEscuelaDeProcedencia(datos.get(9).text()
                .replaceAll("\\s*\\(*\\d*\\)\\s*", "").trim());
        this.datosAcademicos.setPeriodoDeIngreso(datos.get(10).text()
                .replaceAll("\\(\\d+\\)", "").trim());
        this.datosAcademicos.setPeriodosValidados(datos.get(11).text().trim());
        this.datosAcademicos.setPeriodoActual(datos.get(12).text()
                .replaceAll("\\(\\d+\\)", "").trim());
        this.datosAcademicos.setCreditosAcumulados(datos.get(13).text().trim());
        this.datosAcademicos.setSituacion(datos.get(14).text().trim());


    }

    private void ObtenerKardex() throws IOException {
        String url = "http://201.164.155.162/cgi-bin/sie.pl?Opc=KARDEX&Control=" + this.control +
                "&password=" + this.passwordToken + "&aceptar=ACEPTAR";

        Document document = Jsoup.connect(url).get();
        Elements tables = document.select("table");
        Elements trs = tables.get(1).select("tr");
        List<KardexData> data = new ArrayList<>();

        for (int i = 2; i < trs.size() - 1; i++) {
            Element tr = trs.get(i);
            Elements tds = tr.select("td");
            data.add(new KardexData(
                    tds.get(0).text(),
                    tds.get(1).text(),
                    tds.get(2).text(),
                    tds.get(5).text()
            ));
        }
        this.kardex.setData(data);

        this.kardex.setPromedio(trs.get(trs.size() - 1).select("td").get(2).text());
        trs = tables.get(2).select("tr");
        String[] creditos = trs.get(0).selectFirst("td").text().toUpperCase().replace("CREDITOS ACUMULADOS", "")
                .trim().split(" DE ");
        this.kardex.setCreditosObtenidos(creditos[0]);
        this.kardex.setCreditosTotales(creditos[1]);
        this.kardex.setAvance(trs.get(1).selectFirst("td").text().toUpperCase().replace("% DE AVANCE:", "")
                .trim());

        Log.i("kardex", this.kardex.toString());
    }

    private void ObtenerHorario() throws IOException {

        String url = "http://201.164.155.162/cgi-bin/sie.pl?Opc=HORARIO&Control=" + this.control +
                "&password=" + this.passwordToken + "&aceptar=ACEPTAR";

        Document document = Jsoup.connect(url).get();
        Elements tables = document.select("table");
        Elements trs = tables.get(1).select("tr");
        int id = 0;
        String[] colors = {
                "#bf9780",
                "#fdcae1",
                "#ffda89",
                "#84b6f4",
                "#fdfd96",
                "#ff6961",
                "#bae0f5",
                "#77dd77",
                "#98f6a9",
                "#bc98f3"
        };
        for (int i = 1; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            for (int j = 7; j <= 11; j++) {
                String[] horaAula = tds.get(j).text().split(" ");
                if (horaAula[0] == "") {
                    continue;
                }
                String[] hora = horaAula[0].split("-");
                String aula = horaAula[1];

                horario.add(new HorarioEvent(
                        id++,
                        tds.get(1).text().trim(),
                        Integer.parseInt(hora[0].split(":")[0]),
                        Integer.parseInt(hora[1].split(":")[0]),
                        aula,
                        j - 6,
                        Color.parseColor(colors[i-1])
                ));


            }
        }

    }

    private void ObtenerCalificaciones() throws IOException {
        String url = "http://201.164.155.162/cgi-bin/sie.pl?Opc=CALIF&Control=" + this.control +
                "&password=" + this.passwordToken + "&aceptar=ACEPTAR";
        Document document = Jsoup.connect(url).get();
        Elements tables = document.select("table");
        Elements trs = tables.get(1).select("tr");

        for (int i = 1; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            float promedio = 0;
            ArrayList<Integer> notas = new ArrayList<>();
            for (int j = 5; j < tds.size() ; j++) {
                String nota = tds.get(j).text().trim();
                if (nota != ""){
                    notas.add(Integer.parseInt(nota));
                    promedio += Integer.parseInt(nota);
                }
            }
            promedio = promedio/notas.size();
            this.calificaciones.add(new Calificaciones(
                    tds.get(0).text().trim(),
                    tds.get(2).text().trim(),
                    notas,
                    promedio
            ));
        }
    }
}
