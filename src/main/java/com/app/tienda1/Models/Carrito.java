package com.app.tienda1;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false) // Relación con la entidad Usuario
    private Usuario cliente;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ContenidoCarrito> contenido;

    // Getters y setters
    public Integer getIdCarrito() {
        return id;
    }

    public void setIdCarrito(Integer idCarrito) {
        this.id = idCarrito;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public List<ContenidoCarrito> getContenido() {
        return contenido;
    }

    public void setContenido(List<ContenidoCarrito> contenido) {
        this.contenido = contenido;
    }

    // Método auxiliar para obtener el ID del cliente sin duplicar campos
    public Integer getIdCliente() {
        return cliente != null ? cliente.getId() : null;
    }
}