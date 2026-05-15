package com.pedidos.model;

/**
 * Modelo de datos que representa un pedido de restaurante.
 * El id se autogenera mediante un contador estático.
 */
public class Pedido {

    private static int contadorId = 1;

    private final int id;
    private final String nombrePlato;
    private final String categoria;
    private final int cantidad;
    private final String metodoEntrega;
    private final String direccion;

    public Pedido(String nombrePlato, String categoria, int cantidad,
                  String metodoEntrega, String direccion) {
        this.id = contadorId++;
        this.nombrePlato = nombrePlato;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.metodoEntrega = metodoEntrega;
        this.direccion = direccion;
    }

    public int getId()             { return id; }
    public String getNombrePlato() { return nombrePlato; }
    public String getCategoria()   { return categoria; }
    public int getCantidad()       { return cantidad; }
    public String getMetodoEntrega() { return metodoEntrega; }
    public String getDireccion()   { return direccion; }

    @Override
    public String toString() {
        return "Pedido{id=" + id
                + ", plato='" + nombrePlato + "'"
                + ", categoria='" + categoria + "'"
                + ", cantidad=" + cantidad
                + ", entrega='" + metodoEntrega + "'"
                + ", direccion='" + direccion + "'}";
    }
}
