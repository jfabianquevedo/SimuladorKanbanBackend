package com.jquevedo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Entity
@Table(name = "Tareas")
public class Tareas {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer idTarea;
	
	@Size(min = 10, message = "la tarea debe tener minimo 10 caracteres y maximo 20")
	@Column(name = "nombreTarea",length = 20)
	private String nombreTarea;
	
	@Size(min = 15, message = "la descripcion debe tener minimo 15 caracteres y maximo 100")
	@Column(name = "resumenTarea", length = 100)
	private String resumenTarea;

	private String estadoTarea;
	@ManyToOne
	@JoinColumn(name="idUsuario", foreignKey = @ForeignKey(name = "FK_tarea_usuario"))
	private Usuarios usuarios;

}
