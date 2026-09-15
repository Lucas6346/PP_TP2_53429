package Modelo.Certificacion;

import Modelo.Estudiante;

public interface Certificable
{
    String ENTIDAD_EMISORA = "Universidad X";
    String generarCertificado(Estudiante est);
}
