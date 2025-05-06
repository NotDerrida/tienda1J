package com.app.tienda1;

import java.util.List;

public class PedidoDTO {
    private String ticket;
    private Double totalPago;
    private String fechaEntrega;
    private String estado;
    private List<String> productos; // Lista de nombres de productos

    // Constructor
    public PedidoDTO(String ticket, Double totalPago, String fechaEntrega, String estado, List<String> productos) {
        this.ticket = ticket;
        this.totalPago = totalPago;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.productos = productos;
    }

    // Getters y setters
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(Double totalPago) {
        this.totalPago = totalPago;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getProductos() {
        return productos;
    }

    public void setProductos(List<String> productos) {
        this.productos = productos;
    }
}