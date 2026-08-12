package com.vali.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "filetab")
public class FileData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fileName")
	private String fileName;
	
	@Column(name = "fileType")
	private String fileType;
	
	@Lob
	@Column(name = "fileData", columnDefinition = "LONGBLOB")
	private byte[] fileData;
	
	@CreationTimestamp
	@Column(name = "createDate")
	private LocalDateTime createDate;
	
	@UpdateTimestamp
	@Column(name = "updateDate")
	private LocalDateTime updateDate;
}
