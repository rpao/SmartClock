package br.ufpe.nti.model;

import java.sql.Timestamp;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.ufpe.nti.util.CalcularAngulo;
import br.ufpe.nti.util.ClockLocalTimeSerializer;

public class SentClock {
	private Long id;

	@JsonSerialize(using = ClockLocalTimeSerializer.class)
	private LocalTime time;
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Timestamp createdAt;
	
	private double angle;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setAngle(){
		this.angle = CalcularAngulo.calcular(this.time.getHour(), this.time.getMinute());
	}
	
	public double getAngle(){
		return angle;
	}
}
