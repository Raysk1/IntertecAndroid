package com.raysk.intertec;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class Alumno {
    public String control;
    public String password;
    public String passwordToken;
    public JSONObject datosGenerales;
    public JSONObject datosPersonales;
    public JSONObject datosAcademicos;
    static private Alumno alumno= null;

    private Alumno(String control, String password) {
        this.control = control;
        this.password = password;
        this.datosGenerales = new JSONObject();
        this.datosPersonales = new JSONObject();
        this.datosAcademicos = new JSONObject();
    }

    public static Alumno getAlumno(){
        return alumno;
    }


    public static Alumno getAlumno(String control, String password) {
        if (alumno == null){
            alumno = new Alumno(control,password);
        }
        return alumno;
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

        return true;
    }

    //Obtiene los datos Personales del alumno
    public void ObtenerDatosDeAlumno() throws IOException, JSONException {
        String url = "http://201.164.155.162/cgi-bin/sie.pl?Opc=DATOSALU&Control=" +
                this.control + "&password=" + this.passwordToken + "&aceptar=ACEPTAR";

        Document document = Jsoup.connect(url).get();
        Elements datos = document.select("strong");

        //Obteniendo los datos Generales
        this.datosGenerales.put("nombre", datos.get(3).text().trim());
        this.datosGenerales.put("curp", datos.get(4).text().trim());
        this.datosGenerales.put("carrera", datos.get(7).text().replaceAll("\\(\\d+\\)", "")
                .trim());
        this.datosGenerales.put("planDeEstudios", datos.get(8).text().replaceAll("\\(\\d+\\)", ""));
        this.datosGenerales.put("especialidad", datos.get(9).text().replaceAll("\\(\\d+\\)", "")
                .trim());

        //Obteniendo los datos Personales
        datos = document.select("p");
        this.datosPersonales.put("calle", datos.get(0).text().trim());
        this.datosPersonales.put("noCalle", datos.get(1).text().trim());
        this.datosPersonales.put("colonia", datos.get(2).text().trim());
        this.datosPersonales.put("ciudad", datos.get(3).text().trim());
        this.datosPersonales.put("cp", datos.get(4).text().trim());
        this.datosPersonales.put("telefono", datos.get(5).text().trim());
        this.datosPersonales.put("correoPersonal", datos.get(6).text().trim());
        this.datosPersonales.put("correoInstitucional",control + "@eldorado.tecnm.mx");
        this.datosPersonales.put("fechaDeNacimiento", datos.get(8).text().trim());

        //Obteniendo los datos Academicos
        this.datosAcademicos.put("escuelaDeProcedencia",datos.get(9).text()
                .replaceAll("\\s*\\(*\\d*\\)\\s*","").trim());
        this.datosAcademicos.put("periodoDeIngreso",datos.get(10).text()
                .replaceAll("\\(\\d+\\)", "").trim());
        this.datosAcademicos.put("periodosValidados",datos.get(11).text().trim());
        this.datosAcademicos.put("periodoActual",datos.get(12).text()
                .replaceAll("\\(\\d+\\)", "").trim());
        this.datosAcademicos.put("creditosAcumulados",datos.get(13).text().trim());
        this.datosAcademicos.put("situacion",datos.get(14).text().trim());






    }

}
