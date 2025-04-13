package com.jvalencia.peliculas.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

// ### Define el orden en que se presentará la respuesta en el JSON
@JsonPropertyOrder({"status","cantidad","timestamp","data"})

public class ResponseWrapper<T> {
    private String status;
    private int cantidad;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private List<T> data;

    public ResponseWrapper(String status, int cantidad, List<T> data){
        this.status = status;
        this.cantidad = cantidad;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }


    // --getters y setters

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void setTimeStamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }

    public String getStatus(){
        return status;
    }

    public int getCantidad(){
        return cantidad;
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public List<T> getData(){
        return data;
    }
    public void setData(List<T> data){
        this.data = data;
    }
}
