package modelo;

public class NodoPagina{
    int pagina, frecuencia, altura;
    NodoPagina izq, der;

    public NodoPagina(int pagina) {
        this.pagina = pagina;
        frecuencia = 1;
        altura = 1;
    }
}
