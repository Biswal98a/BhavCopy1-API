package com.BhavCopy_1.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "job")
public class JobQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private UUID reqid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addedat;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startedat;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endedat;

    private Duration duration;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> params;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<?, ?> response;

    private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getReqid() {
		return reqid;
	}

	public void setReqid(UUID reqid) {
		this.reqid = reqid;
	}

	public LocalDateTime getAddedat() {
		return addedat;
	}

	public void setAddedat(LocalDateTime addedat) {
		this.addedat = addedat;
	}

	public LocalDateTime getStartedat() {
		return startedat;
	}

	public void setStartedat(LocalDateTime startedat) {
		this.startedat = startedat;
	}

	public LocalDateTime getEndedat() {
		return endedat;
	}

	public void setEndedat(LocalDateTime endedat) {
		this.endedat = endedat;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Map<?, ?> getResponse() {
		return response;
	}

	public void setResponse(Map<?, ?> response) {
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}




    
    

	
}
