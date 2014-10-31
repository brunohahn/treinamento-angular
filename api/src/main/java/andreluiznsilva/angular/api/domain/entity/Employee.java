package andreluiznsilva.angular.api.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import andreluiznsilva.angular.api.domain.vo.Function;

@Entity
@XmlRootElement
public class Employee implements Serializable {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;
	
	@NotNull
	@Min(value = 1)
	private Double salary;
	
	@Enumerated(EnumType.STRING)
	private Function function;

	public Date getBirthday() {
		return birthday;
	}

	public Function getFunction() {
		return function;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

}